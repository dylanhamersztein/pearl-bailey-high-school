package com.pearlbailey.studentmanager.api

import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.Student
import com.pearlbailey.studentmanager.api.model.StudentStatus
import com.pearlbailey.studentmanager.api.model.StudentStatus.ENROLLED
import com.pearlbailey.studentmanager.api.model.StudentStatus.PROSPECT
import java.time.LocalDate

object StudentFactory {
    fun getCreateStudentDto(
        firstName: String = "Snot",
        middleName: String? = null,
        lastName: String = "Lonstein",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = PROSPECT
    ) = CreateStudentDto(firstName, middleName, lastName, dateOfBirth, status)

    fun getPatchStudentDto(
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = ENROLLED
    ) = PatchStudentDto(firstName, middleName, lastName, dateOfBirth, status)

    fun getStudent(
        id: Int = 1,
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = ENROLLED
    ) = Student().apply {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.dateOfBirth = dateOfBirth
        this.status = status
    }
}