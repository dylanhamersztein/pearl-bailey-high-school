package com.pearlbailey.departmentmanager.api.model

import org.webjars.NotFoundException

class DepartmentNotFoundException(departmentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Department with id $departmentId not found.")
