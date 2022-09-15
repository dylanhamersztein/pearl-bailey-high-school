package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.*
import com.pearlbailey.pearlbaileyhighschool.department.DepartmentService
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

sealed interface CourseService {
    fun createCourse(createCourseDto: CreateCourseDto): Int?
    fun updateCourse(id: Int, patchCourseDto: PatchCourseDto): Course?
    fun getCourseById(id: Int): Course?
    fun getAllCoursesWithStatus(courseStatus: CourseStatus): List<Course>
    fun getCoursesByTeacher(teacherId: Int): List<Course>
    fun getCoursesByDepartment(departmentId: Int): List<Course>
}

@Service
class DefaultCourseService(
    private val courseRepository: CourseRepository,
    private val teacherService: TeacherService,
    private val departmentService: DepartmentService
) : CourseService {

    override fun createCourse(createCourseDto: CreateCourseDto) =
        teacherService.getTeacherById(createCourseDto.teacherId)
            ?.let { teacher ->
                departmentService.getDepartmentById(createCourseDto.departmentId)
                    ?.let { department -> teacher to department }
            }
            ?.let { (teacher, department) -> createCourseDto.toCourse(teacher, department) }
            ?.let { courseRepository.save(it) }
            ?.id

    override fun updateCourse(id: Int, patchCourseDto: PatchCourseDto) = getCourseById(id)
        ?.let {
            val headOfCourse = patchCourseDto.teacherId
                ?.let { newId -> teacherService.getTeacherById(newId) ?: throw RuntimeException() }
                ?: it.taughtBy

            val department = patchCourseDto.departmentId
                ?.let { newId -> departmentService.getDepartmentById(newId) ?: throw RuntimeException() }
                ?: it.department

            it.name = patchCourseDto.name ?: it.name
            it.taughtBy = headOfCourse
            it.department = department
            it.description = patchCourseDto.description ?: it.description
            it.courseStatus = patchCourseDto.courseStatus ?: it.courseStatus

            it
        }
        ?.let { courseRepository.save(it) }

    override fun getCourseById(id: Int) = courseRepository.findByIdOrNull(id)

    override fun getAllCoursesWithStatus(courseStatus: CourseStatus) =
        courseRepository.findAllByCourseStatus(courseStatus)

    override fun getCoursesByTeacher(teacherId: Int) = courseRepository.findAllByTaughtBy_Id(teacherId)

    override fun getCoursesByDepartment(departmentId: Int) = courseRepository.findAllByDepartment_Id(departmentId)

}