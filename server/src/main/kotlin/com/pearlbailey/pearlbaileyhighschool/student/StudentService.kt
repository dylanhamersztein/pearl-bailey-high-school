package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student

sealed interface StudentService {
    fun createStudent(createStudentDto: CreateStudentDto): Int
    fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): Student?
    fun getStudentById(id: Int): Student?
    fun searchStudentByName(firstName: String?, lastName: String?): Student?
}
