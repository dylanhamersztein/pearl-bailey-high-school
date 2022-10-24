package com.pearlbailey.enrolmentmanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.enrolmentmanager.api.EnrolmentConstants.ENROLLMENTS_RESOURCE_PATH
import com.pearlbailey.enrolmentmanager.api.EnrolmentFactory
import com.pearlbailey.enrolmentmanager.api.service.EnrolmentService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(EnrolmentEndpoint::class)
class EnrolmentEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var enrolmentService: EnrolmentService

    @Test
    fun `POST - should return 400 when student id is null`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto(studentId = null)

        doPost(ENROLLMENTS_RESOURCE_PATH, createEnrolmentDto)
            .verifyBadRequestOnPost(ENROLLMENTS_RESOURCE_PATH, "studentId", "must not be null")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `POST - should return 400 when student id is negative`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto(studentId = -1)

        doPost(ENROLLMENTS_RESOURCE_PATH, createEnrolmentDto)
            .verifyBadRequestOnPost(ENROLLMENTS_RESOURCE_PATH, "studentId", "must be greater than 0")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `POST - should return 400 when course id is null`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto(courseId = null)

        doPost(ENROLLMENTS_RESOURCE_PATH, createEnrolmentDto)
            .verifyBadRequestOnPost(ENROLLMENTS_RESOURCE_PATH, "courseId", "must not be null")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `POST - should return 400 when course id is negative`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto(courseId = -1)

        doPost(ENROLLMENTS_RESOURCE_PATH, createEnrolmentDto)
            .verifyBadRequestOnPost(ENROLLMENTS_RESOURCE_PATH, "courseId", "must be greater than 0")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `PATCH - should return 400 when student id is negative`() {
        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto(studentId = -1)

        doPatch("$ENROLLMENTS_RESOURCE_PATH/1", patchEnrolmentDto)
            .verifyBadRequestOnPatch("$ENROLLMENTS_RESOURCE_PATH/1", "studentId", "must be greater than 0")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `PATCH - should return 400 when course id is negative`() {
        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto(courseId = -1)

        doPatch("$ENROLLMENTS_RESOURCE_PATH/1", patchEnrolmentDto)
            .verifyBadRequestOnPatch("$ENROLLMENTS_RESOURCE_PATH/1", "courseId", "must be greater than 0")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `PATCH - should return 404 when enrolment not found`() {
        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto()

        doPatch("$ENROLLMENTS_RESOURCE_PATH/1", patchEnrolmentDto)
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Enrolment with id 1 not found."))

        verify(enrolmentService).updateEnrolment(1, patchEnrolmentDto)
    }

    @Test
    fun `GET - should return 400 when enrolment id is negative`() {
        mvc.perform(get("$ENROLLMENTS_RESOURCE_PATH/-1"))
            .verifyBadRequestOnGet("$ENROLLMENTS_RESOURCE_PATH/-1", "id", "must be greater than 0")

        verifyNoInteractions(enrolmentService)
    }

    @Test
    fun `GET - should return 404 when enrolment not found`() {
        mvc.perform(get("$ENROLLMENTS_RESOURCE_PATH/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Enrolment with id 1 not found."))

        verify(enrolmentService).getEnrolmentById(1)
    }

}