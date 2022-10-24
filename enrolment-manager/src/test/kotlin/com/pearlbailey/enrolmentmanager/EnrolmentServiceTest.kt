package com.pearlbailey.enrolmentmanager

import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.CourseFactory
import com.pearlbailey.coursemanager.api.service.CourseWebService
import com.pearlbailey.enrolmentmanager.api.EnrolmentFactory
import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import com.pearlbailey.studentmanager.api.StudentFactory
import com.pearlbailey.studentmanager.api.service.StudentWebService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class EnrolmentServiceTest {

    private lateinit var enrolmentService: EnrolmentService

    @MockBean
    private lateinit var enrolmentRepository: EnrolmentRepository

    @MockBean
    private lateinit var studentWebService: StudentWebService

    @MockBean
    private lateinit var courseWebService: CourseWebService

    @BeforeEach
    fun setUp() {
        enrolmentService = EnrolmentService(enrolmentRepository, studentWebService, courseWebService)
    }

    @Test
    fun `CREATE - should save enrolment in a repository`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto()
        val student = StudentFactory.getStudentResponseDto(id = createEnrolmentDto.studentId!!)
        val course = CourseFactory.getCourseResponseDto(id = createEnrolmentDto.courseId!!)

        whenever(studentWebService.getStudentById(any())).thenReturn(student)
        whenever(courseWebService.getCourseById(any())).thenReturn(course)
        whenever(enrolmentRepository.save(Mockito.any(Enrolment::class.java)))
            .thenAnswer { (it.arguments.first() as Enrolment).apply { id = 1 } }

        enrolmentService.createEnrolment(createEnrolmentDto)

        verify(studentWebService).getStudentById(createEnrolmentDto.studentId!!)
        verify(courseWebService).getCourseById(createEnrolmentDto.courseId!!)
        verify(enrolmentRepository).save(any())

        verifyNoMoreInteractions(enrolmentRepository, studentWebService, courseWebService)
    }

    @Test
    fun `CREATE - should throw UnprocessableRequestException when student not found`() {
        whenever(studentWebService.getStudentById(any())).thenReturn(null)

        val exception = assertThrows<UnprocessableRequestException> {
            enrolmentService.createEnrolment(EnrolmentFactory.getCreateEnrolmentDto())
        }

        assertThat(exception).hasMessageContaining("Student")

        verify(studentWebService).getStudentById(any())
        verifyNoMoreInteractions(studentWebService)

        verifyNoInteractions(courseWebService, enrolmentRepository)
    }

    @Test
    fun `CREATE - should throw UnprocessableRequestException when course not found`() {
        val createEnrolmentDto = EnrolmentFactory.getCreateEnrolmentDto()
        val student = StudentFactory.getStudentResponseDto(id = createEnrolmentDto.studentId!!)

        whenever(studentWebService.getStudentById(any())).thenReturn(student)
        whenever(courseWebService.getCourseById(any())).thenReturn(null)

        val exception = assertThrows<UnprocessableRequestException> {
            enrolmentService.createEnrolment(EnrolmentFactory.getCreateEnrolmentDto())
        }

        assertThat(exception).hasMessageContaining("Course")

        verify(studentWebService).getStudentById(any())
        verify(courseWebService).getCourseById(any())

        verifyNoMoreInteractions(studentWebService, courseWebService)
        verifyNoInteractions(enrolmentRepository)
    }

    @Test
    fun `UPDATE - should return null if course does not exist`() {
        whenever(enrolmentRepository.findById(any())).thenReturn(Optional.empty())

        val updatedEnrolment = enrolmentService.updateEnrolment(1, EnrolmentFactory.getPatchEnrolmentDto())
        assertThat(updatedEnrolment).isNull()

        verify(enrolmentRepository).findById(any())
        verifyNoMoreInteractions(enrolmentRepository)
        verifyNoInteractions(studentWebService, courseWebService)
    }

    @Test
    fun `UPDATE - should call into both web services when student and course id are provided`() {
        val existingEnrolment = EnrolmentFactory.getEnrolment()
        whenever(enrolmentRepository.findById(any())).thenReturn(Optional.of(existingEnrolment))

        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto()

        whenever(courseWebService.getCourseById(any()))
            .thenReturn(CourseFactory.getCourseResponseDto(id = patchEnrolmentDto.courseId!!))

        whenever(studentWebService.getStudentById(any()))
            .thenReturn(StudentFactory.getStudentResponseDto(id = patchEnrolmentDto.studentId!!))

        whenever(enrolmentRepository.save(Mockito.any(Enrolment::class.java))).thenAnswer { it.arguments.first() }

        enrolmentService.updateEnrolment(existingEnrolment.id!!, patchEnrolmentDto)

        verify(enrolmentRepository).save(check {
            assertThat(it.id).isEqualTo(existingEnrolment.id)
            assertThat(it.studentId).isEqualTo(patchEnrolmentDto.studentId)
            assertThat(it.courseId).isEqualTo(patchEnrolmentDto.courseId)
        })

        verify(courseWebService).getCourseById(patchEnrolmentDto.courseId!!)
        verify(studentWebService).getStudentById(patchEnrolmentDto.studentId!!)
    }

    @Test
    fun `UPDATE - should only call into student service when new course id not provided`() {
        val existingEnrolment = EnrolmentFactory.getEnrolment()
        whenever(enrolmentRepository.findById(any())).thenReturn(Optional.of(existingEnrolment))

        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto(courseId = null)

        whenever(studentWebService.getStudentById(any()))
            .thenReturn(StudentFactory.getStudentResponseDto(id = patchEnrolmentDto.studentId!!))

        whenever(enrolmentRepository.save(Mockito.any(Enrolment::class.java))).thenAnswer { it.arguments.first() }

        enrolmentService.updateEnrolment(existingEnrolment.id!!, patchEnrolmentDto)

        verify(enrolmentRepository).save(check {
            assertThat(it.id).isEqualTo(existingEnrolment.id)
            assertThat(it.studentId).isEqualTo(patchEnrolmentDto.studentId)
            assertThat(it.courseId).isEqualTo(existingEnrolment.courseId)
        })

        verify(studentWebService).getStudentById(patchEnrolmentDto.studentId!!)
        verifyNoInteractions(courseWebService)
    }

    @Test
    fun `UPDATE - should only call into student service when new student id not provided`() {
        val existingEnrolment = EnrolmentFactory.getEnrolment()
        whenever(enrolmentRepository.findById(any())).thenReturn(Optional.of(existingEnrolment))

        val patchEnrolmentDto = EnrolmentFactory.getPatchEnrolmentDto(studentId = null)

        whenever(courseWebService.getCourseById(any()))
            .thenReturn(CourseFactory.getCourseResponseDto(id = patchEnrolmentDto.courseId!!))

        whenever(enrolmentRepository.save(Mockito.any(Enrolment::class.java))).thenAnswer { it.arguments.first() }

        enrolmentService.updateEnrolment(existingEnrolment.id!!, patchEnrolmentDto)

        verify(enrolmentRepository).save(check {
            assertThat(it.id).isEqualTo(existingEnrolment.id)
            assertThat(it.studentId).isEqualTo(existingEnrolment.studentId)
            assertThat(it.courseId).isEqualTo(patchEnrolmentDto.courseId)
        })

        verify(courseWebService).getCourseById(patchEnrolmentDto.courseId!!)
        verifyNoInteractions(studentWebService)
    }

}