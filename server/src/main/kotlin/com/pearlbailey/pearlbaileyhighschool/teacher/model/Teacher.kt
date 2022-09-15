package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

@Entity
@Table(name = "teachers")
class Teacher {

    var firstName: String? = null

    @Column(nullable = true)
    var middleName: String? = null


    var lastName: String? = null

    @Column(nullable = false)
    var dateOfBirth: LocalDate? = null

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null

}

data class TeacherResponseDto(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val dateOfBirth: LocalDate
)

fun Teacher.toTeacherResponseDto() = TeacherResponseDto(firstName!!, middleName, lastName!!, dateOfBirth!!)

data class CreateTeacherDto(
    @field:NotBlank val firstName: String,
    val middleName: String?,
    @field:NotBlank val lastName: String,
    @field:NotNull @field:Past val dateOfBirth: LocalDate
)

data class CreateTeacherResponse(val id: Int)

fun Int.toCreateTeacherResponseDto() = CreateTeacherResponse(this)

fun CreateTeacherDto.toTeacher() = Teacher().apply {
    this.firstName = this@toTeacher.firstName
    this.middleName = this@toTeacher.middleName
    this.lastName = this@toTeacher.lastName
    this.dateOfBirth = this@toTeacher.dateOfBirth
}

data class PatchTeacherDto(
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
)