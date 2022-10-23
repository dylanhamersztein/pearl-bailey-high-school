package com.pearlbailey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PearlBaileyDepartmentManager

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PearlBaileyDepartmentManager>(*args)
}