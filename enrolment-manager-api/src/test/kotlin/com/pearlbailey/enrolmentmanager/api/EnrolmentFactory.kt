package com.pearlbailey.enrolmentmanager.api

import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import com.pearlbailey.enrolmentmanager.api.model.GetEnrolmentResponseDto
import com.pearlbailey.enrolmentmanager.api.model.PatchEnrolmentDto

object EnrolmentFactory {
    fun getCreateEnrolmentDto(
        studentId: Int? = getRandomNumber(),
        courseId: Int? = getRandomNumber()
    ) = CreateEnrolmentDto(studentId, courseId)

    fun getPatchEnrolmentDto(
        studentId: Int? = getRandomNumber(),
        courseId: Int? = getRandomNumber()
    ) = PatchEnrolmentDto(studentId, courseId)

    fun getEnrolment(
        id: Int = getRandomNumber(),
        studentId: Int? = getRandomNumber(),
        courseId: Int = getRandomNumber()
    ) = Enrolment().apply {
        this.id = id
        this.studentId = studentId
        this.courseId = courseId
    }

    fun getEnrolmentResponseDto(
        id: Int = getRandomNumber(),
        studentId: Int = getRandomNumber(),
        headOfEnrolmentId: Int = getRandomNumber()
    ) = GetEnrolmentResponseDto(id, studentId, headOfEnrolmentId)

    private fun getRandomNumber() = (1..100).random()
}