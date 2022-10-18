package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import com.pearlbailey.pearlbaileyhighschool.student.model.StudentResponseDto

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
