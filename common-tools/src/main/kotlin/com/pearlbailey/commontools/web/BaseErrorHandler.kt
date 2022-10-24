package com.pearlbailey.commontools.web

import com.pearlbailey.commontools.exception.NotFoundException
import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.commontools.web.model.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class BaseErrorHandler(private val errorResponseFactory: ErrorResponseFactory) : ResponseEntityExceptionHandler() {

    @ResponseStatus(BAD_REQUEST)
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val msg = with(request as ServletWebRequest) {
            "Failed to invoke ${this.request.method} ${this.request.requestURI}"
        }
        return ResponseEntity.badRequest().body(errorResponseFactory.badRequest(ex, msg))
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(ex: ConstraintViolationException, request: WebRequest): ErrorResponse {
        val msg = with(request as ServletWebRequest) {
            "Failed to invoke ${this.request.method} ${this.request.requestURI}"
        }
        return errorResponseFactory.badRequest(ex, msg)
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(ex: NotFoundException, request: WebRequest) =
        ResponseEntity.status(NOT_FOUND.value()).body(errorResponseFactory.notFoundException(ex))

    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableRequestException::class)
    fun unprocessableRequestException(ex: UnprocessableRequestException) = errorResponseFactory.unprocessableEntity(ex)

}
