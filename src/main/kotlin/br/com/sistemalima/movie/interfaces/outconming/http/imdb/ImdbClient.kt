package br.com.sistemalima.movie.interfaces.outconming.http.imdb

import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250Data
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Component
@FeignClient(name = "api-imdb", url = "\${servers.imdb-filmes.url}")
interface ImdbClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["{lang}/API/Top250Movies/{apiKey}"])
    fun buscar250TopFilmes(@PathVariable apiKey: String): Top250Data<Top250DataDetail>

}