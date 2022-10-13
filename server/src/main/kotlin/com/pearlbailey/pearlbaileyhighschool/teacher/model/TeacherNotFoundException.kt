package com.pearlbailey.pearlbaileyhighschool.teacher.model

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException

class TeacherNotFoundException(teacherId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Teacher with id $teacherId not found.")
