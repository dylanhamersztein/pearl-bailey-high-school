package com.pearlbailey.enrolmentmanager.downstream

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.enrolmentmanager.api.EnrolmentConstants.ENROLLMENTS_RESOURCE_PATH
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.StudentResponseDto
import com.pearlbailey.studentmanager.api.service.StudentWebService
import org.springframework.web.client.RestTemplate

class DefaultStudentWebService(baseUrl: String, restTemplate: RestTemplate) :
    AbstractWebService(baseUrl, restTemplate), StudentWebService {

    override fun getResourceName() = ENROLLMENTS_RESOURCE_PATH

    override fun getStudentById(id: Int): StudentResponseDto? = getResourceById(id)

    override fun createStudent(createStudentDto: CreateStudentDto) = throw UnsupportedOperationException()

    override fun updateStudent(id: Int, patchStudentDto: PatchStudentDto) = throw UnsupportedOperationException()

}