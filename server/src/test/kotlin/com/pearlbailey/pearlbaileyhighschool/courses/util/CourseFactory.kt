package com.pearlbailey.pearlbaileyhighschool.courses.util

import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseStatus
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseStatus.PLANNED
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
import com.pearlbailey.pearlbaileyhighschool.teacher.util.TeacherFactory

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
        this.taughtBy = TeacherFactory.getTeacher(teacherId)
        this.department = DepartmentFactory.getDepartment(departmentId)
        this.description = description
        this.courseStatus = courseStatus
    }
}