package com.pearlbailey.coursemilestonemanager.api.model

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema
data class GetCourseMilestoneResponseDto(

    @Schema(description = "The database-generated ID of the Course Milestone", example = "1")
    val id: Int,

    @Schema(description = "The name of the Course Milestone", example = "Java Assessed Lab - 1st Term")
    val name: String,

    @Schema(description = "The ID of the Course to which the Milestone belongs.", example = "1")
    val courseId: Int,

    @Schema(
        description = "A number between 0 and 1 representing the weight in the Course's final grade.",
        example = "0.67"
    )
    val weight: BigDecimal,

    @Schema(description = "The type of Course Milestone", example = "COURSEWORK")
    val type: CourseMilestoneType
)
