package com.pearlbailey.studentmanager.jpa

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.studentmanager.api.StudentConstants.STUDENTS_RESOURCE_PATH
import com.pearlbailey.studentmanager.api.StudentFactory
import com.pearlbailey.studentmanager.api.service.StudentService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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

        doPost(STUDENTS_RESOURCE_PATH, createStudentDto)
            .verifyBadRequestOnPost(STUDENTS_RESOURCE_PATH, "firstName", "must not be blank")

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when last name is blank`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(lastName = "")

        doPost(STUDENTS_RESOURCE_PATH, createStudentDto)
            .verifyBadRequestOnPost(STUDENTS_RESOURCE_PATH, "lastName", "must not be blank")

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in present`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(dateOfBirth = LocalDate.now())

        doPost(STUDENTS_RESOURCE_PATH, createStudentDto)
            .verifyBadRequestOnPost(STUDENTS_RESOURCE_PATH, "dateOfBirth", "must be a past date")

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 400 when date of birth is in future`() {
        val createStudentDto = StudentFactory.getCreateStudentDto(dateOfBirth = LocalDate.now().plusDays(1))

        doPost(STUDENTS_RESOURCE_PATH, createStudentDto)
            .verifyBadRequestOnPost(STUDENTS_RESOURCE_PATH, "dateOfBirth", "must be a past date")

        verifyNoInteractions(studentService)
    }

    @Test
    fun `POST - should return 200 when student information is valid`() {
        val createStudentDto = StudentFactory.getCreateStudentDto()

        whenever(studentService.createStudent(createStudentDto)).thenReturn(1)

        doPost(STUDENTS_RESOURCE_PATH, createStudentDto)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(studentService).createStudent(createStudentDto)
    }

    @Test
    fun `PATCH - should return 400 when id is negative`() {
        doPatch("$STUDENTS_RESOURCE_PATH/-1")
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

    @Test
    fun `PATCH - should return 400 when id is 0`() {
        doPatch("$STUDENTS_RESOURCE_PATH/0")
            .andExpect(status().isBadRequest)

        verifyNoInteractions(studentService)
    }

}