package com.pearlbailey.pearlbaileyhighschool.milestones.model

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
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

    @Column(name = "course_id")
    var courseId: Int? = null

    @Column(name = "weight")
    var weight: BigDecimal? = null

    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    var type: CourseMilestoneType? = null
}
