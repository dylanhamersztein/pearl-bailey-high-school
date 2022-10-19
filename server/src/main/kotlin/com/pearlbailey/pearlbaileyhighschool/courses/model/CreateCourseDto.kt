package com.pearlbailey.pearlbaileyhighschool.courses.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateCourseDto(

    @field:NotBlank
    @Schema(description = "The name of the Course.", example = "Java 101")
    val name: String?,

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the course's Teacher.", example = "1")
    val teacherId: Int?,

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the Department offering the Course.", example = "1")
    val departmentId: Int?,

    @field:NotBlank
    @Schema(description = "The course's description.", example = "An introduction to Java.")
    val description: String?,

    @field:NotNull
    @Schema(description = "What status the course is in.", example = "PLANNED")
    val courseStatus: CourseStatus?
)
