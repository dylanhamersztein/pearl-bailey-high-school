package com.pearlbailey.enrolmentmanager.api.service

import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.GetEnrolmentResponseDto
import com.pearlbailey.enrolmentmanager.api.model.PatchEnrolmentDto

interface EnrolmentWebService {
    fun createEnrolment(createEnrolmentDto: CreateEnrolmentDto): Int
    fun updateEnrolment(id: Int, patchEnrolmentDto: PatchEnrolmentDto): GetEnrolmentResponseDto?
    fun getEnrolmentById(id: Int): GetEnrolmentResponseDto?
}
