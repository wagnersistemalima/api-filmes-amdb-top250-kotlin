package br.com.sistemalima.movie.domain.service

import br.com.sistemalima.movie.domain.entity.Filme
import br.com.sistemalima.movie.domain.entity.Observabilidade
import br.com.sistemalima.movie.domain.exceptions.ResourceNotFoundException
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.ImdbClient
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250Data
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class MovieService(
    @Autowired
    private val imdbClient: ImdbClient,

) {

    @Value("\${servers.imdb-filmes.apikey}")
    private var apiKey: String = ""

    companion object {
        private const val tag = "class: MovieService, "
        private val logger = LoggerFactory.getLogger(MovieService::class.java)
        private val errorMessageNotfound = "Recurso não encontrado, "
    }

    fun listarFilmesTop250(observabilidade: Observabilidade): List<Filme> {
        try {
            logger.info(String.format(tag + observabilidade))

            val top250Data = imdbClient.buscar250TopFilmes(apiKey)
            return top250Data.items.map { it -> Filme(it) }
        } catch (exception: FeignException) {
            logger.error(String.format("Error message: ${exception.message},  $tag, $observabilidade"))
            throw IOException(exception.message)
        }

    }

    fun filmeFavorito(id: String, observabilidade: Observabilidade): List<Filme> {
        try {
            logger.info(String.format("method: filmeFavorito, $tag $observabilidade"))

            val top250Data = imdbClient.buscar250TopFilmes(apiKey)

            return filtrarPorFavorito(id, observabilidade, top250Data)
        }catch (exception: FeignException) {
            logger.error(String.format("Error message: ${exception.message},  $tag, $observabilidade"))
            throw IOException(exception.message)
        }
    }

    fun filtrarPorFavorito(id: String, observabilidade: Observabilidade, top250Data: Top250Data<Top250DataDetail>): List<Filme> {
        logger.info(String.format("method: filtrarPorFavorito, $tag $observabilidade"))

        val listaFilmeFavorito = mutableListOf<Filme>()

        val filmes = top250Data.items.filter { filme -> filme.id == id }


        if (filmes.isEmpty()) {
            throw ResourceNotFoundException(errorMessageNotfound)
        }
        return filmes.map { it -> Filme(it) }

    }
}