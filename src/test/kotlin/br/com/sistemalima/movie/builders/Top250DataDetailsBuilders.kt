package br.com.sistemalima.movie.builders

import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail
import kotlin.random.Random

class Top250DataDetailsBuilders: AbstractBuilder<Top250DataDetail>(){


    override fun random(): Top250DataDetail {
        return Top250DataDetail(
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10)
        )
    }

    fun randomTwo(): Top250DataDetail {
        return Top250DataDetail(
            "idValido",
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10),
            randomString(10)
        )
    }


}