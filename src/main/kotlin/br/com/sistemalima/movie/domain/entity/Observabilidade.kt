package br.com.sistemalima.movie.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class Observabilidade(
    val version: String,
    val resourceName: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val correlationId: String = UUID.randomUUID().toString()
)
