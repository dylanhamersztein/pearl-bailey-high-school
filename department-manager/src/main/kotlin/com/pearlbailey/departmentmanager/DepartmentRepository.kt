package com.pearlbailey.departmentmanager

import com.pearlbailey.departmentmanager.api.model.Department
import org.springframework.data.repository.CrudRepository

interface DepartmentRepository : CrudRepository<Department, Int> {
    fun findByName(firstName: String): Department?
}
