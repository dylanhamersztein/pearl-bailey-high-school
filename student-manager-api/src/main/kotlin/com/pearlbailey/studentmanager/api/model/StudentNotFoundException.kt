package com.pearlbailey.studentmanager.api.model

import org.webjars.NotFoundException

class StudentNotFoundException(studentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Student with id $studentId not found.")
