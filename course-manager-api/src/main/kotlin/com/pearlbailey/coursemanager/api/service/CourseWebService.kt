package com.pearlbailey.coursemanager.api.service

import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.GetCourseResponseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto

interface CourseWebService {
    fun createCourse(createCourseDto: CreateCourseDto): Int
    fun updateCourse(id: Int, patchCourseDto: PatchCourseDto): GetCourseResponseDto?
    fun getCourseById(id: Int): GetCourseResponseDto?
    fun getAllCoursesWithStatus(courseStatus: CourseStatus): List<GetCourseResponseDto>
    fun getCoursesByTeacher(teacherId: Int): List<GetCourseResponseDto>
    fun getCoursesByDepartment(departmentId: Int): List<GetCourseResponseDto>
}
