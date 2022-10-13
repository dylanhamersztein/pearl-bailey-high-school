package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import com.pearlbailey.pearlbaileyhighschool.teacher.util.TeacherFactory
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
internal class DefaultTeacherServiceTest {

    private lateinit var teacherService: DefaultTeacherService

    @MockBean
    private lateinit var teacherRepository: TeacherRepository

    @BeforeEach
    fun beforeEach() {
        teacherService = DefaultTeacherService(teacherRepository)
    }

    @Test
    fun `should save a teacher in a repository`() {
        whenever(teacherRepository.save(ArgumentMatchers.any())).thenReturn(TeacherFactory.getTeacher())

        teacherService.createTeacher(TeacherFactory.getCreateTeacherDto())

        verify(teacherRepository).save(any())
    }

    @Test
    fun `should update a teacher in a repository when it exists`() {
        val teacherId = 1
        val originalTeacher = TeacherFactory.getCreateTeacherDto().toTeacher(teacherId)
        val patchTeacherDto = TeacherFactory.getPatchTeacherDto()

        whenever(teacherRepository.findById(teacherId)).thenReturn(Optional.of(originalTeacher))

        teacherService.updateTeacher(teacherId, patchTeacherDto)

        inOrder(teacherRepository) {
            verify(teacherRepository).findById(teacherId)
            verify(teacherRepository).save(check {
                assertThat(it.id).isEqualTo(teacherId)
                assertThat(it.firstName).isEqualTo(patchTeacherDto.firstName)
                assertThat(it.middleName).isEqualTo(patchTeacherDto.middleName)
                assertThat(it.lastName).isEqualTo(patchTeacherDto.lastName)
                assertThat(it.dateOfBirth).isEqualTo(patchTeacherDto.dateOfBirth)
            })
        }
    }

    @Test
    fun `should not do anything when teacher does not exist`() {
        whenever(teacherRepository.findById(anyOrNull())).thenReturn(Optional.empty())

        teacherService.updateTeacher(1, TeacherFactory.getPatchTeacherDto())

        verify(teacherRepository).findById(1)
        verifyNoMoreInteractions(teacherRepository)
    }

    @Test
    fun `should search by both first and last name when neither are null`() {
        teacherService.searchTeacherByName("Steve", "Smith")
        verify(teacherRepository).findByFirstNameAndLastName("Steve", "Smith")
    }

    @Test
    fun `should search by first name when last name is null`() {
        teacherService.searchTeacherByName("Steve", null)
        verify(teacherRepository).findByFirstName("Steve")
    }

    @Test
    fun `should search by last name when first name is null`() {
        teacherService.searchTeacherByName(null, "Smith")
        verify(teacherRepository).findByLastName("Smith")
    }

    private fun CreateTeacherDto.toTeacher(id: Int) = Teacher().apply {
        this.id = id
        this.firstName = this@toTeacher.firstName
        this.middleName = this@toTeacher.middleName
        this.lastName = this@toTeacher.lastName
        this.dateOfBirth = this@toTeacher.dateOfBirth
    }
}