package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate

data class GetTeacherResponseDto(
    val firstName: String, val middleName: String?, val lastName: String, val dateOfBirth: LocalDate
)
