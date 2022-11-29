package br.com.sistemalima.movie.interfaces.inconming.controller

import br.com.sistemalima.movie.builders.FilmeBuilder
import br.com.sistemalima.movie.builders.Top250DataBuilders
import br.com.sistemalima.movie.domain.dto.FilmeResponseDTO
import br.com.sistemalima.movie.domain.dto.ListaFilmesResponseDTO
import br.com.sistemalima.movie.domain.entity.Filme
import br.com.sistemalima.movie.domain.entity.Observabilidade
import br.com.sistemalima.movie.domain.enums.VersionApiEnum
import br.com.sistemalima.movie.domain.exceptions.BadRequestException
import br.com.sistemalima.movie.domain.exceptions.ResourceNotFoundException
import br.com.sistemalima.movie.domain.service.MovieService
import br.com.sistemalima.movie.interfaces.mapper.ObservabilidadeMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class MovieControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var movieService: MovieService

    @MockBean
    private lateinit var observabilidadeMapper: ObservabilidadeMapper

    private val uriTop250 = URI("/filmes/top250")


    private val versionApi = VersionApiEnum.V1.version
    private val resourceName = "resourceName"
    private val testMessage = "test message"

    private val observabilidade = Observabilidade(versionApi, resourceName)


    @Test
    fun `listarFilmesTop250 deve listar filmes 250 top com sucesso`() {

        // Dado
        val top250Data = Top250DataBuilders().random()
        val listaFilmes = top250Data.items.map { filme -> Filme(filme) }
        val listaDeFilmesResponseDTO = ListaFilmesResponseDTO(listaFilmes)


        Mockito.`when`(observabilidadeMapper.map(any(), any())).thenReturn(observabilidade)
        Mockito.`when`(movieService.listarFilmesTop250(any())).thenReturn(listaFilmes)


        // Quando / Então

        mockMvc.perform(MockMvcRequestBuilders.get(uriTop250)
            .header("Accept-Version", "v1")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJson(listaDeFilmesResponseDTO)))


    }

    @Test
    fun `listarFilmesTop250 deveria retornar 400 badRequest, quando a versao da api nao for validada`() {

        // Dado
        Mockito.`when`(observabilidadeMapper.map(any(), any())).thenThrow(BadRequestException::class.java)


        // Quando / Então

        mockMvc.perform(MockMvcRequestBuilders.get(uriTop250)
            .header("Accept-Version", "v1")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)

    }

    @Test
    fun `favoritos deve listar filmes favorito com sucesso`() {

        // Dado
        val idFilmeValido = "idValido"
        val uriFilmeFavorito = UriComponentsBuilder.fromUriString("/filmes/favoritos/{id}").buildAndExpand(idFilmeValido).toUri()

        val filme = FilmeBuilder().random()

        val listFilmes = listOf(filme)
        val response = FilmeResponseDTO(listFilmes)

        Mockito.`when`(observabilidadeMapper.map(any(), any())).thenReturn(observabilidade)

        Mockito.`when`(movieService.filmeFavorito(idFilmeValido, observabilidade)).thenReturn(listFilmes)

        // Quando / Então

        mockMvc.perform(MockMvcRequestBuilders.post(uriFilmeFavorito)
            .header("Accept-Version", "v1")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJsonFilmeResponseDTO(response)))
    }

    @Test
    fun `favoritos deve retornar 404 quando nao encontrar filme favorito por id`() {

        // Dado
        val idFilme = "abcdef"
        val uriFilmeFavorito = UriComponentsBuilder.fromUriString("/filmes/favoritos/{id}").buildAndExpand(idFilme).toUri()

        Mockito.`when`(observabilidadeMapper.map(any(), any())).thenReturn(observabilidade)

        Mockito.`when`(movieService.filmeFavorito(idFilme, observabilidade)).thenThrow(ResourceNotFoundException::class.java)

        // Quando / Então

        mockMvc.perform(MockMvcRequestBuilders.post(uriFilmeFavorito)
            .header("Accept-Version", "v1")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isNotFound)

    }

    private fun toJson(listaDeFilmesResponseDTO: ListaFilmesResponseDTO<List<Filme>>): String {
        return objectMapper.writeValueAsString(listaDeFilmesResponseDTO)
    }

    private fun toJsonFilmeResponseDTO(listaDeFilmesResponseDTO: FilmeResponseDTO<List<Filme>>): String {
        return objectMapper.writeValueAsString(listaDeFilmesResponseDTO)
    }


}