package com.pearlbailey.departmentmanager.api.model

import com.pearlbailey.commontools.exception.NotFoundException

class DepartmentNotFoundException(departmentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Department with id $departmentId not found.")
