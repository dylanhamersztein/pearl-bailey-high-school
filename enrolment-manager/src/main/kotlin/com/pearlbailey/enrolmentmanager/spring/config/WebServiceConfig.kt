package com.pearlbailey.enrolmentmanager.spring.config

import com.pearlbailey.enrolmentmanager.downstream.DefaultCourseWebService
import com.pearlbailey.enrolmentmanager.downstream.DefaultStudentWebService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebServiceConfig(private val restTemplate: RestTemplate) {

    @Value("\${downstream.student-manager.url}")
    private lateinit var studentManagerUrl: String

    @Value("\${downstream.course-manager.url}")
    private lateinit var courseManagerUrl: String

    @Bean
    fun defaultStudentWebService() = DefaultStudentWebService(studentManagerUrl, restTemplate)

    @Bean
    fun defaultCourseWebService() = DefaultCourseWebService(courseManagerUrl, restTemplate)
}