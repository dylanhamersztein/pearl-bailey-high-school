package com.pearlbailey.coursemilestonemanager.spring.config

import com.pearlbailey.coursemilestonemanager.downstream.DefaultCourseWebService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebServiceConfig(private val restTemplate: RestTemplate) {

    @Value("\${downstream.course-manager.url}")
    private lateinit var courseManagerUrl: String

    @Bean
    fun defaultCourseWebService() = DefaultCourseWebService(courseManagerUrl, restTemplate)
}