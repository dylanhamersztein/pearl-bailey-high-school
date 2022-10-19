package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.milestones.model.GetCourseMilestoneResponseDto
import io.swagger.v3.oas.annotations.media.Schema

data class GetCourseResponseDto(

    @Schema(description = "The name of the Course.", example = "Java 101")
    val name: String,

    @Schema(description = "The ID of the course's Teacher.", example = "1")
    val teacherId: Int,

    @Schema(description = "The ID of the Department offering the Course.", example = "1")
    val departmentId: Int,

    @Schema(description = "The course's description.", example = "An introduction to Java.")
    val description: String,

    @Schema(description = "What status the course is in.", example = "PLANNED")
    val courseStatus: CourseStatus,

    @Schema(description = "Any Course Milestones associated to the Course")
    val courseMilestones: List<GetCourseMilestoneResponseDto>? = null
)
