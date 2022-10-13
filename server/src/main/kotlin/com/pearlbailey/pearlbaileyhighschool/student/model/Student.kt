package com.pearlbailey.pearlbaileyhighschool.student.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

@Entity
@Table(name = "students")
class Student {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null

    @Column(name = "middle_name", nullable = true)
    var middleName: String? = null

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null

    @Column(name = "date_of_birth", nullable = false)
    var dateOfBirth: LocalDate? = null

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    var status: StudentStatus? = null
}

data class StudentResponseDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val status: StudentStatus
)

fun Student.toStudentResponseDto() = StudentResponseDto(firstName!!, middleName, lastName!!, dateOfBirth!!, status!!)

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

data class CreateStudentResponse(val id: Int)

fun Int.toCreateStudentResponseDto() = CreateStudentResponse(this)

fun CreateStudentDto.toStudent() = Student().apply {
    this.firstName = this@toStudent.firstName
    this.middleName = this@toStudent.middleName
    this.lastName = this@toStudent.lastName
    this.dateOfBirth = this@toStudent.dateOfBirth
    this.status = this@toStudent.status
}

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
