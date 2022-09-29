@file:Suppress("FunctionName")

package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseStatus
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {
    fun findAllByTaughtBy_Id(taughtById: Int): List<Course>
    fun findAllByDepartment_Id(departmentId: Int): List<Course>
    fun findAllByCourseStatus(courseStatus: CourseStatus): List<Course>
}
