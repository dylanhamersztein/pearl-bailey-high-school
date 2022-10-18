package com.pearlbailey.pearlbaileyhighschool.milestones.model

data class GetCourseMilestoneResponseDto(
    val id: Int,
    val name: String,
    val courseId: Int,
    val type: CourseMilestoneType
)
