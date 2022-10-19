package com.pearlbailey.pearlbaileyhighschool.milestones.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class GetCourseMilestoneResponseDto(

    @Schema(description = "The database-generated ID of the Course Milestone", example = "1")
    val id: Int,

    @Schema(description = "The name of the Course Milestone", example = "Java Assessed Lab - 1st Term")
    val name: String,

    @Schema(description = "The ID of the Course to which the Milestone belongs.", example = "1")
    val courseId: Int,

    @Schema(description = "The type of Course Milestone", example = "COURSEWORK")
    val type: CourseMilestoneType
)
