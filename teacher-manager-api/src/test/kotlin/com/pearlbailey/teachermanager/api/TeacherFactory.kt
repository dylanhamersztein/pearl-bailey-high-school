package com.pearlbailey.teachermanager.api

import com.pearlbailey.teachermanager.api.model.db.Teacher
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto
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
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14)
    ) = Teacher().apply {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.dateOfBirth = dateOfBirth
    }

    fun getTeacherResponseDto(
        id: Int = 1,
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14)
    ) = GetTeacherResponseDto(id, firstName, middleName, lastName, dateOfBirth)
}