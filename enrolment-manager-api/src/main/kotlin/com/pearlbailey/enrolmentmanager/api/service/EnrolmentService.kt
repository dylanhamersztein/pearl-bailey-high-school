package com.pearlbailey.enrolmentmanager.api.service

import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import com.pearlbailey.enrolmentmanager.api.model.PatchEnrolmentDto

interface EnrolmentService {
    fun createEnrolment(createEnrolmentDto: CreateEnrolmentDto): Int
    fun updateEnrolment(id: Int, patchEnrolmentDto: PatchEnrolmentDto): Enrolment?
    fun getEnrolmentById(id: Int): Enrolment?
}
