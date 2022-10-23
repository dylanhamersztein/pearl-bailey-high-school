package com.pearlbailey.coursemilestonemanager.api

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestone
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.GetCourseMilestoneResponseDto

object CourseMilestoneMapper {

    fun CreateCourseMilestoneDto.toEntity(courseId: Int) = CourseMilestone().apply {
        this.name = this@toEntity.name
        this.courseId = courseId
        this.weight = this@toEntity.weight
        this.type = this@toEntity.type
    }

    fun Int.toCreatedResourceResponse() = CreatedResourceResponse(this)

    fun CourseMilestone.toCourseMilestoneResponse() = GetCourseMilestoneResponseDto(id!!, name!!, courseId!!, weight!!, type!!)

}
