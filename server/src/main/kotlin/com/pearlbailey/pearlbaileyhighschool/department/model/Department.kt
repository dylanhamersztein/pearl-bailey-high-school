package com.pearlbailey.pearlbaileyhighschool.department.model

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(name = "departments")
class Department {
    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "head_of_department_id", nullable = false)
    var headOfDepartmentId: Int? = null

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null
}

data class DepartmentResponseDto(
    val name: String,
    val headOfDepartment: Int
)

fun Department.toDepartmentResponseDto() = DepartmentResponseDto(name!!, headOfDepartmentId!!)

data class CreateDepartmentDto(
    @field:NotBlank val name: String,
    @field:NotNull @field:Positive val headOfDepartmentId: Int
)

data class CreateDepartmentResponse(val id: Int)

fun Int.toCreateDepartmentResponseDto() = CreateDepartmentResponse(this)

fun CreateDepartmentDto.toDepartment() = Department().apply {
    name = this@toDepartment.name;
    headOfDepartmentId = this@toDepartment.headOfDepartmentId
}

data class PatchDepartmentDto(
    val name: String?,
    val headOfDepartmentId: Int?
)