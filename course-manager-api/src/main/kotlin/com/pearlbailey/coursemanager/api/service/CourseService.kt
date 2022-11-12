package com.pearlbailey.coursemanager.api.service

import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto

interface CourseService {
    fun getAllCourses(): List<Course>
    fun createCourse(createCourseDto: CreateCourseDto): Int
    fun updateCourse(id: Int, patchCourseDto: PatchCourseDto): Course?
    fun getCourseById(id: Int): Course?
    fun getAllCoursesWithStatus(courseStatus: CourseStatus): List<Course>
    fun getCoursesByTeacher(teacherId: Int): List<Course>
    fun getCoursesByDepartment(departmentId: Int): List<Course>
}
