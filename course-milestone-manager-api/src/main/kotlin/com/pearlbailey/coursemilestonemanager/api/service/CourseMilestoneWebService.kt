package com.pearlbailey.coursemilestonemanager.api.service

import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.GetCourseMilestoneResponseDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto

interface CourseMilestoneWebService {

    fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int

    fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): GetCourseMilestoneResponseDto?

    fun getAllCourseMilestones(): List<GetCourseMilestoneResponseDto>

    fun getCourseMilestoneById(id: Int): GetCourseMilestoneResponseDto?

    fun getCourseMilestonesByCourseId(courseId: Int): List<GetCourseMilestoneResponseDto>?

    fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<GetCourseMilestoneResponseDto>?

}
