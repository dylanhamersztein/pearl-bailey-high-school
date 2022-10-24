package com.pearlbailey.teachermanager.api.model.web

import org.webjars.NotFoundException

class TeacherNotFoundException(teacherId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Teacher with id $teacherId not found.")
