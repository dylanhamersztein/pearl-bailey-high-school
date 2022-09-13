package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
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
internal class DefaultDepartmentServiceTest {

    private lateinit var departmentService: DefaultDepartmentService

    @MockBean
    private lateinit var departmentRepository: DepartmentRepository

    @BeforeEach
    fun beforeEach() {
        departmentService = DefaultDepartmentService(departmentRepository)
    }

    @Test
    fun `should save a department in a repository`() {
        `when`(departmentRepository.save(any())).thenReturn(DepartmentFactory.getDepartment())

        departmentService.createDepartment(DepartmentFactory.getCreateDepartmentDto())

        verify(departmentRepository).save(any())
    }

    @Test
    fun `should update a department in a repository when it exists`() {
        val departmentId = 1
        val originalDepartment = DepartmentFactory.getCreateDepartmentDto().toDepartment(departmentId)
        val patchDepartmentDto = DepartmentFactory.getPatchDepartmentDto()

        whenever(departmentRepository.findById(departmentId)).thenReturn(Optional.of(originalDepartment))

        departmentService.updateDepartment(departmentId, patchDepartmentDto)

        inOrder(departmentRepository) {
            verify(departmentRepository).findById(departmentId)
            verify(departmentRepository).save(check {
                assertThat(it.id).isEqualTo(departmentId)
                assertThat(it.name).isEqualTo(patchDepartmentDto.name)
                assertThat(it.headOfDepartmentId).isEqualTo(patchDepartmentDto.headOfDepartmentId)
            })
        }
    }

    @Test
    fun `should not do anything when department does not exist`() {
        whenever(departmentRepository.findById(anyOrNull())).thenReturn(Optional.empty())

        departmentService.updateDepartment(1, DepartmentFactory.getPatchDepartmentDto())

        verify(departmentRepository).findById(1)
        verifyNoMoreInteractions(departmentRepository)
    }

    private fun CreateDepartmentDto.toDepartment(id: Int) = Department().apply {
        this.id = id
        this.name = this@toDepartment.name
        this.headOfDepartmentId = this@toDepartment.headOfDepartmentId
    }
}