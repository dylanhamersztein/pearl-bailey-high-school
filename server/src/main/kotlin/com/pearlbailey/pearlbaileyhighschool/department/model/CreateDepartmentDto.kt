package com.pearlbailey.pearlbaileyhighschool.department.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateDepartmentDto(
    @field:NotBlank val name: String?,
    @field:NotNull @field:Positive val headOfDepartmentId: Int?
)
