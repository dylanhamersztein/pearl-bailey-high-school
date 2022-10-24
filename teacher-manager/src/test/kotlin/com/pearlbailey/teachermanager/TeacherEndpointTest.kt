package com.pearlbailey.teachermanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.teachermanager.api.TeacherFactory
import com.pearlbailey.teachermanager.api.TeacherService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("firstName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when first name is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(firstName = null)
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("firstName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when last name is blank`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(lastName = "")
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("lastName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when last name is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(lastName = null)
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("lastName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is null`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = null)
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("dateOfBirth"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in present`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = LocalDate.now())
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("dateOfBirth"))
            .andExpect(jsonPath("$.errors[0].error").value("must be a past date"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in future`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(dateOfBirth = LocalDate.now().plusDays(1))
        mvc.perform(post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /teachers"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("dateOfBirth"))
            .andExpect(jsonPath("$.errors[0].error").value("must be a past date"))

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `POST - should return 200 when teacher information is valid`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto()

        whenever(teacherService.createTeacher(createTeacherDto)).thenReturn(1)

        mvc.perform(
            post(TEACHERS).contentType(APPLICATION_JSON).content(toJson(createTeacherDto))
        ).andExpect(status().isCreated).andExpect(jsonPath("$.id").value(1))

        verify(teacherService).createTeacher(createTeacherDto)
    }

    companion object {
        private const val TEACHERS = "/teachers"
    }
}