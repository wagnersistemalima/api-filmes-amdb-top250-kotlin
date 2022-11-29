package br.com.sistemalima.movie.builders

import br.com.sistemalima.movie.domain.entity.Filme

class FilmeBuilder: AbstractBuilder<Filme>() {
    override fun random(): Filme {
        return Filme(
            id = "idValido",
            title = randomString(10),
            urlImage = randomString(10),
            rating = randomString(10),
            year = randomString(10),
            crew = randomString(10)
        )
    }
}