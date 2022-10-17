package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseStatus
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto

sealed interface CourseService {
    fun createCourse(createCourseDto: CreateCourseDto): Int
    fun updateCourse(id: Int, patchCourseDto: PatchCourseDto): Course?
    fun getCourseById(id: Int): Course?
    fun getAllCoursesWithStatus(courseStatus: CourseStatus): List<Course>
    fun getCoursesByTeacher(teacherId: Int): List<Course>
    fun getCoursesByDepartment(departmentId: Int): List<Course>
}
