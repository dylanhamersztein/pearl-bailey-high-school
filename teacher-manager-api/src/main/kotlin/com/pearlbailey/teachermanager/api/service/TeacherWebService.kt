package com.pearlbailey.teachermanager.api.service

import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto

interface TeacherWebService {
    fun createTeacher(createTeacherDto: CreateTeacherDto): Int
    fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): GetTeacherResponseDto?
    fun getTeacherById(id: Int): GetTeacherResponseDto?
}
