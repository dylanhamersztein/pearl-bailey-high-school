package com.pearlbailey.coursemilestonemanager.api.service.impl

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneConstants.COURSE_MILESTONES_RESOURCE_PATH
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.GetCourseMilestoneResponseDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.service.CourseMilestoneWebService
import org.springframework.web.client.RestTemplate

class DefaultCourseMilestoneWebService(baseUrl: String, restTemplate: RestTemplate) :
    AbstractWebService(baseUrl, restTemplate), CourseMilestoneWebService {

    override fun getResourceName() = COURSE_MILESTONES_RESOURCE_PATH

    override fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto) =
        createResource(createCourseMilestoneDto).id

    override fun updateCourseMilestone(
        id: Int,
        updateCourseMilestoneDto: UpdateCourseMilestoneDto
    ): GetCourseMilestoneResponseDto? = updateResource(id, updateCourseMilestoneDto)

    override fun getCourseMilestoneById(id: Int): GetCourseMilestoneResponseDto? = getResourceById(id)

    override fun getAllCourseMilestones(): List<GetCourseMilestoneResponseDto> {
        return getAllResources<GetCourseMilestoneResponseDto>()!!.toList()
    }

    override fun getCourseMilestonesByCourseId(courseId: Int): List<GetCourseMilestoneResponseDto>? {
        TODO("Not yet implemented")
    }

    override fun getCourseMilestonesByCourseIdAndType(
        courseId: Int,
        type: CourseMilestoneType
    ): List<GetCourseMilestoneResponseDto>? {
        TODO("Not yet implemented")
    }
}