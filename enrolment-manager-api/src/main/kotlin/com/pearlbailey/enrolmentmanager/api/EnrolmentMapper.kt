package com.pearlbailey.enrolmentmanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import com.pearlbailey.enrolmentmanager.api.model.GetEnrolmentResponseDto

object EnrolmentMapper {

    fun Int.toCreateEnrolmentResponseDto() = CreatedResourceResponse(this)

    fun CreateEnrolmentDto.toEnrolment() = Enrolment().apply {
        this.studentId = this@toEnrolment.studentId
        this.courseId = this@toEnrolment.courseId
    }

    fun Enrolment.toEnrolmentResponseDto() = GetEnrolmentResponseDto(id!!, studentId!!, courseId!!)

}
