package com.pearlbailey.departmentmanager.api.service

import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.Department
import com.pearlbailey.departmentmanager.api.model.PatchDepartmentDto

interface DepartmentService {
    fun getAllDepartments(): List<Department>
    fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int
    fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto): Department?
    fun getDepartmentById(id: Int): Department?
    fun searchDepartmentByName(name: String): Department?
}
