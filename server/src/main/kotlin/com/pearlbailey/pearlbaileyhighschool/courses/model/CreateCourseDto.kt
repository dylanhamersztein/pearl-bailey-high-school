package com.pearlbailey.pearlbaileyhighschool.courses.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateCourseDto(
    @field:NotBlank val name: String?,
    @field:NotNull @field:Positive val teacherId: Int?,
    @field:NotNull @field:Positive val departmentId: Int?,
    @field:NotBlank val description: String?,
    @field:NotNull val courseStatus: CourseStatus?
)
