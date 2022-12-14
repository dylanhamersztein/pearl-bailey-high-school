package com.pearlbailey.studentmanager.api.service

import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.Student

interface StudentService {
    fun createStudent(createStudentDto: CreateStudentDto): Int
    fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): Student?
    fun getStudentById(id: Int): Student?

    fun getAllStudents(): List<Student>
}
