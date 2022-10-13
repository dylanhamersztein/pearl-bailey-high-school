package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.student.util.StudentFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(StudentEndpoint::class)
internal class StudentEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var studentService: StudentService

    @Test
    fun `should return 400 when first name is blank on create`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(firstName = "")
        mvc.perform(post(STUDENTS, objectMapper.writeValueAsString(createStudentDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 when last name is blank on create`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(lastName = "")
        mvc.perform(post(STUDENTS, objectMapper.writeValueAsString(createStudentDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 when date of birth is in present on create`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(birthDate = LocalDate.now())
        mvc.perform(post(STUDENTS, objectMapper.writeValueAsString(createStudentDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 when date of birth is in future on create`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(birthDate = LocalDate.now().plusDays(1))
        mvc.perform(post(STUDENTS, objectMapper.writeValueAsString(createStudentDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 200 on create when student information is valid`() {
        val createStudentDto = StudentFactory.getCreateStudentDto()

        whenever(studentService.createStudent(createStudentDto)).thenReturn(1)

        mvc.perform(
            post(STUDENTS).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(createStudentDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(studentService).createStudent(createStudentDto)
    }

    @Test
    fun `should return 400 on patch when id is negative`() {
        mvc.perform(patch("$STUDENTS/-1"))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 on patch when id is 0`() {
        mvc.perform(patch("$STUDENTS/0"))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 on search when first and last name are null`() {
        mvc.perform(get("$STUDENTS/search"))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 400 on search when first and last name are empty`() {
        mvc.perform(get("$STUDENTS/search").param("firstName", "").param("lastName", ""))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `should return 200 on search when first name is null`() {
        whenever(studentService.searchStudentByName(anyOrNull(), anyOrNull()))
            .thenReturn(StudentFactory.getStudent())

        mvc.perform(get("$STUDENTS/search").param("lastName", "Smith"))
            .andExpect(status().isOk)

        verify(studentService).searchStudentByName(null, "Smith")
    }

    @Test
    fun `should return 200 on search when last name is null`() {
        whenever(studentService.searchStudentByName(anyOrNull(), anyOrNull()))
            .thenReturn(StudentFactory.getStudent())

        mvc.perform(get("$STUDENTS/search").queryParam("firstName", "Steve"))
            .andExpect(status().isOk)

        verify(studentService).searchStudentByName("Steve", null)
    }

    companion object {
        private const val STUDENTS = "/students"
    }
}