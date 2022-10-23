package com.pearlbailey.coursemanager.api.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
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

    @Column(name = "taught_by")
    var taughtById: Int? = null

    @Column(name = "department_id")
    var departmentId: Int? = null

    @Column(name = "description", nullable = false)
    var description: String? = null

    @Enumerated(STRING)
    @Column(name = "course_status", nullable = false)
    var courseStatus: CourseStatus? = null
}
