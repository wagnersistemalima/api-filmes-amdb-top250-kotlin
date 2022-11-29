package br.com.sistemalima.movie.domain.constants

import br.com.sistemalima.movie.domain.enums.VersionApiEnum
import java.util.Collections

class ApiConstantVersion {

    companion object {

        // adicionando as versoes criadas da api na lista
        val list = Collections.unmodifiableList(VersionApiEnum.values().asList())
    }
}