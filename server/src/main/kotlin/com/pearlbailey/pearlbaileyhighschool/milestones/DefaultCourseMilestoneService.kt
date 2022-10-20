package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.UnprocessableRequestException
import com.pearlbailey.pearlbaileyhighschool.courses.CourseService
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toEntity
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestone
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneType
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal.ONE

@Service
class DefaultCourseMilestoneService(
    private val courseMilestoneRepository: CourseMilestoneRepository, private val courseService: CourseService
) : CourseMilestoneService {

    override fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int {
        val courseId = courseService.getCourseById(createCourseMilestoneDto.courseId!!)?.id
            ?: throw CourseNotFoundException(createCourseMilestoneDto.courseId)

        val invalidWeights = createCourseMilestoneDto.weight!! + courseMilestoneRepository.findAllByCourseId(courseId)
            .sumOf { it.weight!! } > ONE

        if (invalidWeights) {
            throw UnprocessableRequestException("Total course milestone weights in Course with id $courseId exceed 1.")
        }

        return courseMilestoneRepository.save(createCourseMilestoneDto.toEntity(courseId)).id!!
    }

    override fun updateCourseMilestone(id: Int, updateCourseMilestoneDto: UpdateCourseMilestoneDto): CourseMilestone? {
        return courseMilestoneRepository.findByIdOrNull(id)
            ?.apply {
                val courseId = updateCourseMilestoneDto.courseId
                    ?.let { newId -> courseService.getCourseById(newId)?.id ?: throw CourseNotFoundException(newId) }
                    ?: courseId

                val weight = updateCourseMilestoneDto.weight
                    ?.let { newWeight ->
                        val invalidWeights = newWeight + courseMilestoneRepository.findAllByCourseId(courseId!!)
                            .filterNot { it.id == id }
                            .sumOf { it.weight!! } > ONE

                        if (invalidWeights) {
                            throw UnprocessableRequestException("Total course milestone weights in Course with id $courseId exceed 1.")
                        }

                        newWeight
                    }
                    ?: weight

                name = updateCourseMilestoneDto.name ?: name
                type = updateCourseMilestoneDto.type ?: type
                this.courseId = courseId
                this.weight = weight
            }
            ?.let { courseMilestoneRepository.save(it) }
    }

    override fun getAllCourseMilestones() = courseMilestoneRepository.findAll().toList()

    override fun getCourseMilestoneById(id: Int) = courseMilestoneRepository.findByIdOrNull(id)

    override fun getCourseMilestonesByCourseId(courseId: Int) = courseMilestoneRepository.findAllByCourseId(courseId)

    override fun getCourseMilestonesByCourseIdAndType(courseId: Int, type: CourseMilestoneType) =
        courseMilestoneRepository.findAllByCourseIdAndType(courseId, type)

}
