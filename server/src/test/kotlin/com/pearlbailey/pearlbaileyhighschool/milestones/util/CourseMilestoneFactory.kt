package com.pearlbailey.pearlbaileyhighschool.milestones.util

import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType.EXAM
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType.TUTORIAL
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto

object CourseMilestoneFactory {

    fun getUpdateCourseMilestoneDto(
        name: String? = "Assessed Tutorial",
        courseId: Int? = 3,
        courseMilestoneType: CourseMilestoneType? = TUTORIAL
    ) = UpdateCourseMilestoneDto(name, courseId, courseMilestoneType)

    fun getCreateCourseMilestoneDto(
        name: String = "Midterm Exam", courseId: Int = 2, courseMilestoneType: CourseMilestoneType = EXAM
    ) = CreateCourseMilestoneDto(
        name, courseId, courseMilestoneType
    )

    fun getCourseMilestone(
        i: Int = 1,
        milestoneName: String = "Midterm Exam",
        courseId: Int = 2,
        courseMilestoneType: CourseMilestoneType = EXAM
    ) = CourseMilestone().apply {
        id = i
        name = milestoneName
        this.courseId = courseId
        type = courseMilestoneType
    }

}
