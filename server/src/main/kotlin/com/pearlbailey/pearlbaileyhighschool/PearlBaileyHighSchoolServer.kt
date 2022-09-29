package com.pearlbailey.pearlbaileyhighschool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PearlBaileyHighSchoolServer

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PearlBaileyHighSchoolServer>(*args)
}
