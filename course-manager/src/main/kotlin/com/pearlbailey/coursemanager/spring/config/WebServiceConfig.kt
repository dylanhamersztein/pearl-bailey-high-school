package com.pearlbailey.coursemanager.spring.config

import com.pearlbailey.coursemanager.downstream.DefaultDepartmentWebService
import com.pearlbailey.coursemanager.downstream.DefaultTeacherWebService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class WebServiceConfig(private val restTemplate: RestTemplate) {

    @Value("\${downstream.teacher-manager.url}")
    private lateinit var teacherManagerUrl: String

    @Value("\${downstream.department-manager.url}")
    private lateinit var departmentManagerUrl: String

    @Bean
    fun teacherWebService() = DefaultTeacherWebService(teacherManagerUrl, restTemplate)

    @Bean
    fun departmentManagerService() = DefaultDepartmentWebService(departmentManagerUrl, restTemplate)

}