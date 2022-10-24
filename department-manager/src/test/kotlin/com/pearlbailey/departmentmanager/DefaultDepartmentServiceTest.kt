package com.pearlbailey.departmentmanager

import com.pearlbailey.departmentmanager.api.DepartmentFactory
import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.Department
import com.pearlbailey.teachermanager.api.TeacherFactory
import com.pearlbailey.teachermanager.api.TeacherWebService
import com.pearlbailey.teachermanager.api.model.web.TeacherNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.eq
import org.mockito.kotlin.never
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
    private lateinit var teacherService: TeacherWebService

    @BeforeEach
    fun beforeEach() {
        departmentService = DefaultDepartmentService(departmentRepository, teacherService)
    }

    @Test
    fun `should save a department in a repository`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(TeacherFactory.getTeacherResponseDto())
        whenever(departmentRepository.save(ArgumentMatchers.any())).thenReturn(DepartmentFactory.getDepartment())

        departmentService.createDepartment(DepartmentFactory.getCreateDepartmentDto())

        verify(departmentRepository).save(any())
    }

    @Test
    fun `should update a department in a repository when it exists`() {
        val departmentId = 1
        val oldHeadOfDepartmentId = 1
        val newHeadOfDepartmentId = 2
        val originalDepartment = DepartmentFactory.getCreateDepartmentDto()
            .toDepartment(departmentId, oldHeadOfDepartmentId)
        val patchDepartmentDto = DepartmentFactory.getPatchDepartmentDto(headOfDepartmentId = newHeadOfDepartmentId)

        whenever(departmentRepository.findById(departmentId)).thenReturn(Optional.of(originalDepartment))
        whenever(teacherService.getTeacherById(newHeadOfDepartmentId)).thenReturn(TeacherFactory.getTeacherResponseDto(id = newHeadOfDepartmentId))

        departmentService.updateDepartment(departmentId, patchDepartmentDto)

        verify(departmentRepository).findById(eq(departmentId))
        verify(departmentRepository).save(check {
            assertThat(it.id).isEqualTo(departmentId)
            assertThat(it.name).isEqualTo(patchDepartmentDto.name)
            assertThat(it.headOfDepartmentId!!).isEqualTo(newHeadOfDepartmentId)
        })
    }

    @Test
    fun `should throw TeacherNotFoundException when teacher does not exist on update`() {
        whenever(departmentRepository.findById(any())).thenReturn(Optional.of(DepartmentFactory.getDepartment()))
        whenever(teacherService.getTeacherById(any())).thenReturn(null)

        assertThrows<TeacherNotFoundException> {
            departmentService.updateDepartment(1, DepartmentFactory.getPatchDepartmentDto())
        }

        verify(departmentRepository, never()).save(any())
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
        this.headOfDepartmentId = headOfDepartmentId
    }
}