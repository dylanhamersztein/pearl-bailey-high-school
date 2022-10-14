package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.courses.CourseService
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.util.CourseMilestoneFactory
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.util.CourseFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.any
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
    fun `should update course milestone when it already exists`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        val newCourse = CourseFactory.getCourse(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourse = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourse!!.course!!.id).isEqualTo(newCourse.id)
        assertThat(updatedCourse.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourse.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourse.type).isEqualTo(updateCourseMilestoneDto.type)

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

        val updatedCourse = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourse!!.course!!.id).isEqualTo(newCourse.id)
        assertThat(updatedCourse.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourse.name).isEqualTo(courseMilestone.name)
        assertThat(updatedCourse.type).isEqualTo(updateCourseMilestoneDto.type)

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

        val updatedCourse = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourse!!.course!!.id).isEqualTo(courseMilestone.course!!.id)
        assertThat(updatedCourse.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourse.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourse.type).isEqualTo(updateCourseMilestoneDto.type)

        verify(courseMilestoneRepository).save(any())
        verifyNoInteractions(courseService)
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

        val updatedCourse = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourse!!.course!!.id).isEqualTo(newCourse.id)
        assertThat(updatedCourse.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourse.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourse.type).isEqualTo(courseMilestone.type)

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
