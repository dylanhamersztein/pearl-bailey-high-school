package com.pearlbailey.departmentmanager.api.model

import io.swagger.v3.oas.annotations.media.Schema

data class GetDepartmentResponseDto(
    @Schema(description = "The db-generated ID of the Department", example = "1")
    val id: Int,

    @Schema(description = "The name of the Department", example = "Computer Science")
    val name: String,

    @Schema(description = "The ID of the Teacher who is head of the Department.", example = "1")
    val headOfDepartment: Int
)
