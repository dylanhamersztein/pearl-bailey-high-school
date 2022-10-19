package com.pearlbailey.pearlbaileyhighschool.milestones.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class UpdateCourseMilestoneDto(

    @field:Size(min = 1, max = 255)
    @Schema(description = "The name of the Course Milestone", example = "Java Assessed Lab - 1st Term")
    val name: String? = null,

    @field:Positive
    @Schema(description = "The ID of the Course to which the Milestone belongs.", example = "1")
    val courseId: Int? = null,

    @Schema(description = "The type of Course Milestone", example = "COURSEWORK")
    val type: CourseMilestoneType? = null
)
