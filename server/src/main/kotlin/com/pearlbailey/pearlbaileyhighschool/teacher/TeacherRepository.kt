package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import org.springframework.data.repository.CrudRepository

interface TeacherRepository : CrudRepository<Teacher, Int> {
    fun findByFirstName(firstName: String): Teacher?
    fun findByLastName(lastName: String): Teacher?
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Teacher?
}