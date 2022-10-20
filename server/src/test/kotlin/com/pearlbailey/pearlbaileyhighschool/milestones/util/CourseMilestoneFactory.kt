package com.pearlbailey.pearlbaileyhighschool.milestones.util

import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType.EXAM
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType.TUTORIAL
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto
import java.math.BigDecimal

object CourseMilestoneFactory {

    fun getCreateCourseMilestoneDto(
        name: String? = "Midterm Exam",
        courseId: Int? = 2,
        weight: BigDecimal? = 0.5.toBigDecimal(),
        courseMilestoneType: CourseMilestoneType? = EXAM
    ) = CreateCourseMilestoneDto(name, courseId, weight, courseMilestoneType)

    fun getUpdateCourseMilestoneDto(
        name: String? = "Assessed Tutorial",
        courseId: Int? = 3,
        weight: BigDecimal? = 0.6.toBigDecimal(),
        courseMilestoneType: CourseMilestoneType? = TUTORIAL
    ) = UpdateCourseMilestoneDto(name, courseId, weight, courseMilestoneType)

    fun getCourseMilestone(
        i: Int = 1,
        milestoneName: String = "Midterm Exam",
        courseId: Int = 2,
        weight: BigDecimal? = 0.54.toBigDecimal(),
        courseMilestoneType: CourseMilestoneType = EXAM
    ) = CourseMilestone().apply {
        id = i
        name = milestoneName
        this.courseId = courseId
        this.weight = weight
        type = courseMilestoneType
    }

}
