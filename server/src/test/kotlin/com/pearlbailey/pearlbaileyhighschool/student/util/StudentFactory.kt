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
        birthDate: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.PROSPECT
    ) = CreateStudentDto(firstName, middleName, lastName, birthDate, status)

    fun getPatchStudentDto(
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        birthDate: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.ENROLLED
    ) = PatchStudentDto(firstName, middleName, lastName, birthDate, status)

    fun getStudent(
        id: Int = 1,
        firstName: String = "Steven",
        middleName: String? = "Anita",
        lastName: String = "Smith",
        birthDate: LocalDate = LocalDate.now().minusYears(14),
        status: StudentStatus = StudentStatus.ENROLLED
    ) = Student().apply {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.dateOfBirth = birthDate
        this.status = status
    }
}