package com.pearlbailey.studentmanager.coroutines

import com.pearlbailey.commontools.web.BaseErrorHandler
import com.pearlbailey.studentmanager.api.StudentConstants.STUDENTS_RESOURCE_PATH
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
@Profile("coroutines")
@Import(BaseErrorHandler::class)
class StudentRoutesConfig {

    @Bean
    fun studentRoutes(studentHandler: StudentHandler) = coRouter {
        STUDENTS_RESOURCE_PATH.nest {
            accept(APPLICATION_JSON).nest {
                GET("/{id}", studentHandler::getStudentById)

                contentType(APPLICATION_JSON).nest {
                    PATCH("/{id}", studentHandler::updateStudent)
                    POST("", studentHandler::createStudent)
                }
            }
        }
    }

}