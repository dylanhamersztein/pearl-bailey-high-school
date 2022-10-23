package com.pearlbailey.coursemilestonemanager

import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestone
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import org.springframework.data.repository.CrudRepository

interface CourseMilestoneRepository : CrudRepository<CourseMilestone, Int> {

    fun findAllByCourseId(courseId: Int): List<CourseMilestone>

    fun findAllByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>

}
