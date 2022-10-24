package com.pearlbailey.departmentmanager.spring.config

import com.pearlbailey.departmentmanager.downstream.DefaultTeacherWebService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebServiceConfig(private val restTemplate: RestTemplate) {

    @Value("\${downstream.teacher-manager.url}")
    private lateinit var teacherManagerUrl: String

    @Bean
    fun teacherWebService() = DefaultTeacherWebService(teacherManagerUrl, restTemplate)
}