package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import org.springframework.data.repository.CrudRepository

interface DepartmentRepository : CrudRepository<Department, Int> {
    fun findByName(firstName: String): Department?
}