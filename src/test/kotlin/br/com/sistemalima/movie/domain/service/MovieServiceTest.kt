package br.com.sistemalima.movie.domain.service

import br.com.sistemalima.movie.builders.Top250DataBuilders
import br.com.sistemalima.movie.domain.entity.Observabilidade
import br.com.sistemalima.movie.domain.enums.VersionApiEnum
import br.com.sistemalima.movie.domain.exceptions.ResourceNotFoundException
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.ImdbClient
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250Data
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail
import com.nhaarman.mockitokotlin2.any
import feign.FeignException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.IOException

@ExtendWith(SpringExtension::class)
internal class MovieServiceTest {

    @field:InjectMocks
    private lateinit var movieService: MovieService

    @field:Mock
    private lateinit var imdbClient: ImdbClient

    private val testMessage = "test message"

    private val observabilidade = Observabilidade(
        version = VersionApiEnum.V1.version,
        resourceName = "resourceName"
    )

    @Test
    fun `listarFilmesTop250 deve retornar uma lista de filme dos top 250`() {
        // Dado
        val listTop250Data = Top250DataBuilders().random()
        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenReturn(listTop250Data)

        // Quando

        val top250Data = movieService.listarFilmesTop250(observabilidade)

        // Então
        Assertions.assertEquals(3, top250Data.size)
        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())
    }

    @Test
    fun `listarFilmesTop250 deve retornar uma lista de filmes vazia quando apikey invalida`() {
        // Dado
        val listTop250Data = Top250Data<Top250DataDetail>(
            errorMessage = testMessage
        )

        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenReturn(listTop250Data)

        // Quando

        val listaFilmes = movieService.listarFilmesTop250(observabilidade)

        // Então
        Assertions.assertEquals(0, listaFilmes.size)

        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())
    }

    @Test
    fun `listarFilmesTop250 deve retornar IoException, quando api imdb estiver off`() {
        // Dado
        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenThrow(FeignException::class.java)

        // Quando / Então
        Assertions.assertThrows(IOException::class.java) {movieService.listarFilmesTop250(observabilidade)}

        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())
    }

    @Test
    fun `filmeFavorito deve retornar uma lista com o filme favorito, quando passar id do filme`() {

        // Dado
        val idFilmeValido = "idValido"
        val listTop250Data = Top250DataBuilders().random()

        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenReturn(listTop250Data)

        // Quando

        val listFilmeFavorito = movieService.filmeFavorito(idFilmeValido, observabilidade)

        // Então

        Assertions.assertEquals(1, listFilmeFavorito.size)
        Assertions.assertEquals(idFilmeValido, listFilmeFavorito[0].id)
        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())

    }

    @Test
    fun `filmeFavorito deve retornar uma exception ResourceNotFoundException, quando nao encontrar o filme pelo id`() {

        // Dado
        val idFilme = "abcdef"
        val listTop250Data = Top250DataBuilders().random()

        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenReturn(listTop250Data)

        // Quando

        Assertions.assertThrows(ResourceNotFoundException::class.java) {movieService.filmeFavorito(idFilme, observabilidade)}

        // Então

        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())

    }

    @Test
    fun `filmeFavorito deve retornar uma exception IoException, quando api imdb estiver off`() {

        // Dado
        val idFilme = "abcdef"

        Mockito.`when`(imdbClient.buscar250TopFilmes(any())).thenThrow(FeignException::class.java)

        // Quando

        Assertions.assertThrows(IOException::class.java) {movieService.filmeFavorito(idFilme, observabilidade)}

        // Então

        Mockito.verify(imdbClient, Mockito.times(1)).buscar250TopFilmes(any())

    }

}