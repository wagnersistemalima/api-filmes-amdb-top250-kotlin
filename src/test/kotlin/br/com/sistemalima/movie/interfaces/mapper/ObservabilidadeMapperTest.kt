package br.com.sistemalima.movie.interfaces.mapper

import br.com.sistemalima.movie.domain.enums.VersionApiEnum
import br.com.sistemalima.movie.domain.exceptions.BadRequestException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class ObservabilidadeMapperTest {

    @InjectMocks
    private lateinit var observabilidadeMapper: ObservabilidadeMapper

    private val version = VersionApiEnum.V1.version
    private val resourceName = "resourceName"

    @Test
    fun `deve mapear a version api e resourceName`() {
        // Dado

        val observabilidade = observabilidadeMapper.map(version, resourceName)

        // Quando / Então

        Assertions.assertEquals(version, observabilidade.version)
        Assertions.assertEquals(resourceName, observabilidade.resourceName)
        Assertions.assertNotNull(observabilidade.date)
        Assertions.assertNotNull(observabilidade.correlationId)
    }

    @Test
    fun `deve lancar exception BadRequestException, quando nao validar a versao`() {
        // Dado
        val versionInvalida = "versaoInvalida"

        // Quando / Então

        Assertions.assertThrows(BadRequestException::class.java) {observabilidadeMapper.map(versionInvalida, resourceName)}
    }

    @Test
    fun `deve lancar exception BadRequestException, quando passar version nula`() {
        // Dado
        val versionInvalida = null

        // Quando / Então

        Assertions.assertThrows(BadRequestException::class.java) {observabilidadeMapper.map(versionInvalida, resourceName)}
    }

}