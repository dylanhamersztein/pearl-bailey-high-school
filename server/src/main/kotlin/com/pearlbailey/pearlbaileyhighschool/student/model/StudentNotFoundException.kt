package com.pearlbailey.pearlbaileyhighschool.student.model

class StudentNotFoundException(studentId: Int? = null, message: String? = null) :
    RuntimeException(message ?: "Student with id $studentId not found.")