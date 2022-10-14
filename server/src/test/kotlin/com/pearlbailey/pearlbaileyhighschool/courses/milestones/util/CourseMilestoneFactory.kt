package com.pearlbailey.pearlbaileyhighschool.courses.milestones.util

import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType.EXAM
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType.TUTORIAL
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.UpdateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.Course

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
        course = Course().apply {
            id = courseId
        }
        type = courseMilestoneType
    }

}
