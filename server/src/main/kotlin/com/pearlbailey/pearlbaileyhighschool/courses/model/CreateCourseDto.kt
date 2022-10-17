package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateCourseDto(
    @field:NotBlank val name: String,
    @field:NotNull @field:Positive val teacherId: Int,
    @field:NotNull @field:Positive val departmentId: Int,
    @field:NotBlank val description: String,
    @field:NotNull val courseStatus: CourseStatus
)

fun CreateCourseDto.toCourse(taughtBy: Teacher, department: Department) = Course().apply {
    this.name = this@toCourse.name
    this.taughtBy = taughtBy
    this.department = department
    this.description = this@toCourse.description
    this.courseStatus = this@toCourse.courseStatus
}