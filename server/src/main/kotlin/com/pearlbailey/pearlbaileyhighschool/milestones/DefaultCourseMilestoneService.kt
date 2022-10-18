package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.courses.CourseService
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toEntity
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultCourseMilestoneService(
    private val courseMilestoneRepository: CourseMilestoneRepository, private val courseService: CourseService
) : CourseMilestoneService {

    override fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int {
        val courseId = courseService.getCourseById(createCourseMilestoneDto.courseId!!)?.id
            ?: throw CourseNotFoundException(createCourseMilestoneDto.courseId)

        return courseMilestoneRepository.save(createCourseMilestoneDto.toEntity(courseId)).id!!
    }

    override fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone? {
        return courseMilestoneRepository.findByIdOrNull(id)
            ?.let {
                val courseId = updateCourseMilestoneDto.courseId
                    ?.let { newId -> courseService.getCourseById(newId)?.id ?: throw CourseNotFoundException(newId) }
                    ?: it.courseId

                it.name = updateCourseMilestoneDto.name ?: it.name
                it.type = updateCourseMilestoneDto.type ?: it.type
                it.courseId = courseId

                it
            }
            ?.let { courseMilestoneRepository.save(it) }
    }

    override fun getAllCourseMilestones() = courseMilestoneRepository.findAll().toList()

    override fun getCourseMilestoneById(id: Int) = courseMilestoneRepository.findByIdOrNull(id)

    override fun getCourseMilestonesByCourseId(courseId: Int) = courseMilestoneRepository.findAllByCourseId(courseId)

    override fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType) =
        courseMilestoneRepository.findAllByCourseIdAndType(courseId, type)
}
