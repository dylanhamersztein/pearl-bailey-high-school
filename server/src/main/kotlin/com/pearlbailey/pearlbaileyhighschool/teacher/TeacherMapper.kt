package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.GetTeacherResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher

object TeacherMapper {

    fun CreateTeacherDto.toTeacher() = Teacher().apply {
        this.firstName = this@toTeacher.firstName
        this.middleName = this@toTeacher.middleName
        this.lastName = this@toTeacher.lastName
        this.dateOfBirth = this@toTeacher.dateOfBirth
    }

    fun Int.toCreateTeacherResponseDto() = CreatedResourceResponse(this)

    fun Teacher.toTeacherResponseDto() = GetTeacherResponseDto(firstName!!, middleName, lastName!!, dateOfBirth!!)
}
