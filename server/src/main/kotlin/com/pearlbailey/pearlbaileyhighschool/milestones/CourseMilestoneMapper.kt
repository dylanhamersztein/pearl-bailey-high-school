package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto

object CourseMilestoneMapper {

    fun CreateCourseMilestoneDto.toEntity(courseId: Int) = CourseMilestone().apply {
        this.name = this@toEntity.name
        this.courseId = courseId
        this.type = this@toEntity.type
    }

    fun Int.toCreatedResourceResponse() = CreatedResourceResponse(this)

    fun CourseMilestone.toCourseMilestoneResponse() = CourseMilestoneResponse(id!!, name!!, courseId!!, type!!)

}
