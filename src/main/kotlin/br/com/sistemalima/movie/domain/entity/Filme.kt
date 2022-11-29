package br.com.sistemalima.movie.domain.entity

import br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto.Top250DataDetail

class Filme(
    val id: String,
    val title: String,
    val urlImage: String,
    val rating: String,
    val year: String,
    val crew: String,
){
    constructor(top250DataDetail: Top250DataDetail): this(
        top250DataDetail.id,
        top250DataDetail.title,
        top250DataDetail.image,
        top250DataDetail.imDbRating,
        top250DataDetail.year,
        top250DataDetail.crew
    )


}