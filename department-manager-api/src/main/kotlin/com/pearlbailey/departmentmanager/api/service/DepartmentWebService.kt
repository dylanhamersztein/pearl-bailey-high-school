package com.pearlbailey.departmentmanager.api.service

import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.GetDepartmentResponseDto
import com.pearlbailey.departmentmanager.api.model.PatchDepartmentDto

interface DepartmentWebService {
    fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int
    fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto): GetDepartmentResponseDto?
    fun getDepartmentById(id: Int): GetDepartmentResponseDto?
}
