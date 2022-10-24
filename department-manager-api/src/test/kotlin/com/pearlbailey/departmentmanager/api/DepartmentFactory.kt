package com.pearlbailey.departmentmanager.api

import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.Department
import com.pearlbailey.departmentmanager.api.model.GetDepartmentResponseDto
import com.pearlbailey.departmentmanager.api.model.PatchDepartmentDto

object DepartmentFactory {
    fun getCreateDepartmentDto(
        name: String? = "Computer Science",
        headOfDepartmentId: Int? = 1
    ) = CreateDepartmentDto(name, headOfDepartmentId)

    fun getPatchDepartmentDto(
        name: String? = "Computer Science",
        headOfDepartmentId: Int? = 2
    ) = PatchDepartmentDto(name, headOfDepartmentId)

    fun getDepartment(
        id: Int = 1,
        name: String = "Computer Science",
        headOfDepartmentId: Int = 1
    ) = Department().apply {
        this.id = id
        this.name = name
        this.headOfDepartmentId = headOfDepartmentId
    }

    fun getDepartmentResponseDto(
        id: Int = 1,
        name: String = "Computer Science",
        headOfDepartmentId: Int = 1
    ) = GetDepartmentResponseDto(id, name, headOfDepartmentId)
}