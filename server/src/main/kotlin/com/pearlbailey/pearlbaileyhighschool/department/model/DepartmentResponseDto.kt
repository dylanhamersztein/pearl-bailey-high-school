package com.pearlbailey.pearlbaileyhighschool.department.model

data class DepartmentResponseDto(
    val name: String,
    val headOfDepartment: Int
)

fun Department.toDepartmentResponseDto() = DepartmentResponseDto(name!!, this.headOfDepartment!!.id!!)
