package com.pearlbailey.teachermanager.api.model.web

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class CreateTeacherDto(

    @field:NotBlank
    @Schema(description = "The teacher's first name.", example = "Brian")
    val firstName: String?,

    @Schema(description = "The teacher's middle name.", example = "P.")
    val middleName: String?,

    @field:NotBlank
    @Schema(description = "The teacher's last name.", example = "Lewis")
    val lastName: String?,

    @field:Past
    @field:NotNull
    @Schema(description = "The teacher's date of birth.", example = "1964/10/19", pattern = "yyyy/MM/dd")
    val dateOfBirth: LocalDate?
)
