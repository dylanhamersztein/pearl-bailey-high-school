package com.pearlbailey.pearlbaileyhighschool.student.model

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
class Student(
    val firstName: String,
    @Column(nullable = true) val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val status: StudentStatus,
    @Id @GeneratedValue(strategy = IDENTITY) val id: Int? = null
)

data class StudentResponseDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val status: StudentStatus
)

fun Student.toStudentResponseDto() = StudentResponseDto(firstName, middleName, lastName, dateOfBirth, status)

data class CreateStudentDto(
    @field:NotBlank val firstName: String,
    val middleName: String?,
    @field:NotBlank val lastName: String,
    @field:NotNull @field:Past val dateOfBirth: LocalDate,
    @field:NotNull val status: StudentStatus
)

data class CreateStudentResponse(val id: Int)

fun Int.toCreateStudentResponseDto() = CreateStudentResponse(this)

fun CreateStudentDto.toStudent() = Student(firstName, middleName, lastName, dateOfBirth, status)

data class PatchStudentDto(
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
    val status: StudentStatus?
)

enum class StudentStatus {
    PROSPECT, ENROLLED, GRADUATED, SUSPENDED, EXPELLED, DECEASED
}