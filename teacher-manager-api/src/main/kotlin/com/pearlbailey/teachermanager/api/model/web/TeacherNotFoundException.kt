package com.pearlbailey.teachermanager.api.model.web

import com.pearlbailey.commontools.exception.NotFoundException

class TeacherNotFoundException(teacherId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Teacher with id $teacherId not found.")
