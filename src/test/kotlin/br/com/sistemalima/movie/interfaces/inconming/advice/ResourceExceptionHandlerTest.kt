package br.com.sistemalima.movie.interfaces.inconming.advice

import br.com.sistemalima.movie.domain.exceptions.BadRequestException
import br.com.sistemalima.movie.domain.exceptions.ResourceNotFoundException
import com.nhaarman.mockitokotlin2.any
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension::class)
internal class ResourceExceptionHandlerTest {

    @InjectMocks
    private lateinit var resourceExceptionHandler: ResourceExceptionHandler

    private val request = MockHttpServletRequest()

    private val testMessage = "testando mensagem"

    @Test
    fun handleBadRequestException() {
        // Dado
        val exception = BadRequestException(testMessage)

        // Quando

        val response = resourceExceptionHandler.handleBadRequestException(exception, request)

        // Então
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.status)
        assertEquals(testMessage, response.message)
        assertEquals(HttpStatus.BAD_REQUEST.name, response.error)

    }

    @Test
    fun handleResourceNotFoundException() {
        // Dado
        val exception = ResourceNotFoundException(testMessage)

        // Quando

        val response = resourceExceptionHandler.handleResourceNotFoundException(exception, request)

        // Então
        assertEquals(HttpStatus.NOT_FOUND.value(), response.status)
        assertEquals(testMessage, response.message)
        assertEquals(HttpStatus.NOT_FOUND.name, response.error)

    }

    @Test
    fun handleConstraintViolationException() {
        // Dado
        val exception = ConstraintViolationException(testMessage, any())

        // Quando

        val response = resourceExceptionHandler.handleConstraintViolationException(exception, request)

        // Então
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.status)
        assertEquals(testMessage, response.message)
        assertEquals(HttpStatus.BAD_REQUEST.name, response.error)

    }
}