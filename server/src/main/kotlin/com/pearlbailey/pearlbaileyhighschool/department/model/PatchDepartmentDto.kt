package com.pearlbailey.pearlbaileyhighschool.department.model

import io.swagger.v3.oas.annotations.media.Schema

data class PatchDepartmentDto(

    @Schema(description = "The name of the Department", example = "Computer Science")
    val name: String?,

    @Schema(description = "The ID of the Teacher who is head of the Department.", example = "1")
    val headOfDepartmentId: Int?
)
