package com.pearlbailey.coursemilestonemanager.api

import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestone
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType.EXAM
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType.TUTORIAL
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.GetCourseMilestoneResponseDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto
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

    fun getCourseMilestoneResponse(
        id: Int = 1,
        milestoneName: String = "Midterm Exam",
        courseId: Int = 2,
        weight: BigDecimal = 0.54.toBigDecimal(),
        courseMilestoneType: CourseMilestoneType = EXAM
    ) = GetCourseMilestoneResponseDto(id, milestoneName, courseId, weight, courseMilestoneType)

}
