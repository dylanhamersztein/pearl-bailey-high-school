package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.courses.CourseService
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.CourseMilestoneMapper.toEntity
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.UpdateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultCourseMilestoneService(
    private val courseMilestoneRepository: CourseMilestoneRepository, private val courseService: CourseService
) : CourseMilestoneService {

    override fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int {
        val course = courseService.getCourseById(createCourseMilestoneDto.courseId!!)
            ?: throw CourseNotFoundException(createCourseMilestoneDto.courseId)

        return courseMilestoneRepository.save(createCourseMilestoneDto.toEntity(course)).id!!
    }

    override fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone? {
        return courseMilestoneRepository.findByIdOrNull(id)?.let {
            it.name = updateCourseMilestoneDto.name ?: it.name
            it.type = updateCourseMilestoneDto.type ?: it.type
            it.course = updateCourseMilestoneDto.courseId?.let { newId ->
                courseService.getCourseById(newId) ?: throw CourseNotFoundException(newId)
            } ?: it.course

            courseMilestoneRepository.save(it)
        }
    }

    override fun getAllCourseMilestones() = courseMilestoneRepository.findAll().toList()

    override fun getCourseMilestoneById(id: Int) = courseMilestoneRepository.findByIdOrNull(id)

    override fun getCourseMilestonesByCourseId(courseId: Int) = courseMilestoneRepository.findAllByCourse_Id(courseId)

    override fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType) =
        courseMilestoneRepository.findAllByCourse_IdAndType(courseId, type)
}