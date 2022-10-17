package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.UpdateCourseMilestoneDto

sealed interface CourseMilestoneService {

    fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int

    fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone?

    fun getAllCourseMilestones(): List<CourseMilestone>

    fun getCourseMilestoneById(id: Int): CourseMilestone?

    fun getCourseMilestonesByCourseId(courseId: Int): List<CourseMilestone>?

    fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>?

}
