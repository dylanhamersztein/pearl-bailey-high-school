package com.pearlbailey.pearlbaileyhighschool.student.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class CreateStudentDto(
    @field:NotBlank
    val firstName: String?,

    val middleName: String?,

    @field:NotBlank
    val lastName: String?,

    @field:Past
    @field:NotNull
    @field:JsonDeserialize(using = LocalDateDeserializer::class)
    val dateOfBirth: LocalDate?,

    @field:NotNull
    val status: StudentStatus?
)

fun CreateStudentDto.toStudent() = Student().apply {
    this.firstName = this@toStudent.firstName
    this.middleName = this@toStudent.middleName
    this.lastName = this@toStudent.lastName
    this.dateOfBirth = this@toStudent.dateOfBirth
    this.status = this@toStudent.status
}
