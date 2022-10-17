package com.pearlbailey.pearlbaileyhighschool.student.model

data class CreateStudentResponse(val id: Int)

fun Int.toCreateStudentResponseDto() = CreateStudentResponse(this)