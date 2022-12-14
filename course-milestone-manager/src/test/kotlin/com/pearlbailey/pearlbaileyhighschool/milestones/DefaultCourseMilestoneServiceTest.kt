package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.CourseFactory
import com.pearlbailey.coursemanager.api.service.CourseWebService
import com.pearlbailey.coursemilestonemanager.CourseMilestoneRepository
import com.pearlbailey.coursemilestonemanager.DefaultCourseMilestoneService
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneFactory
import com.pearlbailey.coursemilestonemanager.api.service.CourseMilestoneService
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
    private lateinit var courseWebService: CourseWebService

    @BeforeEach
    fun beforeEach() {
        courseMilestoneService = DefaultCourseMilestoneService(courseMilestoneRepository, courseWebService)
    }

    @Test
    fun `should create course milestone when course exists`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()

        val existingCourse = CourseFactory.getCourseResponseDto(id = createCourseMilestoneDto.courseId!!)
        whenever(courseWebService.getCourseById(createCourseMilestoneDto.courseId!!)).thenReturn(existingCourse)

        val createdCourseMilestone = CourseMilestoneFactory.getCourseMilestone(
            courseId = createCourseMilestoneDto.courseId!!,
            milestoneName = createCourseMilestoneDto.name!!,
            courseMilestoneType = createCourseMilestoneDto.type!!
        )
        whenever(courseMilestoneRepository.save(ArgumentMatchers.any())).thenReturn(createdCourseMilestone)

        courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)

        verify(courseWebService).getCourseById(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository).save(any())
    }

    @Test
    fun `should throw CourseNotFoundException when course does not exist on create`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()
        assertThrows<UnprocessableRequestException> {
            courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)
        }

        verify(courseWebService).getCourseById(createCourseMilestoneDto.courseId!!)
        verifyNoInteractions(courseMilestoneRepository)
    }

    @Test
    fun `should throw UnprocessableRequestException when all course milestone weights exceed one on create`() {
        val getCourseResponseDto = CourseFactory.getCourseResponseDto()
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseId = getCourseResponseDto.id)

        whenever(courseWebService.getCourseById(any())).thenReturn(getCourseResponseDto)
        whenever(courseMilestoneRepository.findAllByCourseId(any()))
            .thenReturn(List(3) { CourseMilestoneFactory.getCourseMilestone() })

        assertThrows<UnprocessableRequestException> {
            courseMilestoneService.createCourseMilestone(createCourseMilestoneDto)
        }

        verify(courseWebService).getCourseById(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository).findAllByCourseId(createCourseMilestoneDto.courseId!!)
        verify(courseMilestoneRepository, never()).save(any())
    }

    @Test
    fun `should update course milestone when it already exists`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        val newCourse = CourseFactory.getCourseResponseDto(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseWebService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)

        verify(courseMilestoneRepository).save(any())
        verify(courseWebService).getCourseById(newCourse.id)
    }

    @Test
    fun `should not update name field when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = null)

        val newCourse = CourseFactory.getCourseResponseDto(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseWebService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(courseMilestone.name)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)

        verify(courseMilestoneRepository).save(any())
        verify(courseWebService).getCourseById(newCourse.id)
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
        verifyNoInteractions(courseWebService)
    }

    @Test
    fun `should not update course milestone weight when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(weight = null)

        val newCourse = CourseFactory.getCourseResponseDto(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseWebService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.type).isEqualTo(updateCourseMilestoneDto.type)
        assertThat(updatedCourseMilestone.weight).isEqualTo(courseMilestone.weight)

        verify(courseMilestoneRepository).save(any())
        verify(courseWebService).getCourseById(newCourse.id)
    }

    @Test
    fun `should not update course milestone type field when null on update request`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseMilestoneType = null)

        val newCourse = CourseFactory.getCourseResponseDto(id = updateCourseMilestoneDto.courseId!!)
        whenever(courseWebService.getCourseById(updateCourseMilestoneDto.courseId!!)).thenReturn(newCourse)

        whenever(courseMilestoneRepository.save(ArgumentMatchers.any()))
            .thenAnswer { it.arguments.first() }

        val updatedCourseMilestone = courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)

        assertThat(updatedCourseMilestone!!.courseId!!).isEqualTo(newCourse.id)
        assertThat(updatedCourseMilestone.id).isEqualTo(courseMilestone.id)
        assertThat(updatedCourseMilestone.name).isEqualTo(updateCourseMilestoneDto.name)
        assertThat(updatedCourseMilestone.weight).isEqualTo(updateCourseMilestoneDto.weight)
        assertThat(updatedCourseMilestone.type).isEqualTo(courseMilestone.type)

        verify(courseMilestoneRepository).save(any())
        verify(courseWebService).getCourseById(newCourse.id)
    }

    @Test
    fun `should throw UnprocessableRequestException when new course id does not exist`() {
        val courseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneRepository.findById(courseMilestone.id!!)).thenReturn(Optional.of(courseMilestone))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseMilestoneType = null)

        assertThrows<UnprocessableRequestException> {
            courseMilestoneService.updateCourseMilestone(courseMilestone.id!!, updateCourseMilestoneDto)
        }
    }

}
