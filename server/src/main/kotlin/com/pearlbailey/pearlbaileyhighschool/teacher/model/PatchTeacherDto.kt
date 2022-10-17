package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate

data class PatchTeacherDto(
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
)
