package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.common.model.web.CreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CreateCourseMilestoneDto

object CourseMilestoneMapper {

    fun CreateCourseMilestoneDto.toEntity(courseId: Int) = CourseMilestone().apply {
        this.name = this@toEntity.name
        this.courseId = courseId
        this.type = this@toEntity.type
    }

    fun Int.toCreatedResourceResponse() = CreatedResourceResponse(this)

    fun CourseMilestone.toCourseMilestoneResponse() = CourseMilestoneResponse(id!!, name!!, courseId!!, type!!)

}
