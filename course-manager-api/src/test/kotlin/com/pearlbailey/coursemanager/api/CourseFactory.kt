package com.pearlbailey.coursemanager.api

import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CourseStatus.PLANNED
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.GetCourseResponseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto

object CourseFactory {

    fun getCreateCourseDto(
        name: String? = "Computer Science",
        teacherId: Int? = 1,
        departmentId: Int? = 2,
        description: String? = "Learning computer science at Pearl Bailey High School",
        courseStatus: CourseStatus? = PLANNED
    ) = CreateCourseDto(name, teacherId, departmentId, description, courseStatus)

    fun getPatchCourseDto(
        name: String? = "Computer Science",
        teacherId: Int? = 1,
        departmentId: Int? = 2,
        description: String? = "Learning computer science at Pearl Bailey High School",
        courseStatus: CourseStatus? = PLANNED
    ) = PatchCourseDto(name, teacherId, departmentId, description, courseStatus)

    fun getCourse(
        id: Int = 1,
        name: String = "Computer Science",
        teacherId: Int = 2,
        departmentId: Int = 3,
        description: String = "Learning computer science at Pearl Bailey High School",
        courseStatus: CourseStatus = PLANNED
    ) = Course().apply {
        this.id = id
        this.name = name
        this.taughtById = teacherId
        this.departmentId = departmentId
        this.description = description
        this.courseStatus = courseStatus
    }

    fun getCourseResponseDto(
        id: Int = 1,
        name: String = "Computer Science",
        teacherId: Int = 2,
        departmentId: Int = 3,
        description: String = "Learning computer science at Pearl Bailey High School",
        courseStatus: CourseStatus = PLANNED
    ) = GetCourseResponseDto(id, name, teacherId, departmentId, description, courseStatus)
}