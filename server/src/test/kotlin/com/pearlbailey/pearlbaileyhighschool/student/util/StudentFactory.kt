package com.pearlbailey.pearlbaileyhighschool.student.util

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import com.pearlbailey.pearlbaileyhighschool.student.model.StudentStatus
import java.time.LocalDate

object StudentFactory {
    fun getCreateStudentDto(
        firstName: String = "Snot",
        middleName: String? = null,
        lastName: String = "Lonstein",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.PROSPECT
    ) = CreateStudentDto(firstName, middleName, lastName, dateOfBirth, status)

    fun getPatchStudentDto(
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.ENROLLED
    ) = PatchStudentDto(firstName, middleName, lastName, dateOfBirth, status)

    fun getStudent(
        id: Int = 1,
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.ENROLLED
    ) = Student().apply {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.dateOfBirth = dateOfBirth
        this.status = status
    }
}