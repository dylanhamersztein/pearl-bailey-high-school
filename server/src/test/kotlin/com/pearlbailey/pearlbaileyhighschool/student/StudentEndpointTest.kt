package com.pearlbailey.pearlbaileyhighschool.student

import com.fasterxml.jackson.databind.ObjectMapper
import com.pearlbailey.pearlbaileyhighschool.student.util.StudentFactory
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(StudentEndpoint::class)
internal class StudentEndpointTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
    fun `should return 200 when student information is valid`() {
        val createStudentDto = StudentFactory.getCreateStudentDto()

        `when`(studentService.createStudent(createStudentDto)).thenReturn(1)

        mvc.perform(
            post(STUDENTS).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(createStudentDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(studentService).createStudent(createStudentDto)
    }

    @Test
    fun `should return 400 on search when first and last name are null`() {
        mvc.perform(get("$STUDENTS/search"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 200 on search when first name is null`() {
        whenever(studentService.searchStudentByName(anyOrNull(), anyOrNull()))
            .thenReturn(StudentFactory.getStudent())

        mvc.perform(get("$STUDENTS/search").queryParam("lastName", "Smith"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should return 200 on search when last name is null`() {
        whenever(studentService.searchStudentByName(anyOrNull(), anyOrNull()))
            .thenReturn(StudentFactory.getStudent())

        mvc.perform(get("$STUDENTS/search").queryParam("firstName", "Steve"))
            .andExpect(status().isOk)
    }

    companion object {
        private const val STUDENTS = "/students"
    }
}