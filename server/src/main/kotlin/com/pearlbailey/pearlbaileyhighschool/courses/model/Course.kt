package com.pearlbailey.pearlbaileyhighschool.courses.model

import com.pearlbailey.pearlbaileyhighschool.department.model.Department
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toCourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.persistence.CascadeType.MERGE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

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

    @Column(name = "description", nullable = false)
    var description: String? = null

    @Enumerated(STRING)
    @Column(name = "course_status", nullable = false)
    var courseStatus: CourseStatus? = null
}

fun Course.toCourseResponseDto(courseMilestones: List<CourseMilestone>? = null) = CourseResponseDto(name!!,
    taughtBy!!.id!!,
    department!!.id!!,
    description!!,
    courseStatus!!,
    courseMilestones?.map { it.toCourseMilestoneResponse() })

