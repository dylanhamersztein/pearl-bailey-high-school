package com.pearlbailey.coursemanager

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.CourseMapper.toCourse
import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto
import com.pearlbailey.coursemanager.api.service.CourseService
import com.pearlbailey.departmentmanager.api.service.DepartmentWebService
import com.pearlbailey.teachermanager.api.service.TeacherWebService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultCourseService(
    private val courseRepository: CourseRepository,
    private val teacherService: TeacherWebService,
    private val departmentService: DepartmentWebService
) : CourseService {

    override fun getAllCourses() = courseRepository.findAll().toList()

    override fun createCourse(createCourseDto: CreateCourseDto): Int {
        val teacher = teacherService.getTeacherById(createCourseDto.teacherId!!)
            ?: throw UnprocessableRequestException("Could not find Teacher with id ${createCourseDto.teacherId}")

        val department = departmentService.getDepartmentById(createCourseDto.departmentId!!)
            ?: throw UnprocessableRequestException("Could not find Department with id ${createCourseDto.departmentId}")

        val newCourse = createCourseDto.toCourse(teacher, department)
        return courseRepository.save(newCourse).id!!
    }

    override fun updateCourse(id: Int, patchCourseDto: PatchCourseDto) = getCourseById(id)
        ?.let {
            val headOfCourse = patchCourseDto.teacherId
                ?.let { newId ->
                    teacherService.getTeacherById(newId)?.id
                        ?: throw UnprocessableRequestException("Could not find Teacher with id $newId")
                }
                ?: it.taughtById

            val department = patchCourseDto.departmentId
                ?.let { newId ->
                    departmentService.getDepartmentById(newId)?.id
                        ?: throw UnprocessableRequestException("Could not find Department with id $newId")
                }
                ?: it.departmentId

            it.name = patchCourseDto.name ?: it.name
            it.taughtById = headOfCourse
            it.departmentId = department
            it.description = patchCourseDto.description ?: it.description
            it.courseStatus = patchCourseDto.courseStatus ?: it.courseStatus

            it
        }
        ?.let { courseRepository.save(it) }

    override fun getCourseById(id: Int): Course? = courseRepository.findByIdOrNull(id)

    override fun getAllCoursesWithStatus(courseStatus: CourseStatus) =
        courseRepository.findAllByCourseStatus(courseStatus)

    override fun getCoursesByTeacher(teacherId: Int) = courseRepository.findAllByTaughtById(teacherId)

    override fun getCoursesByDepartment(departmentId: Int) = courseRepository.findAllByDepartmentId(departmentId)

}
