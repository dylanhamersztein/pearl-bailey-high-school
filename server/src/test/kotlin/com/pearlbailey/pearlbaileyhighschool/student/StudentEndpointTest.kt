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
    fun `POST - should return 400 when first name is blank`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(firstName = "")
        mvc.perform(post(STUDENTS).contentType(APPLICATION_JSON).content(toJson(createStudentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /students"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("firstName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when last name is blank`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(lastName = "")
        mvc.perform(post(STUDENTS).contentType(APPLICATION_JSON).content(toJson(createStudentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /students"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("lastName"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in present`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(dateOfBirth = LocalDate.now())
        mvc.perform(post(STUDENTS).contentType(APPLICATION_JSON).content(toJson(createStudentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /students"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("dateOfBirth"))
            .andExpect(jsonPath("$.errors[0].error").value("must be a past date"))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in future`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(dateOfBirth = LocalDate.now().plusDays(1))
        mvc.perform(post(STUDENTS).contentType(APPLICATION_JSON).content(toJson(createStudentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /students"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("dateOfBirth"))
            .andExpect(jsonPath("$.errors[0].error").value("must be a past date"))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 200 when student information is valid`() {
        val createStudentDto = StudentFactory.getCreateStudentDto()

        whenever(studentService.createStudent(createStudentDto)).thenReturn(1)

        mvc.perform(
            post(STUDENTS).contentType(APPLICATION_JSON).content(toJson(createStudentDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(studentService).createStudent(createStudentDto)
    }

    @Test
    fun `PATCH - should return 400 when id is negative`() {
        mvc.perform(patch("$STUDENTS/-1"))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `PATCH - should return 400 when id is 0`() {
        mvc.perform(patch("$STUDENTS/0"))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `GET - should return 404 on search when first and last name are null`() {
        mvc.perform(get("$STUDENTS/search"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("One of [firstName, lastName] must be provided as URL parameter."))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `GET - should return 404 on search when first and last name are empty`() {
        mvc.perform(get("$STUDENTS/search").param("firstName", "").param("lastName", ""))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("One of [firstName, lastName] must be provided as URL parameter."))

        verifyNoInteractions(studentService)
    }

    @Test
    fun `GET - should return 200 on search when first name is null`() {
        whenever(studentService.searchStudentByName(anyOrNull(), anyOrNull()))
            .thenReturn(StudentFactory.getStudent())

        mvc.perform(get("$STUDENTS/search").param("lastName", "Smith"))
            .andExpect(status().isOk)

        verify(studentService).searchStudentByName(null, "Smith")
    }

    @Test
    fun `GET - should return 200 on search when last name is null`() {
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