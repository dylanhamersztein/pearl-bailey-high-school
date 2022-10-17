package com.pearlbailey.pearlbaileyhighschool.department.model

import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateDepartmentDto(
    @field:NotBlank val name: String,
    @field:NotNull @field:Positive val headOfDepartmentId: Int
)

fun CreateDepartmentDto.toDepartment(headOfDepartment: Teacher) = Department().apply {
    this.name = this@toDepartment.name;
    this.headOfDepartment = headOfDepartment
}
