package com.pearlbailey.studentmanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.Student
import com.pearlbailey.studentmanager.api.model.StudentResponseDto

object StudentMapper {

    fun CreateStudentDto.toStudent() = Student().apply {
        this.firstName = this@toStudent.firstName
        this.middleName = this@toStudent.middleName
        this.lastName = this@toStudent.lastName
        this.dateOfBirth = this@toStudent.dateOfBirth
        this.status = this@toStudent.status
    }

    fun Int.toCreateStudentResponseDto() = CreatedResourceResponse(this)

    fun Student.toStudentResponseDto() =
        StudentResponseDto(firstName!!, middleName, lastName!!, dateOfBirth!!, status!!)
}
