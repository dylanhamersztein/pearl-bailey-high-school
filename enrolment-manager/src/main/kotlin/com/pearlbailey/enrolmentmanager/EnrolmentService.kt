package com.pearlbailey.enrolmentmanager

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.service.CourseWebService
import com.pearlbailey.enrolmentmanager.api.EnrolmentMapper.toEnrolment
import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import com.pearlbailey.enrolmentmanager.api.model.PatchEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.service.EnrolmentService
import com.pearlbailey.studentmanager.api.service.StudentWebService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EnrolmentService(
    private val enrolmentRepository: EnrolmentRepository,
    private val studentWebService: StudentWebService,
    private val courseWebService: CourseWebService
) : EnrolmentService {
    override fun getAllEnrolments() = enrolmentRepository.findAll().toList()

    override fun createEnrolment(createEnrolmentDto: CreateEnrolmentDto): Int {
        studentWebService.getStudentById(createEnrolmentDto.studentId!!)
            ?: throw UnprocessableRequestException("Student with id [${createEnrolmentDto.studentId}] not found.")

        courseWebService.getCourseById(createEnrolmentDto.courseId!!)
            ?: throw UnprocessableRequestException("Course with id [${createEnrolmentDto.courseId}] not found.")

        return enrolmentRepository.save(createEnrolmentDto.toEnrolment()).id!!
    }

    override fun updateEnrolment(id: Int, patchEnrolmentDto: PatchEnrolmentDto): Enrolment? {
        return getEnrolmentById(id)
            ?.apply {
                val studentId = patchEnrolmentDto.studentId?.let { newStudentId ->
                    studentWebService.getStudentById(newStudentId)?.id
                        ?: throw UnprocessableRequestException("Student with id [$newStudentId] not found.")
                } ?: studentId

                val courseId = patchEnrolmentDto.courseId?.let { newCourseId ->
                    courseWebService.getCourseById(newCourseId)?.id
                        ?: throw UnprocessableRequestException("Course with id [$newCourseId] not found.")
                } ?: courseId

                this.studentId = studentId
                this.courseId = courseId
            }
            ?.let { enrolmentRepository.save(it) }
    }

    override fun getEnrolmentById(id: Int) = enrolmentRepository.findByIdOrNull(id)
}