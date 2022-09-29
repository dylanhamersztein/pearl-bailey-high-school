package com.pearlbailey.pearlbaileyhighschool.department.model

class DepartmentNotFoundException(departmentId: Int? = null, message: String? = null) :
    RuntimeException(message ?: "Department with id $departmentId not found.")
