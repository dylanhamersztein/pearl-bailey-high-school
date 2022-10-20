package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.UnprocessableRequestException
import com.pearlbailey.pearlbaileyhighschool.courses.CourseService
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.util.CourseFactory
import com.pearlbailey.pearlbaileyhighschool.milestones.util.CourseMilestoneFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class DefaultCourseMilestoneServiceTest {

    private lateinit var courseMilestoneService: CourseMilestoneService

    @MockBean
    private lateinit var courseMilestoneRepository: CourseMilestoneRepository

    @MockBean
    private lateinit var courseService: CourseService

    @BeforeEach
    fun beforeEach() {
        courseMilestoneService = DefaultCourseMilestoneService(courseMilestoneRepository, courseService)
    }

    @Test
    fun `should create course milestone when course exists`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()

        val existingCourse = CourseFactory.getCourse(id = createCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(createCourseMilestoneDto.courseId!!)).thenReturn(existingCourse)

        val createdCourseMilestone = CourseMilestoneFactory.getCourseMilestone(
            courseId = createCourseMilestoneDto.courseId!!,
            milestoneName = createCourseMilestoneDto.name!!,
            courseMilestoneType = createCourseMilestoneDto.type!!
        )
        whenever(courseMilestoneRepository.save(ArgumentMatchers.any())).thenReturn(createdCourseMilestone)

        courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)

        verify(courseService).getCourseById(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository).save(any())
    }

    @Test
    fun `should throw CourseNotFoundException when course does not exist on create`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()
        assertThrows<CourseNotFoundException>() {
            courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)
        }

        verify(courseService).getCourseById(createCourseMilestoneDto.courseId!!)
        verifyNoInteractions(courseMilestoneRepository)
    }

    @Test
    fun `should throw UnprocessableRequestException when all course milestone weights exceed one on create`() {
        val course = CourseFactory.getCourse()
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseId = course.id)

        whenever(courseService.getCourseById(any())).thenReturn(course)
        whenever(courseMilestoneRepository.findAllByCourseId(any()))
            .thenReturn(List(3) { CourseMilestoneFactory.getCourseMilestone() })

        assertThrows<UnprocessableRequestException>() {
            courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)
        }

        verify(courseService).getCourseById(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository).findAllByCourseId(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository, never()).save(any())
    }

    @Test
    fun `should update course milestone when it already exists`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        val newCourse = CourseFactory.getCourse(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)

        verify(courseMilestoneRepository).save(any())
        verify(courseService).getCourseById(newCourse.id!!)
    }

    @Test
    fun `should not update name field when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = null)

        val newCourse = CourseFactory.getCourse(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(courseMilestone.name)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)

        verify(courseMilestoneRepository).save(any())
        verify(courseService).getCourseById(newCourse.id!!)
    }

    @Test
    fun `should not update course id when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = null)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(courseMilestone.courseId!!)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)

        verify(courseMilestoneRepository).save(any())
        verifyNoInteractions(courseService)
    }

    @Test
    fun `should not update course milestone weight when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(weight = null)

        val newCourse = CourseFactory.getCourse(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)
        assertThat(updatedCourseMilestone.weight).isEqualTo(courseMilestone.weight)

        verify(courseMilestoneRepository).save(any())
        verify(courseService).getCourseById(newCourse.id!!)
    }

    @Test
    fun `should not update course milestone type field when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseMilestoneType = null)

        val newCourse = CourseFactory.getCourse(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)
        assertThat(updatedCourseMilestone.type).isEqualTo(courseMilestone.type)

        verify(courseMilestoneRepository).save(any())
        verify(courseService).getCourseById(newCourse.id!!)
    }

    @Test
    fun `should throw CourseNotFoundException when new course id does not exist`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseMilestoneType = null)

        assertThrows<CourseNotFoundException>() {
            courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)
        }
    }

}
