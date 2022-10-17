package com.pearlbailey.pearlbaileyhighschool.courses.milestones;

import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType
import org.springframework.data.repository.CrudRepository

interface CourseMilestoneRepository : CrudRepository<CourseMilestone, Int> {

    fun findAllByCourseId(courseId: Int): List<CourseMilestone>?

    fun findAllByCourseIdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>?

}
