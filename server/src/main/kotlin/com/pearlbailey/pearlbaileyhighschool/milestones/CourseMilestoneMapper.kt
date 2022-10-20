package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.GetCourseMilestoneResponseDto

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
