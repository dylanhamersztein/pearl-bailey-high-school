package com.pearlbailey.studentmanager.api.model

import com.pearlbailey.commontools.exception.NotFoundException

class StudentNotFoundException(studentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Student with id $studentId not found.")
