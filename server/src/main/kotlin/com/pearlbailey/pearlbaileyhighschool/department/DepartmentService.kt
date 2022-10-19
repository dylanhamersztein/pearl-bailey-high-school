package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto

interface DepartmentService {
    fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int
    fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto): Department?
    fun getDepartmentById(id: Int): Department?
    fun searchDepartmentByName(name: String): Department?
}
