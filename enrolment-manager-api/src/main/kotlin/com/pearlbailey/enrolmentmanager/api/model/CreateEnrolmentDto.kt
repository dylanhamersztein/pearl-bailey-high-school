package com.pearlbailey.enrolmentmanager.api.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateEnrolmentDto(

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the Student being enrolled.", example = "1")
    val studentId: Int?,

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the Course into which the student is being enrolled.", example = "1")
    val courseId: Int?

)
