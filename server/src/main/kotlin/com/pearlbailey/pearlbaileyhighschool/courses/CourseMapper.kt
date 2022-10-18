package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.GetCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toCourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher

object CourseMapper {

    fun Course.toCourseResponseDto(courseMilestones: List<CourseMilestone>? = null) = GetCourseResponseDto(name!!,
        taughtBy!!.id!!,
        department!!.id!!,
        description!!,
        courseStatus!!,
        courseMilestones?.map { it.toCourseMilestoneResponse() })

    fun CreateCourseDto.toCourse(taughtBy: Teacher, department: Department) = Course().apply {
        this.name = this@toCourse.name
        this.taughtBy = taughtBy
        this.department = department
        this.description = this@toCourse.description
        this.courseStatus = this@toCourse.courseStatus
    }

    fun Int.toCreateCourseResponseDto() = CreatedResourceResponse(this)
}
