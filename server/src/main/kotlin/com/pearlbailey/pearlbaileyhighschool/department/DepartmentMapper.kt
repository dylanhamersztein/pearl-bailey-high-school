package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.department.model.GetDepartmentResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher

object DepartmentMapper {

    fun Int.toCreateDepartmentResponseDto() = CreatedResourceResponse(this)

    fun CreateDepartmentDto.toDepartment(headOfDepartment: Teacher) = Department().apply {
        this.name = this@toDepartment.name
        this.headOfDepartment = headOfDepartment
    }

    fun Department.toDepartmentResponseDto() = GetDepartmentResponseDto(name!!, this.headOfDepartment!!.id!!)
}
