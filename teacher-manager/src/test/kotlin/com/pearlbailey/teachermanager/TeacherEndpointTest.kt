package com.pearlbailey.teachermanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.teachermanager.api.TeacherConstants.TEACHERS_RESOURCE_PATH
import com.pearlbailey.teachermanager.api.TeacherFactory
import com.pearlbailey.teachermanager.api.service.TeacherService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(TeacherEndpoint::class)
internal class TeacherEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var teacherService: TeacherService

    @Test
    fun `POST - should return 400 when first name is blank`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(firstName = "")

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "firstName", "must not be blank")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when first name is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(firstName = null)

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "firstName", "must not be blank")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when last name is blank`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(lastName = "")

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "lastName", "must not be blank")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when last name is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(lastName = null)

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "lastName", "must not be blank")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = null)

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "dateOfBirth", "must not be null")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in present`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = LocalDate.now())

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "dateOfBirth", "must be a past date")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in future`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = LocalDate.now().plusDays(1))

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .verifyBadRequestOnPost(TEACHERS_RESOURCE_PATH, "dateOfBirth", "must be a past date")

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 200 when teacher information is valid`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto()

        whenever(teacherService.createTeacher(createTeacherDto)).thenReturn(1)

        doPost(TEACHERS_RESOURCE_PATH, createTeacherDto)
            .andExpect(status().isCreated).andExpect(jsonPath("$.id").value(1))

        verify(teacherService).createTeacher(createTeacherDto)
    }

}