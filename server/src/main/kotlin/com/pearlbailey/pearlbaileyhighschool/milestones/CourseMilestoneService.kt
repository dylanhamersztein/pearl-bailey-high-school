package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto

sealed interface CourseMilestoneService {

    fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int

    fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone?

    fun getAllCourseMilestones(): List<CourseMilestone>

    fun getCourseMilestoneById(id: Int): CourseMilestone?

    fun getCourseMilestonesByCourseId(courseId: Int): List<CourseMilestone>?

    fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>?

}
