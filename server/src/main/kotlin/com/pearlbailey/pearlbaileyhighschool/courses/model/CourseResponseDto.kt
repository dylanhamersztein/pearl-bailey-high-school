package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneResponse

data class CourseResponseDto(
    val name: String,
    val teacherId: Int,
    val departmentId: Int,
    val description: String,
    val courseStatus: CourseStatus,
    val courseMilestones: List<CourseMilestoneResponse>? = null
)
