package com.pearlbailey.teachermanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.teachermanager.api.model.db.Teacher
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto

object TeacherMapper {

    fun CreateTeacherDto.toTeacher() = Teacher().apply {
        this.firstName = this@toTeacher.firstName
        this.middleName = this@toTeacher.middleName
        this.lastName = this@toTeacher.lastName
        this.dateOfBirth = this@toTeacher.dateOfBirth
    }

    fun Int.toCreateTeacherResponseDto() = CreatedResourceResponse(this)

    fun Teacher.toTeacherResponseDto() = GetTeacherResponseDto(id!!, firstName!!, middleName, lastName!!, dateOfBirth!!)
}
