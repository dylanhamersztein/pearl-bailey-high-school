package com.pearlbailey.pearlbaileyhighschool.courses.model

data class CreateCourseResponse(val id: Int)

fun Int.toCreateCourseResponseDto() = CreateCourseResponse(this)