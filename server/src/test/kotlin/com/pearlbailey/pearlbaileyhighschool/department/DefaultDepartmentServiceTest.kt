package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherService
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
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class DefaultDepartmentServiceTest {

    private lateinit var departmentService: DefaultDepartmentService

    @MockBean
    private lateinit var departmentRepository: DepartmentRepository

    @MockBean
    private lateinit var teacherService: TeacherService

    @BeforeEach
    fun beforeEach() {
        departmentService = DefaultDepartmentService(departmentRepository, teacherService)
    }

    @Test
    fun `should save a department in a repository`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(TeacherFactory.getTeacher())
        whenever(departmentRepository.save(ArgumentMatchers.any())).thenReturn(DepartmentFactory.getDepartment())

        departmentService.createDepartment(DepartmentFactory.getCreateDepartmentDto())

        verify(departmentRepository).save(any())
    }

    @Test
    fun `should update a department in a repository when it exists`() {
        val departmentId = 1
        val headOfDepartmentId = 2
        val originalDepartment = DepartmentFactory.getCreateDepartmentDto()
            .toDepartment(departmentId, headOfDepartmentId)
        val patchDepartmentDto = DepartmentFactory.getPatchDepartmentDto(headOfDepartmentId = headOfDepartmentId)

        whenever(departmentRepository.findById(departmentId)).thenReturn(Optional.of(originalDepartment))

        departmentService.updateDepartment(departmentId, patchDepartmentDto)

        verify(departmentRepository).findById(eq(departmentId))
        verify(departmentRepository).save(check {
            assertThat(it.id).isEqualTo(departmentId)
            assertThat(it.name).isEqualTo(patchDepartmentDto.name)
            assertThat(it.headOfDepartment!!.id).isEqualTo(patchDepartmentDto.headOfDepartmentId)
        })
    }

    @Test
    fun `should not do anything when department does not exist`() {
        whenever(departmentRepository.findById(anyOrNull())).thenReturn(Optional.empty())

        departmentService.updateDepartment(1, DepartmentFactory.getPatchDepartmentDto())

        verify(departmentRepository).findById(1)
        verifyNoMoreInteractions(departmentRepository)
    }

    private fun CreateDepartmentDto.toDepartment(id: Int = 1, headOfDepartmentId: Int = 2) = Department().apply {
        this.id = id
        this.name = this@toDepartment.name
        this.headOfDepartment = Teacher().apply { this.id = headOfDepartmentId }
    }
}