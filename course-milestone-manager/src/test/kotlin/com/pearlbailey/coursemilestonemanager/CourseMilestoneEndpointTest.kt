package com.pearlbailey.coursemilestonemanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.commontools.exception.UnprocessableRequestException
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneConstants.COURSE_MILESTONES_RESOURCE_PATH
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneFactory
import com.pearlbailey.coursemilestonemanager.api.service.CourseMilestoneService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO

@WebMvcTest(CourseMilestoneEndpoint::class)
internal class CourseMilestoneEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var courseMilestoneService: CourseMilestoneService

    @Test
    fun `GET - should return 400 when course milestone id is 0`() {
        doGet("$COURSE_MILESTONES_RESOURCE_PATH/0")
            .verifyBadRequestOnGet("$COURSE_MILESTONES_RESOURCE_PATH/0", "courseMilestoneId", "must be greater than 0")
    }

    @Test
    fun `GET - should return 400 when course milestone id is negative`() {
        doGet("$COURSE_MILESTONES_RESOURCE_PATH/-1")
            .verifyBadRequestOnGet("$COURSE_MILESTONES_RESOURCE_PATH/-1", "courseMilestoneId", "must be greater than 0")
    }

    @Test
    fun `GET - should return 404 when course milestone not found`() {
        doGet("$COURSE_MILESTONES_RESOURCE_PATH/1")
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Course Milestone with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `PATCH - should return 422 when course not found`() {
        whenever(courseMilestoneService.updateCourseMilestone(any(), any()))
            .thenThrow(UnprocessableRequestException("Course with id 1 not found."))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.message").value("Course with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `PATCH - should return 422 when all course milestone weights exceed 1`() {
        whenever(courseMilestoneService.updateCourseMilestone(any(), any()))
            .thenThrow(UnprocessableRequestException("Total course milestone weights in Course with id 1 exceed 1."))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.message").value("Total course milestone weights in Course with id 1 exceed 1."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `PATCH - should return 400 when course milestone name too long`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "a".repeat(1000))

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "name", "size must be between 1 and 255")
    }

    @Test
    fun `PATCH - should return 400 when course milestone name too short`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "")

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "name", "size must be between 1 and 255")
    }

    @Test
    fun `PATCH - should return 400 when course id negative`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = -1)

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "courseId", "must be greater than 0")
    }

    @Test
    fun `PATCH - should return 400 when weight is 0`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(weight = ZERO)

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "weight", "must be greater than 0")
    }

    @Test
    fun `PATCH - should return 400 when weight is greater than 1`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(weight = TEN)

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "weight", "must be less than or equal to 1")
    }

    @Test
    fun `PATCH - should return 400 when weight is negative`() {
        val updateCourseMilestoneDto =
            CourseMilestoneFactory.getUpdateCourseMilestoneDto(weight = 0.56.toBigDecimal().negate())

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "weight", "must be greater than 0")
    }

    @Test
    fun `PATCH - should return 400 when course id 0`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = 0)

        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .verifyBadRequestOnPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", "courseId", "must be greater than 0")
    }

    @Test
    fun `PATCH - should return 200 when all values are valid`() {
        val newCourseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneService.updateCourseMilestone(any(), any())).thenReturn(newCourseMilestone)

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()
        doPatch("$COURSE_MILESTONES_RESOURCE_PATH/1", updateCourseMilestoneDto)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(newCourseMilestone.id))
            .andExpect(jsonPath("$.name").value(newCourseMilestone.name))
            .andExpect(jsonPath("$.courseId").value(newCourseMilestone.courseId!!))
            .andExpect(jsonPath("$.type").value(newCourseMilestone.type!!.name))
    }

    @Test
    fun `POST - should return 400 when name is too short`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(name = "")

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "name", "size must be between 1 and 255")
    }

    @Test
    fun `POST - should return 422 when all course milestone weights exceed 1`() {
        whenever(courseMilestoneService.createCourseMilestone(any()))
            .thenThrow(UnprocessableRequestException("Total course milestone weights in Course with id 1 exceed 1."))

        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.message").value("Total course milestone weights in Course with id 1 exceed 1."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `POST - should return 400 when name is too long`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(name = "a".repeat(256))

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "name", "size must be between 1 and 255")
    }

    @Test
    fun `POST - should return 400 when name is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(name = null)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "name", "must not be null")
    }

    @Test
    fun `POST - should return 400 when course id is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseId = null)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "courseId", "must not be null")
    }

    @Test
    fun `POST - should return 400 when course id is negative`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseId = -1)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "courseId", "must be greater than 0")
    }

    @Test
    fun `POST - should return 400 when weight is 0`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(weight = ZERO)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "weight", "must be greater than 0")
    }

    @Test
    fun `POST - should return 400 when weight is greater than 1`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(weight = TEN)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "weight", "must be less than or equal to 1")
    }

    @Test
    fun `POST - should return 400 when weight negative`() {
        val createCourseMilestoneDto =
            CourseMilestoneFactory.getCreateCourseMilestoneDto(weight = 0.55.toBigDecimal().negate())

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "weight", "must be greater than 0")
    }

    @Test
    fun `POST - should return 400 when course id is 0`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseId = 0)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "courseId", "must be greater than 0")
    }

    @Test
    fun `POST - should return 400 when course milestone type is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto(courseMilestoneType = null)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .verifyBadRequestOnPost(COURSE_MILESTONES_RESOURCE_PATH, "type", "must not be null")
    }

    @Test
    fun `POST - should return 201 when course milestone is valid`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()
        whenever(courseMilestoneService.createCourseMilestone(any())).thenReturn(1)

        doPost(COURSE_MILESTONES_RESOURCE_PATH, createCourseMilestoneDto)
            .andExpect(status().isCreated)
            .andExpect(redirectedUrl("/1"))
    }
}