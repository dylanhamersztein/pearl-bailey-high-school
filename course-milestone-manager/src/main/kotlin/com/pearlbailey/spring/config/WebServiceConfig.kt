package com.pearlbailey.spring.config

import com.pearlbailey.coursemanager.api.service.impl.DefaultCourseWebService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebServiceConfig(private val restTemplate: RestTemplate) {

    @Value("\${downstream.course-manager.url}")
    private lateinit var courseManagerUrl: String

    @Bean
    fun courseWebService() = DefaultCourseWebService(courseManagerUrl, restTemplate)
}