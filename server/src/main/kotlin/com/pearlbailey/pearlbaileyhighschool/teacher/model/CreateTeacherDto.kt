package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class CreateTeacherDto(
    @field:NotBlank val firstName: String,
    val middleName: String?,
    @field:NotBlank val lastName: String,
    @field:NotNull @field:Past val dateOfBirth: LocalDate
)
