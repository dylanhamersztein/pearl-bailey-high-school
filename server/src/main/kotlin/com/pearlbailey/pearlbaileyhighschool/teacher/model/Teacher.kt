package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

@Entity
class Teacher(
    val firstName: String,
    @Column(nullable = true) val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    @Id @GeneratedValue(strategy = IDENTITY) val id: Int? = null
)

data class TeacherResponseDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate
)

fun Teacher.toTeacherResponseDto() = TeacherResponseDto(firstName, middleName, lastName, dateOfBirth)

data class CreateTeacherDto(
    @field:NotBlank val firstName: String,
    val middleName: String?,
    @field:NotBlank val lastName: String,
    @field:NotNull @field:Past val dateOfBirth: LocalDate
)

data class CreateTeacherResponse(val id: Int)

fun Int.toCreateTeacherResponseDto() = CreateTeacherResponse(this)

fun CreateTeacherDto.toTeacher() = Teacher(firstName, middleName, lastName, dateOfBirth)

data class PatchTeacherDto(
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
)