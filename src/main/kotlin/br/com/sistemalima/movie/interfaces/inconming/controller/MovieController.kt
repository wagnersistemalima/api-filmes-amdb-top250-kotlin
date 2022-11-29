package br.com.sistemalima.movie.interfaces.inconming.controller

import br.com.sistemalima.movie.domain.dto.FilmeResponseDTO
import br.com.sistemalima.movie.domain.dto.ListaFilmesResponseDTO
import br.com.sistemalima.movie.domain.entity.Filme
import br.com.sistemalima.movie.domain.service.MovieService
import br.com.sistemalima.movie.interfaces.mapper.ObservabilidadeMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping(path = ["/filmes"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MovieController(
    @Autowired
    private val movieService: MovieService,
    @Autowired
    private val observabilidadeMapper: ObservabilidadeMapper
) {

    companion object {
        private val tagStart = "Inicio do processo request, class: MovieController, "
        private val tagEnd = "Fim do processo request, class: MovieController, "
        private val listarTop250Filmes = "pesquisar os Top250 filmes, "
        private val filmeFavorito = "pesquisar o filme favorito, "
        private val logger = LoggerFactory.getLogger(MovieController::class.java)
    }

    @GetMapping(value = ["/top250"])
    fun listarFilmesTop250(
        @RequestHeader(value = "Accept-Version") @Valid @NotBlank version: String?
    ): ResponseEntity<ListaFilmesResponseDTO<List<Filme>>> {

        val observabilidade = observabilidadeMapper.map(version, listarTop250Filmes)

        logger.info(String.format(tagStart + observabilidade))

        val response = movieService.listarFilmesTop250(observabilidade)
        val listaFilmesResponseDTO = ListaFilmesResponseDTO(response)

        logger.info(String.format(tagEnd + observabilidade))

        return ResponseEntity.ok().body(listaFilmesResponseDTO)
    }

    @PostMapping(value = ["/favoritos/{id}"])
    fun favoritos(
        @RequestHeader(value = "Accept-Version") @Valid @NotBlank version: String?,
        @PathVariable @NotBlank id: String?
    ): ResponseEntity<FilmeResponseDTO<List<Filme>>> {
        val observabilidade = observabilidadeMapper.map(version, filmeFavorito)
        logger.info(String.format("$tagStart id: $id, $observabilidade"))

        val listFilme = movieService.filmeFavorito(id!!, observabilidade)
        val filmeResponseDTO = FilmeResponseDTO(listFilme)

        logger.info(String.format("$tagEnd id: $id, $observabilidade"))
        return ResponseEntity.ok().body(filmeResponseDTO)
    }
}