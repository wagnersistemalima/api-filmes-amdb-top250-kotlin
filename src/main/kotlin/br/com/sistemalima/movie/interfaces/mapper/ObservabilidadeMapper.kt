package br.com.sistemalima.movie.interfaces.mapper

import br.com.sistemalima.movie.domain.constants.ApiConstantVersion
import br.com.sistemalima.movie.domain.entity.Observabilidade
import br.com.sistemalima.movie.domain.enums.VersionApiEnum
import br.com.sistemalima.movie.domain.exceptions.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ObservabilidadeMapper {

    companion object {
        private val listVersion = ApiConstantVersion.list
        private const val errorMessage = "Versão da api não validada, "
        private val logger = LoggerFactory.getLogger(ObservabilidadeMapper::class.java)
        private val tag = "class: ObservabilidadeMapper, "
    }

    fun map(version: String?, resourceName: String): Observabilidade {

        logger.info(String.format("$tag version: $version, resourceName: $resourceName"))

        val status = validaVersion(version, listVersion)

        if (status) {
            return Observabilidade(
                version = version!!,
                resourceName = resourceName
            )
        } else {
            throw BadRequestException(errorMessage)
        }

    }

    private fun validaVersion(version: String?, list: List<VersionApiEnum>): Boolean {

        logger.info(String.format("method: validaVersion, $tag version: $version"))
        var status = false

        if (version == null) {
            logger.error(String.format("Error message: $errorMessage, $tag"))
            throw BadRequestException(errorMessage)
        }

        for(i in list) {
            if(i.version == version) {
                status = true
                break
            } else {
                logger.error(String.format("Error message: $errorMessage, method: validaVersion, $tag"))
                throw BadRequestException(errorMessage)
            }
        }
        return status

    }
}