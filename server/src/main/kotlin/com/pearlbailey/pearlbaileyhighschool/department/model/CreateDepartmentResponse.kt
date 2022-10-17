package com.pearlbailey.pearlbaileyhighschool.department.model

data class CreateDepartmentResponse(val id: Int)

fun Int.toCreateDepartmentResponseDto() = CreateDepartmentResponse(this)
