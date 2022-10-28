package com.pearlbailey.studentmanager.spring.config

import com.pearlbailey.studentmanager.coroutines.StudentRoutesConfig
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
@Profile("coroutines")
@Import(StudentRoutesConfig::class)
@ComponentScan(basePackages = ["com.pearlbailey.studentmanager.coroutines"])
class CoroutinesConfig
