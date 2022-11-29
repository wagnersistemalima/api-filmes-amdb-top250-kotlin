package br.com.sistemalima.movie.interfaces.inconming.advice

import br.com.sistemalima.movie.domain.exceptions.BadRequestException
import br.com.sistemalima.movie.domain.exceptions.ResourceNotFoundException
import br.com.sistemalima.movie.interfaces.inconming.advice.dto.ErrorView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ErrorView {

        val errorMessage = HashMap<String, String?>()
        exception.bindingResult.fieldErrors.forEach { error -> errorMessage[error.field] = error.defaultMessage }

        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(exception: ConstraintViolationException, request: HttpServletRequest): ErrorView {

        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message.toString(),
            path = request.servletPath

        )

    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(exception: BadRequestException, request: HttpServletRequest): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message.toString(),
            path = request.servletPath

        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(exception: ResourceNotFoundException, request: HttpServletRequest): ErrorView {
        return ErrorView(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message.toString(),
            path = request.servletPath

        )
    }
}