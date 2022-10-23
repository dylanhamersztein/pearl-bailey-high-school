package com.pearlbailey.studentmanager

import com.pearlbailey.studentmanager.api.StudentFactory
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.Student
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
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
        whenever(studentRepository.save(ArgumentMatchers.any())).thenReturn(StudentFactory.getStudent())

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

    private fun CreateStudentDto.toStudent(id: Int) = Student().apply {
        this.id = id
        this.firstName = this@toStudent.firstName
        this.middleName = this@toStudent.middleName
        this.lastName = this@toStudent.lastName
        this.dateOfBirth = this@toStudent.dateOfBirth
        this.status = this@toStudent.status
    }
}