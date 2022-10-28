package com.pearlbailey.studentmanager.jpa

import com.pearlbailey.studentmanager.api.model.Student
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository

@Profile("jpa")
interface StudentRepository : CrudRepository<Student, Int> {
    fun findByFirstName(firstName: String): Student?
    fun findByLastName(lastName: String): Student?
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Student?
}
