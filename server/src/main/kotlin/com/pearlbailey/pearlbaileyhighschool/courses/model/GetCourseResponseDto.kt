package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.milestones.model.GetCourseMilestoneResponseDto

data class GetCourseResponseDto(
    val name: String,
    val teacherId: Int,
    val departmentId: Int,
    val description: String,
    val courseStatus: CourseStatus,
    val courseMilestones: List<GetCourseMilestoneResponseDto>? = null
)
