package com.pearlbailey.teachermanager.api.service

import com.pearlbailey.teachermanager.api.model.db.Teacher
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto

interface TeacherService {
    fun createTeacher(createTeacherDto: CreateTeacherDto): Int
    fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): Teacher?
    fun getTeacherById(id: Int): Teacher?

    fun getAllTeachers(): List<Teacher>
}
