package com.pearlbailey.pearlbaileyhighschool.milestones.model

import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class CreateCourseMilestoneDto(

    @field:NotNull
    @field:Size(min = 1, max = 255)
    val name: String?,

    @field:NotNull
    @field:Positive
    val courseId: Int?,

    @field:NotNull
    val type: CourseMilestoneType?
)
