package com.pearlbailey

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PearlBaileyCourseMilestoneManager

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PearlBaileyCourseMilestoneManager>(*args)
}