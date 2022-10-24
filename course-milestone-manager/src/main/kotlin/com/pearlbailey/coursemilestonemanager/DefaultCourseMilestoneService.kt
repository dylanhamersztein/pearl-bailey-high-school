package com.pearlbailey.coursemilestonemanager

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.service.CourseWebService
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneMapper.toEntity
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestone
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneType
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.service.CourseMilestoneService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal.ONE

@Service
class DefaultCourseMilestoneService(
    private val courseMilestoneRepository: CourseMilestoneRepository,
    private val courseWebService: CourseWebService
) : CourseMilestoneService {

    override fun createCourseMilestone(createCourseMilestoneDto: CreateCourseMilestoneDto): Int {
        val courseId = courseWebService.getCourseById(createCourseMilestoneDto.courseId!!)?.id
            ?: throw UnprocessableRequestException("Could not find Department with id ${createCourseMilestoneDto.courseId}.")

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
                    ?.let { newId ->
                        courseWebService.getCourseById(newId)?.id
                            ?: throw UnprocessableRequestException("Could not find Course with id $newId.")
                    }
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
