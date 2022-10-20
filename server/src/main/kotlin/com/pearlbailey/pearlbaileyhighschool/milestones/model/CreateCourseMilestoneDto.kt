package com.pearlbailey.pearlbaileyhighschool.milestones.model

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class CreateCourseMilestoneDto(

    @field:NotNull
    @field:Size(min = 1, max = 255)
    @Schema(description = "The name of the Course Milestone", example = "Java Assessed Lab - 1st Term")
    val name: String?,

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the Course to which the Milestone belongs.", example = "1")
    val courseId: Int?,

    @field:Max(1)
    @field:NotNull
    @field:Positive
    @Schema(
        description = "A number between 0 and 1 representing the weight in the Course's final grade.",
        example = "0.67"
    )
    val weight: BigDecimal? = null,

    @field:NotNull
    @Schema(description = "The type of Course Milestone", example = "COURSEWORK")
    val type: CourseMilestoneType?
)
