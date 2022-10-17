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

fun CreateTeacherDto.toTeacher() = Teacher().apply {
    this.firstName = this@toTeacher.firstName
    this.middleName = this@toTeacher.middleName
    this.lastName = this@toTeacher.lastName
    this.dateOfBirth = this@toTeacher.dateOfBirth
}
