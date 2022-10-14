package com.pearlbailey.pearlbaileyhighschool.courses.milestones.model

import java.io.Serializable
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class UpdateCourseMilestoneDto(
    @field:Size(min = 1, max = 255)
    val name: String? = null,

    @field:Positive
    val courseId: Int? = null,

    val type: CourseMilestoneType? = null
) : Serializable
