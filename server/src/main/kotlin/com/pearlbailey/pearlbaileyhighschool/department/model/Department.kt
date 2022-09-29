package com.pearlbailey.pearlbaileyhighschool.department.model

import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.persistence.CascadeType.MERGE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(name = "departments")
class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @OneToOne(fetch = LAZY, cascade = [MERGE])
    @JoinColumn(name = "head_of_department_id", referencedColumnName = "id")
    var headOfDepartment: Teacher? = null
}

data class DepartmentResponseDto(
    val name: String,
    val headOfDepartment: Int
)

fun Department.toDepartmentResponseDto() = DepartmentResponseDto(name!!, this.headOfDepartment!!.id!!)

data class CreateDepartmentDto(
    @field:NotBlank val name: String,
    @field:NotNull @field:Positive val headOfDepartmentId: Int
)

data class CreateDepartmentResponse(val id: Int)

fun Int.toCreateDepartmentResponseDto() = CreateDepartmentResponse(this)

fun CreateDepartmentDto.toDepartment(headOfDepartment: Teacher) = Department().apply {
    this.name = this@toDepartment.name;
    this.headOfDepartment = headOfDepartment
}

data class PatchDepartmentDto(
    val name: String?,
    val headOfDepartmentId: Int?
)
