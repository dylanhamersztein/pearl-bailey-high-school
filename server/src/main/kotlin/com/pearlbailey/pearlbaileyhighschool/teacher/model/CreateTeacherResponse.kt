package com.pearlbailey.pearlbaileyhighschool.teacher.model

data class CreateTeacherResponse(val id: Int)

fun Int.toCreateTeacherResponseDto() = CreateTeacherResponse(this)
