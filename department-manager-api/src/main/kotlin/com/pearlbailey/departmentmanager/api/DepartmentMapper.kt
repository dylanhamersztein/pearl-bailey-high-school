package com.pearlbailey.departmentmanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.Department
import com.pearlbailey.departmentmanager.api.model.GetDepartmentResponseDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto

object DepartmentMapper {

    fun Int.toCreateDepartmentResponseDto() = CreatedResourceResponse(this)

    fun CreateDepartmentDto.toDepartment(headOfDepartment: GetTeacherResponseDto) = Department().apply {
        this.name = this@toDepartment.name
        this.headOfDepartmentId = headOfDepartment.id
    }

    fun Department.toDepartmentResponseDto() = GetDepartmentResponseDto(id!!, name!!, this.headOfDepartmentId!!)
}
