package com.pearlbailey.pearlbaileyhighschool.spring.web.error

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
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

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val msg = with(request as ServletWebRequest) {
            "Failed to invoke ${this.request.method} ${this.request.requestURI}"
        }
        return ResponseEntity.badRequest().body(errorResponseFactory.badRequest(ex, msg))
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleHttpMessageNotReadable(ex, headers, status, request)
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(ex: ConstraintViolationException) = errorResponseFactory.badRequest(ex)

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(ex: NotFoundException, request: WebRequest) =
        with(request as ServletWebRequest) {
            if (httpMethod == GET) {
                ResponseEntity.status(NOT_FOUND.value()).body(errorResponseFactory.notFoundException(ex))
            } else {
                ResponseEntity.badRequest().body(errorResponseFactory.badRequest(ex))
            }
        }

}
