package br.com.sistemalima.movie.interfaces.outconming.http.imdb.dto

data class Top250Data<T>(
    val items: List<Top250DataDetail> = listOf(),
    val errorMessage: String
)
