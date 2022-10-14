package com.pearlbailey.pearlbaileyhighschool.courses.milestones;

import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType
import org.springframework.data.repository.CrudRepository

@Suppress("FunctionName")
interface CourseMilestoneRepository : CrudRepository<CourseMilestone, Int> {

    fun findAllByCourse_Id(courseId: Int): List<CourseMilestone>?

    fun findAllByCourse_IdAndType(courseId: Int, type: CourseMilestoneType): List<CourseMilestone>?

}
