package com.pearlbailey.enrolmentmanager.api.model

import io.swagger.v3.oas.annotations.media.Schema

data class GetEnrolmentResponseDto(
    @Schema(description = "The db-generated ID of the Enrolment", example = "1")
    val id: Int,

    @Schema(description = "The ID of the Student being enrolled.", example = "1")
    val studentId: Int,

    @Schema(description = "The ID of the Course into which the student is being enrolled.", example = "1")
    val courseId: Int
)
