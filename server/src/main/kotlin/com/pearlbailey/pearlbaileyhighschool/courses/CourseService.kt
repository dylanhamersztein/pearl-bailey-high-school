package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.Course
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseStatus
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.toCourse
import com.pearlbailey.pearlbaileyhighschool.department.DepartmentService
import com.pearlbailey.pearlbaileyhighschool.department.model.DepartmentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherService
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

sealed interface CourseService {
    fun createCourse(createCourseDto: CreateCourseDto): Int
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

    override fun createCourse(createCourseDto: CreateCourseDto): Int {
        val teacher = teacherService.getTeacherById(createCourseDto.teacherId)
            ?: throw TeacherNotFoundException(createCourseDto.teacherId)

        val department = departmentService.getDepartmentById(createCourseDto.departmentId)
            ?: throw DepartmentNotFoundException(createCourseDto.departmentId)

        val newCourse = createCourseDto.toCourse(teacher, department)
        return courseRepository.save(newCourse).id!!
    }

    override fun updateCourse(id: Int, patchCourseDto: PatchCourseDto) = getCourseById(id)
        ?.let {
            val headOfCourse = patchCourseDto.teacherId
                ?.let { newId -> teacherService.getTeacherById(newId) }
                ?: it.taughtBy

            val department = patchCourseDto.departmentId
                ?.let { newId -> departmentService.getDepartmentById(newId) }
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
