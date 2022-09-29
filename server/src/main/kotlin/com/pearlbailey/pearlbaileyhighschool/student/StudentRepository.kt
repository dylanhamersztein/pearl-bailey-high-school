package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import org.springframework.data.repository.CrudRepository

interface StudentRepository : CrudRepository<Student, Int> {
    fun findByFirstName(firstName: String): Student?
    fun findByLastName(lastName: String): Student?
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Student?
}
