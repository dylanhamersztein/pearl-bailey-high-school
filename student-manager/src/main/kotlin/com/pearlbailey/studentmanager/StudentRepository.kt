package com.pearlbailey.studentmanager

import com.pearlbailey.studentmanager.api.model.Student
import org.springframework.data.repository.CrudRepository

interface StudentRepository : CrudRepository<Student, Int> {
    fun findByFirstName(firstName: String): Student?
    fun findByLastName(lastName: String): Student?
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Student?
}
