package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.teacher.util.TeacherFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(TeacherEndpoint::class)
internal class TeacherEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var teacherService: TeacherService

    @Test
    fun `should return 400 when first name is blank on create`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(firstName = "")
        mvc.perform(post(TEACHERS, objectMapper.writeValueAsString(createTeacherDto))).andExpect(status().isBadRequest)

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `should return 400 when last name is blank on create`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(lastName = "")
        mvc.perform(post(TEACHERS, objectMapper.writeValueAsString(createTeacherDto))).andExpect(status().isBadRequest)

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `should return 400 when date of birth is in present on create`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(birthDate = LocalDate.now())
        mvc.perform(post(TEACHERS, objectMapper.writeValueAsString(createTeacherDto))).andExpect(status().isBadRequest)

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `should return 400 when date of birth is in future on create`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto(birthDate = LocalDate.now().plusDays(1))
        mvc.perform(post(TEACHERS, objectMapper.writeValueAsString(createTeacherDto))).andExpect(status().isBadRequest)

        verifyNoInteractions(teacherService)
    }

    @Test
    fun `should return 200 when teacher information is valid`() {
        val createTeacherDto = TeacherFactory.getCreateTeacherDto()

        whenever(teacherService.createTeacher(createTeacherDto)).thenReturn(1)

        mvc.perform(
            post(TEACHERS).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(createTeacherDto))
        ).andExpect(status().isCreated).andExpect(jsonPath("$.id").value(1))

        verify(teacherService).createTeacher(createTeacherDto)
    }

    @Test
    fun `should return 400 on search when first and last name are null`() {
        mvc.perform(get("$TEACHERS/search")).andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 on search when first and last name are blank`() {
        mvc.perform(get("$TEACHERS/search").param("firstName", "").param("lastName", ""))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 200 on search when first name is null`() {
        whenever(teacherService.searchTeacherByName(anyOrNull(), anyOrNull())).thenReturn(TeacherFactory.getTeacher())

        mvc.perform(get("$TEACHERS/search").queryParam("lastName", "Smith")).andExpect(status().isOk)

        verify(teacherService).searchTeacherByName(null, "Smith")
    }

    @Test
    fun `should return 200 on search when last name is null`() {
        whenever(teacherService.searchTeacherByName(anyOrNull(), anyOrNull())).thenReturn(TeacherFactory.getTeacher())

        mvc.perform(get("$TEACHERS/search").queryParam("firstName", "Steve")).andExpect(status().isOk)

        verify(teacherService).searchTeacherByName("Steve", null)
    }

    companion object {
        private const val TEACHERS = "/teachers"
    }
}