package br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto

data class Top250DataDetail(

    val id: String,

    val rank: String,

    val title: String,

    val fullTitle: String,

    val year: String,

    val image: String,

    val crew: String,

    val imDbRating: String,

    val imDbRatingCount: String
)
