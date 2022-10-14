package com.pearlbailey.pearlbaileyhighschool.courses.milestones.model

data class CourseMilestoneResponse(
    val id: Int,
    val name: String,
    val courseId: Int,
    val type: CourseMilestoneType
)
