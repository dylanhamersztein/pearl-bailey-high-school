package com.pearlbailey.pearlbaileyhighschool.department.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateDepartmentDto(

    @field:NotBlank
    @Schema(description = "The name of the Department", example = "Computer Science")
    val name: String?,

    @field:NotNull
    @field:Positive
    @Schema(description = "The ID of the Teacher who is head of the Department.", example = "1")
    val headOfDepartmentId: Int?
)
