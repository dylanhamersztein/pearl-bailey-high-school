package com.pearlbailey.pearlbaileyhighschool.spring.config

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

@Configuration
class JacksonConfig {

    @Bean
    fun javaTimeModule() = JavaTimeModule().apply {
        addDeserializer(LocalDate::class.java, LocalDateDeserializer(ISO_LOCAL_DATE))
        addSerializer(LocalDate::class.java, LocalDateSerializer(ISO_LOCAL_DATE))
    }
}
