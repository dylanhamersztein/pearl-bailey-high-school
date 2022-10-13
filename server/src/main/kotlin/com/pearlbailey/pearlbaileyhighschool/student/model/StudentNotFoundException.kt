package com.pearlbailey.pearlbaileyhighschool.student.model

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException

class StudentNotFoundException(studentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Student with id $studentId not found.")
