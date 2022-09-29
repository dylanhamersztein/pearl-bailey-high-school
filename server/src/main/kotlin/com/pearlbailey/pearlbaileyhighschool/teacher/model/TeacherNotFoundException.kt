package com.pearlbailey.pearlbaileyhighschool.teacher.model

class TeacherNotFoundException(teacherId: Int? = null, message: String? = null) :
    RuntimeException(message ?: "Teacher with id $teacherId not found.")
