package com.pearlbailey.enrolmentmanager.api.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "enrollments")
class Enrolment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "student_id", nullable = false)
    var studentId: Int? = null

    @Column(name = "course_id", nullable = false)
    var courseId: Int? = null

}
