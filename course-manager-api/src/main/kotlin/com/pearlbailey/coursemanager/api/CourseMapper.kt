package com.pearlbailey.coursemanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.GetCourseResponseDto
import com.pearlbailey.departmentmanager.api.model.GetDepartmentResponseDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto

object CourseMapper {

    fun Course.toCourseResponseDto() =
        GetCourseResponseDto(
            id!!,
            name!!,
            taughtById!!,
            departmentId!!,
            description!!,
            courseStatus!!
        )

    fun CreateCourseDto.toCourse(taughtBy: GetTeacherResponseDto, department: GetDepartmentResponseDto) =
        Course().apply {
            this.name = this@toCourse.name
            this.taughtById = taughtBy.id
            this.departmentId = department.id
            this.description = this@toCourse.description
            this.courseStatus = this@toCourse.courseStatus
        }

    fun Int.toCreateCourseResponseDto() = CreatedResourceResponse(this)
}
