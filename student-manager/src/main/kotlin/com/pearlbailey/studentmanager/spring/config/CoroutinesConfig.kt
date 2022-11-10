package com.pearlbailey.studentmanager.spring.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.config.EnableWebFlux

@Profile("coroutines")
@Configuration
@EnableWebFlux
class CoroutinesConfig
