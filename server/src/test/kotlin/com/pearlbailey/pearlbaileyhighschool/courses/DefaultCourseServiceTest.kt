package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.toCourse
import com.pearlbailey.pearlbaileyhighschool.courses.util.CourseFactory
import com.pearlbailey.pearlbaileyhighschool.department.DepartmentService
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherService
import com.pearlbailey.pearlbaileyhighschool.teacher.util.TeacherFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class DefaultCourseServiceTest {

    private lateinit var courseService: DefaultCourseService

    @MockBean
    private lateinit var courseRepository: CourseRepository

    @MockBean
    private lateinit var teacherService: TeacherService

    @MockBean
    private lateinit var departmentService: DepartmentService

    @BeforeEach
    fun beforeEach() {
        courseService = DefaultCourseService(courseRepository, teacherService, departmentService)
    }

    @Test
    fun `should save a course in a repository`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()

        val department = DepartmentFactory.getDepartment()
        val teacher = TeacherFactory.getTeacher()
        val course = createCourseDto.toCourse(teacher, department)

        whenever(departmentService.getDepartmentById(createCourseDto.departmentId)).thenReturn(department)
        whenever(teacherService.getTeacherById(createCourseDto.teacherId)).thenReturn(teacher)
        whenever(courseRepository.save(course)).thenReturn(course)

        courseService.createCourse(createCourseDto)

        verify(courseRepository).save(check {
            assertThat(it.name).isEqualTo(createCourseDto.name)
            assertThat(it.taughtBy!!.id).isEqualTo(teacher.id)
            assertThat(it.department!!.id).isEqualTo(department.id)
            assertThat(it.description).isEqualTo(createCourseDto.description)
            assertThat(it.courseStatus).isEqualTo(createCourseDto.courseStatus)
        })
    }

    @Test
    fun `should return null when teacher is not found`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(null)

        assertThat(courseService.createCourse(CourseFactory.getCreateCourseDto())).isNull()

        verify(teacherService).getTeacherById(any())
        verifyNoMoreInteractions(teacherService)
        verifyNoInteractions(departmentService, courseRepository)
    }

    @Test
    fun `should return null when department is not found`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(TeacherFactory.getTeacher())
        whenever(departmentService.getDepartmentById(any())).thenReturn(null)

        assertThat(courseService.createCourse(CourseFactory.getCreateCourseDto())).isNull()

        verify(teacherService).getTeacherById(TeacherFactory.getTeacher().id!!)
        verify(departmentService).getDepartmentById(any())
    }

    @Test
    fun `should not call teacher or department services when teacher and department id are null on patch object`() {
        val courseId = 1

        val storedCourse = CourseFactory.getCourse(courseId)
        val patchCourseDto = CourseFactory.getPatchCourseDto(teacherId = null, departmentId = null)

        whenever(courseRepository.findById(courseId)).thenReturn(Optional.of(storedCourse))

        courseService.updateCourse(courseId, patchCourseDto)

        verify(courseRepository).findById(eq(courseId))
        verify(courseRepository).save(check {
            assertThat(it.id).isEqualTo(courseId)
            assertThat(it.name).isEqualTo(patchCourseDto.name)
            assertThat(it.description).isEqualTo(patchCourseDto.description)
            assertThat(it.courseStatus).isEqualTo(patchCourseDto.courseStatus)

            // teacher and department have not changed
            assertThat(it.taughtBy!!.id!!).isEqualTo(storedCourse.taughtBy!!.id!!)
            assertThat(it.department!!.id!!).isEqualTo(storedCourse.department!!.id!!)
        })

        verifyNoInteractions(teacherService, departmentService)
    }

    @Test
    fun `should not do anything when course to update does not exist`() {
        whenever(courseRepository.findById(anyOrNull())).thenReturn(Optional.empty())

        courseService.updateCourse(1, CourseFactory.getPatchCourseDto())

        verify(courseRepository).findById(1)
        verifyNoMoreInteractions(courseRepository)
    }

}