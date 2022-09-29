package com.pearlbailey.pearlbaileyhighschool.courses.model

class CourseNotFoundException(courseId: Int) : RuntimeException("Course with id $courseId not found.")