package com.pearlbailey.coursemanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemanager.api.CourseConstants.COURSES_RESOURCE_PATH
import com.pearlbailey.coursemanager.api.CourseFactory
import com.pearlbailey.coursemanager.api.service.CourseService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CourseEndpoint::class)
internal class CourseEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var courseService: CourseService

    @Test
    fun `POST - should return 400 when name is blank`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(name = "")

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "name", "must not be blank")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacherId is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(teacherId = null)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "teacherId", "must not be null")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacherId is negative`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(teacherId = -1)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "teacherId", "must be greater than 0")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when departmentId is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(departmentId = null)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "departmentId", "must not be null")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when departmentId is negative`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(departmentId = -1)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "departmentId", "must be greater than 0")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 422 when departmentId does not exist in DB`() {
        whenever(courseService.createCourse(anyOrNull())).thenThrow(UnprocessableRequestException("Department with id 1 not found."))
        val createCourseDto = CourseFactory.getCreateCourseDto()

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.message").value("Department with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `POST - should return 400 when description is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(description = null)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "description", "must not be blank")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when description is blank`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(description = "")

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "description", "must not be blank")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacher does not exist`() {
        whenever(courseService.createCourse(anyOrNull())).thenThrow(UnprocessableRequestException("Teacher with id 1 not found."))

        doPost(COURSES_RESOURCE_PATH, CourseFactory.getCreateCourseDto())
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.message").value("Teacher with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `POST - should return 400 when courseStatus is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(courseStatus = null)

        doPost(COURSES_RESOURCE_PATH, createCourseDto)
            .verifyBadRequestOnPost(COURSES_RESOURCE_PATH, "courseStatus", "must not be null")

        verifyNoInteractions(courseService)
    }

    @Test
    fun `PATCH - should return 200 when patch course information is valid`() {
        val updatedCourse = CourseFactory.getCourse()
        val patchCourseDto = CourseFactory.getPatchCourseDto()

        whenever(courseService.updateCourse(updatedCourse.id!!, patchCourseDto)).thenReturn(updatedCourse)

        doPatch("$COURSES_RESOURCE_PATH/${updatedCourse.id!!}", patchCourseDto)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(updatedCourse.name))
            .andExpect(jsonPath("$.departmentId").value(updatedCourse.departmentId!!))
            .andExpect(jsonPath("$.teacherId").value(updatedCourse.taughtById!!))
            .andExpect(jsonPath("$.description").value(updatedCourse.description))
            .andExpect(jsonPath("$.courseStatus").value(updatedCourse.courseStatus!!.name))


        verify(courseService).updateCourse(updatedCourse.id!!, patchCourseDto)
    }

    @Test
    fun `PATCH - should return 400 when id is 0`() {
        doPatch("$COURSES_RESOURCE_PATH/0", CourseFactory.getPatchCourseDto())
            .verifyBadRequestOnPatch("$COURSES_RESOURCE_PATH/0", "id", "must be greater than 0")
    }

    @Test
    fun `PATCH - should return 400 when id is negative`() {
        doPatch("$COURSES_RESOURCE_PATH/-1", CourseFactory.getPatchCourseDto())
            .verifyBadRequestOnPatch("$COURSES_RESOURCE_PATH/-1", "id", "must be greater than 0")
    }

    @Test
    fun `GET - should return 400 when id is 0`() {
        doGet("$COURSES_RESOURCE_PATH/0")
            .verifyBadRequestOnGet("$COURSES_RESOURCE_PATH/0", "id", "must be greater than 0")
    }

    @Test
    fun `GET - should return 400 when id is negative`() {
        doGet("$COURSES_RESOURCE_PATH/-1")
            .verifyBadRequestOnGet("$COURSES_RESOURCE_PATH/-1", "id", "must be greater than 0")
    }

    @Test
    fun `GET - should return 200`() {
        val storedCourse = CourseFactory.getCourse()
        whenever(courseService.getCourseById(storedCourse.id!!)).thenReturn(storedCourse)

        doGet("$COURSES_RESOURCE_PATH/${storedCourse.id}")
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(storedCourse.name))
            .andExpect(jsonPath("$.departmentId").value(storedCourse.departmentId!!))
            .andExpect(jsonPath("$.teacherId").value(storedCourse.taughtById!!))
            .andExpect(jsonPath("$.description").value(storedCourse.description))
            .andExpect(jsonPath("$.courseStatus").value(storedCourse.courseStatus!!.name))
    }

    @Test
    fun `GET - should return 404 when no course with id`() {
        doGet("$COURSES_RESOURCE_PATH/1")
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Course with id 1 not found."))

        verify(courseService).getCourseById(1)
    }

}