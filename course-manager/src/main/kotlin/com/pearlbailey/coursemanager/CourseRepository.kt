package com.pearlbailey.coursemanager

import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.coursemanager.api.model.CourseStatus
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {
    fun findAllByTaughtById(taughtById: Int): List<Course>
    fun findAllByDepartmentId(departmentId: Int): List<Course>
    fun findAllByCourseStatus(courseStatus: CourseStatus): List<Course>
}
