package com.pearlbailey.pearlbaileyhighschool.teacher.util

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import java.time.LocalDate

object TeacherFactory {
    fun getCreateTeacherDto(
        firstName: String? = "Snot",
        middleName: String? = null,
        lastName: String? = "Lonstein",
        dateOfBirth: LocalDate? = LocalDate.now().minusYears(14)
    ) = CreateTeacherDto(firstName, middleName, lastName, dateOfBirth)

    fun getPatchTeacherDto(
        firstName: String? = "Steven",
        middleName: String? = "Anita",
        lastName: String? = "Smith",
        dateOfBirth: LocalDate? = LocalDate.now().minusYears(14)
    ) = PatchTeacherDto(firstName, middleName, lastName, dateOfBirth)

    fun getTeacher(
        id: Int = 1,
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        birthDate: LocalDate = LocalDate.now().minusYears(14)
    ) = Teacher().apply {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.dateOfBirth = birthDate
    }
}