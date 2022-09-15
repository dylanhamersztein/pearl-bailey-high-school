package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.persistence.*
import javax.persistence.CascadeType.MERGE
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(name = "courses")
class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @OneToOne(fetch = LAZY, cascade = [MERGE])
    @JoinColumn(name = "taught_by", referencedColumnName = "id")
    var taughtBy: Teacher? = null

    @OneToOne(fetch = LAZY, cascade = [MERGE])
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    var department: Department? = null

    @Column(name = "syllabus", nullable = false)
    var description: String? = null

    @Enumerated(STRING)
    @Column(name = "course_status", nullable = false)
    var courseStatus: CourseStatus? = null
}

data class CourseResponseDto(
    val name: String,
    val teacherId: Int,
    val departmentId: Int,
    val description: String,
    val courseStatus: CourseStatus
)

fun Course.toCourseResponseDto() =
    CourseResponseDto(name!!, taughtBy!!.id!!, department!!.id!!, description!!, courseStatus!!)

data class CreateCourseDto(
    @field:NotBlank val name: String,
    @field:NotNull @field:Positive val teacherId: Int,
    @field:NotNull @field:Positive val departmentId: Int,
    @field:NotBlank val description: String,
    @field:NotNull val courseStatus: CourseStatus
)

data class CreateCourseResponse(val id: Int)

fun Int.toCreateCourseResponseDto() = CreateCourseResponse(this)

fun CreateCourseDto.toCourse(taughtBy: Teacher, department: Department) = Course().apply {
    this.name = this@toCourse.name
    this.taughtBy = taughtBy
    this.department = department
    this.description = this@toCourse.description
    this.courseStatus = this@toCourse.courseStatus
}

data class PatchCourseDto(
    val name: String?,
    @field:Positive val teacherId: Int?,
    @field:Positive val departmentId: Int?,
    val description: String?,
    val courseStatus: CourseStatus?
)

enum class CourseStatus {
    PLANNED,
    ACTIVE,
    ON_HOLD,
    DISCONTINUED
}