package com.pearlbailey.coursemilestonemanager.api.service

import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestone
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto

interface CourseMilestoneService {

    fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int

    fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone?

    fun getAllCourseMilestones(): List<CourseMilestone>

    fun getCourseMilestoneById(id: Int): CourseMilestone?

    fun getCourseMilestonesByCourseId(courseId: Int): List<CourseMilestone>?

    fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>?

}
