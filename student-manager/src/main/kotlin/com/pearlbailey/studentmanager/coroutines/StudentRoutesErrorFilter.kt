package com.pearlbailey.studentmanager.coroutines

import com.pearlbailey.commontools.web.ErrorResponseFactory
import com.pearlbailey.studentmanager.api.model.StudentNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import javax.annotation.Priority

@Component
@Priority(0) // needs to fire before default webflux error handler
class StudentRoutesErrorFilter(private val errorResponseFactory: ErrorResponseFactory) : ErrorWebExceptionHandler {

    private val logger = LoggerFactory.getLogger(StudentRoutesErrorFilter::class.java)

    override fun handle(exchange: ServerWebExchange, ex: Throwable) = when (ex) {
        is StudentNotFoundException -> studentNotFoundException(ex)
        else -> {
            logger.error("Encountered exception.", ex)
            internalServerError()
        }
    }
        .flatMap { it.writeTo(exchange, ResponseContextInstance) }
        .then()

    private fun studentNotFoundException(ex: StudentNotFoundException) = ServerResponse.status(NOT_FOUND)
        .bodyValue(errorResponseFactory.notFoundException(ex))

    private fun internalServerError() = ServerResponse.status(INTERNAL_SERVER_ERROR).build()

    private object ResponseContextInstance : ServerResponse.Context {
        val strategies: HandlerStrategies = HandlerStrategies.withDefaults()
        override fun messageWriters(): List<HttpMessageWriter<*>> = strategies.messageWriters()
        override fun viewResolvers(): List<ViewResolver> = strategies.viewResolvers()
    }
}