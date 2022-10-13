package com.pearlbailey.pearlbaileyhighschool.department.model

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException

class DepartmentNotFoundException(departmentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Department with id $departmentId not found.")
