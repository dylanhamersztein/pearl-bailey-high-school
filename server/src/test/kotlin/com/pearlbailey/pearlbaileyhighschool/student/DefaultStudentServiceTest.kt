package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import com.pearlbailey.pearlbaileyhighschool.student.util.StudentFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class DefaultStudentServiceTest {

    private lateinit var studentService: DefaultStudentService

    @MockBean
    private lateinit var studentRepository: StudentRepository

    @BeforeEach
    fun beforeEach() {
        studentService = DefaultStudentService(studentRepository)
    }

    @Test
    fun `should save a student in a repository`() {
        `when`(studentRepository.save(any())).thenReturn(StudentFactory.getStudent())

        studentService.createStudent(StudentFactory.getCreateStudentDto())

        verify(studentRepository).save(any())
    }

    @Test
    fun `should update a student in a repository when it exists`() {
        val studentId = 1
        val originalStudent = StudentFactory.getCreateStudentDto().toStudent(studentId)
        val patchStudentDto = StudentFactory.getPatchStudentDto()

        whenever(studentRepository.findById(studentId)).thenReturn(Optional.of(originalStudent))

        studentService.updateStudent(studentId, patchStudentDto)

        inOrder(studentRepository) {
            verify(studentRepository).findById(studentId)
            verify(studentRepository).save(check {
                assertThat(it.id).isEqualTo(studentId)
                assertThat(it.firstName).isEqualTo(patchStudentDto.firstName)
                assertThat(it.middleName).isEqualTo(patchStudentDto.middleName)
                assertThat(it.lastName).isEqualTo(patchStudentDto.lastName)
                assertThat(it.dateOfBirth).isEqualTo(patchStudentDto.dateOfBirth)
                assertThat(it.status).isEqualTo(patchStudentDto.status)
            })
        }
    }

    @Test
    fun `should not do anything when student does not exist`() {
        whenever(studentRepository.findById(anyOrNull())).thenReturn(Optional.empty())

        studentService.updateStudent(1, StudentFactory.getPatchStudentDto())

        verify(studentRepository).findById(1)
        verifyNoMoreInteractions(studentRepository)
    }

    @Test
    fun `should search by both first and last name when neither are null`() {
        studentService.searchStudentByName("Steve", "Smith")
        verify(studentRepository).findByFirstNameAndLastName("Steve", "Smith")
    }

    @Test
    fun `should search by first name when last name is null`() {
        studentService.searchStudentByName("Steve", null)
        verify(studentRepository).findByFirstName("Steve")
    }

    @Test
    fun `should search by last name when first name is null`() {
        studentService.searchStudentByName(null, "Smith")
        verify(studentRepository).findByLastName("Smith")
    }

    private fun CreateStudentDto.toStudent(id: Int) = Student(
        firstName,
        middleName,
        lastName,
        dateOfBirth,
        status,
        id
    )
}