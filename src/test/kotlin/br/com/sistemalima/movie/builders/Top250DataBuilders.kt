package br.com.sistemalima.movie.builders

import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250Data
import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail

class Top250DataBuilders: AbstractBuilder<Top250Data<Top250DataDetail>>() {


    override fun random(): Top250Data<Top250DataDetail> {
        val top250DataDetailOne = Top250DataDetailsBuilders().random()
        val top250DataDetailTwo = Top250DataDetailsBuilders().randomTwo()
        val top250DataDetailTre = Top250DataDetailsBuilders().random()

        val list = listOf<Top250DataDetail>(top250DataDetailOne, top250DataDetailTwo, top250DataDetailTre)


        return Top250Data(
            items = list,
            errorMessage = ""
        )
    }
}