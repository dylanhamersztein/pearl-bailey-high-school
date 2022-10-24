package com.pearlbailey.studentmanager.api

import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.StudentResponseDto

interface StudentWebService {
    fun createStudent(createStudentDto: CreateStudentDto): Int
    fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): StudentResponseDto?
    fun getStudentById(id: Int): StudentResponseDto?
    fun searchStudentByName(firstName: String?, lastName: String?): StudentResponseDto?
}
