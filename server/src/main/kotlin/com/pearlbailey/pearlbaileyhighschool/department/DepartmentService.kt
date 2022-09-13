package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.toDepartment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

sealed interface DepartmentService {
    fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int
    fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto): Department?
    fun getDepartmentById(id: Int): Department?
    fun searchDepartmentByName(name: String): Department?
}

@Service
class DefaultDepartmentService(private val departmentRepository: DepartmentRepository) : DepartmentService {

    override fun createDepartment(createDepartmentDto: CreateDepartmentDto) =
        departmentRepository.save(createDepartmentDto.toDepartment()).id!!

    override fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto) = getDepartmentById(id)
        ?.let {
            Department().apply {
                name = patchDepartmentDto.name ?: it.name
                headOfDepartmentId = patchDepartmentDto.headOfDepartmentId ?: it.headOfDepartmentId
                this.id = it.id
            }
        }
        ?.let { departmentRepository.save(it) }

    override fun getDepartmentById(id: Int) = departmentRepository.findByIdOrNull(id)

    override fun searchDepartmentByName(name: String) = departmentRepository.findByName(name)
}