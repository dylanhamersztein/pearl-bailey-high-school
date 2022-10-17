package com.pearlbailey.pearlbaileyhighschool.courses.model

import javax.validation.constraints.Positive

data class PatchCourseDto(
    val name: String?,
    @field:Positive val teacherId: Int?,
    @field:Positive val departmentId: Int?,
    val description: String?,
    val courseStatus: CourseStatus?
)