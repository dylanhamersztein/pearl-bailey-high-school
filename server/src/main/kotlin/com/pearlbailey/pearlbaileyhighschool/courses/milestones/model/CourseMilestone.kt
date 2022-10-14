package com.pearlbailey.pearlbaileyhighschool.courses.milestones.model

import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
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
@Table(name = "course_milestones")
class CourseMilestone {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null


    @Column(name = "name", nullable = false)
    var name: String? = null

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    var course: Course? = null

    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    var type: CourseMilestoneType? = null
}

enum class CourseMilestoneType {
    COURSEWORK,
    TUTORIAL,
    EXAM
}
