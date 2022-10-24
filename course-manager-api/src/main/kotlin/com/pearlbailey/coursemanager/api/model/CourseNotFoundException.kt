package com.pearlbailey.coursemanager.api.model

import com.pearlbailey.commontools.exception.NotFoundException

class CourseNotFoundException(courseId: Int) : NotFoundException("Course with id $courseId not found.")
