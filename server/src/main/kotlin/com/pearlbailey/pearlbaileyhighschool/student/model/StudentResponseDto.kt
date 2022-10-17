package com.pearlbailey.pearlbaileyhighschool.student.model

import java.time.LocalDate

data class StudentResponseDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val status: StudentStatus
)
