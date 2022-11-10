package com.pearlbailey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
class PearlBaileyStudentManager

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PearlBaileyStudentManager>(*args)
}
