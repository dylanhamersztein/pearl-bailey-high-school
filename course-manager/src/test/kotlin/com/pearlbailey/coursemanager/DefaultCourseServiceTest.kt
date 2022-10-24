package com.pearlbailey.coursemanager

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.CourseFactory
import com.pearlbailey.coursemanager.api.model.Course
import com.pearlbailey.departmentmanager.api.DepartmentFactory
import com.pearlbailey.departmentmanager.api.service.DepartmentWebService
import com.pearlbailey.teachermanager.api.TeacherFactory
import com.pearlbailey.teachermanager.api.service.TeacherWebService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class DefaultCourseServiceTest {

    private lateinit var courseService: DefaultCourseService

    @MockBean
    private lateinit var courseRepository: CourseRepository

    @MockBean
    private lateinit var teacherService: TeacherWebService

    @MockBean
    private lateinit var departmentService: DepartmentWebService

    @BeforeEach
    fun beforeEach() {
        courseService = DefaultCourseService(courseRepository, teacherService, departmentService)
    }

    @Test
    fun `should save a course in a repository`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()
        val department = DepartmentFactory.getDepartmentResponseDto()
        val teacher = TeacherFactory.getTeacherResponseDto()

        whenever(departmentService.getDepartmentById(createCourseDto.departmentId!!)).thenReturn(department)
        whenever(teacherService.getTeacherById(createCourseDto.teacherId!!)).thenReturn(teacher)
        whenever(courseRepository.save(any(Course::class.java))).thenAnswer {
            (it.arguments[0] as Course).apply { id = 1 }
        }

        courseService.createCourse(createCourseDto)

        verify(courseRepository).save(check {
            assertThat(it.name).isEqualTo(createCourseDto.name)
            assertThat(it.taughtById!!).isEqualTo(teacher.id)
            assertThat(it.departmentId!!).isEqualTo(department.id)
            assertThat(it.description).isEqualTo(createCourseDto.description)
            assertThat(it.courseStatus).isEqualTo(createCourseDto.courseStatus)
        })
    }

    @Test
    fun `should return throw TeacherNotFoundException when teacher is not found on create`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(null)

        assertThrows<UnprocessableRequestException> {
            courseService.createCourse(CourseFactory.getCreateCourseDto())
        }

        verify(teacherService).getTeacherById(any())
        verifyNoMoreInteractions(teacherService)
        verifyNoInteractions(departmentService, courseRepository)
    }

    @Test
    fun `should return throw TeacherNotFoundException when teacher is not found on update`() {
        whenever(courseRepository.findById(any())).thenReturn(Optional.of(CourseFactory.getCourse()))
        whenever(teacherService.getTeacherById(any())).thenReturn(null)

        assertThrows<UnprocessableRequestException> {
            courseService.updateCourse(1, CourseFactory.getPatchCourseDto())
        }

        verify(teacherService).getTeacherById(any())
        verifyNoMoreInteractions(teacherService)
        verifyNoInteractions(departmentService)
    }

    @Test
    fun `should throw DepartmentNotFoundException null when department is not found on create`() {
        whenever(teacherService.getTeacherById(any())).thenReturn(TeacherFactory.getTeacherResponseDto())
        whenever(departmentService.getDepartmentById(any())).thenReturn(null)

        assertThrows<UnprocessableRequestException> {
            courseService.createCourse(CourseFactory.getCreateCourseDto())
        }

        verify(teacherService).getTeacherById(TeacherFactory.getTeacher().id!!)
        verify(departmentService).getDepartmentById(any())
    }

    @Test
    fun `should throw DepartmentNotFoundException null when department is not found on update`() {
        whenever(courseRepository.findById(any())).thenReturn(Optional.of(CourseFactory.getCourse()))
        whenever(teacherService.getTeacherById(any())).thenReturn(TeacherFactory.getTeacherResponseDto())
        whenever(departmentService.getDepartmentById(any())).thenReturn(null)

        assertThrows<UnprocessableRequestException> {
            courseService.updateCourse(1, CourseFactory.getPatchCourseDto())
        }

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
            assertThat(it.taughtById!!).isEqualTo(storedCourse.taughtById!!)
            assertThat(it.departmentId!!).isEqualTo(storedCourse.departmentId!!)
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

    private fun <T> any(type: Class<T>): T = Mockito.any(type)

}