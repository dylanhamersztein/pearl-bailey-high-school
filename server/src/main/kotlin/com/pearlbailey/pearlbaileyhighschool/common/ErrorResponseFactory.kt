package com.pearlbailey.pearlbaileyhighschool.common

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException
import com.pearlbailey.pearlbaileyhighschool.common.model.web.Error
import com.pearlbailey.pearlbaileyhighschool.common.model.web.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import javax.validation.ConstraintViolationException


@Component
class ErrorResponseFactory {

    fun badRequest(ex: MethodArgumentNotValidException, message: String? = null) =
        ErrorResponse(HttpStatus.BAD_REQUEST.value(), message ?: VALIDATION_ERROR_MESSAGE, buildListOfErrors(ex))

    fun badRequest(ex: ConstraintViolationException, message: String? = null) =
        ErrorResponse(HttpStatus.BAD_REQUEST.value(), message ?: VALIDATION_ERROR_MESSAGE, buildListOfErrors(ex))

    fun badRequest(ex: NotFoundException) = ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message!!)

    fun notFoundException(ex: NotFoundException) = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message!!)

    private fun buildListOfErrors(ex: MethodArgumentNotValidException) =
        ex.bindingResult.fieldErrors.map { err -> Error(err.field, err.defaultMessage) }

    private fun buildListOfErrors(ex: ConstraintViolationException) =
        ex.constraintViolations.map { Error(it.propertyPath.last().name, it.message) }

    companion object {
        private const val VALIDATION_ERROR_MESSAGE = "Encountered validation errors."
    }
}
