package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher

sealed interface TeacherService {
    fun createTeacher(createTeacherDto: CreateTeacherDto): Int
    fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): Teacher?
    fun getTeacherById(id: Int): Teacher?
    fun searchTeacherByName(firstName: String?, lastName: String?): Teacher?
}
