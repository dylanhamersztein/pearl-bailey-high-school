package com.pearlbailey.pearlbaileyhighschool.department.util

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto

object DepartmentFactory {
    fun getCreateDepartmentDto(
        name: String = "Computer Science",
        headOfDepartmentId: Int = 1
    ) = CreateDepartmentDto(name, headOfDepartmentId)

    fun getPatchDepartmentDto(
        name: String = "Computer Science",
        headOfDepartmentId: Int = 1
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
}