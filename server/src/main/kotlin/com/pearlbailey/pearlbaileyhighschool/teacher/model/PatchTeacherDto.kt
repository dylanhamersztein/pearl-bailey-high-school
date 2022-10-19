package com.pearlbailey.pearlbaileyhighschool.teacher.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class PatchTeacherDto(

    @Schema(description = "The teacher's first name.", example = "Brian")
    val firstName: String?,

    @Schema(description = "The teacher's middle name.", example = "P.")
    val middleName: String?,

    @Schema(description = "The teacher's last name.", example = "Lewis")
    val lastName: String?,

    @Schema(description = "The teacher's date of birth.", example = "1964/10/19", pattern = "yyyy/MM/dd")
    val dateOfBirth: LocalDate?,
)
