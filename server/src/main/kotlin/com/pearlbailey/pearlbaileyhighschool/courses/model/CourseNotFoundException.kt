package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException

class CourseNotFoundException(courseId: Int) : NotFoundException("Course with id $courseId not found.")
