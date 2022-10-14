package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.util.CourseMilestoneFactory
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CourseMilestoneEndpoint::class)
internal class CourseMilestoneEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var courseMilestoneService: CourseMilestoneService

    @Test
    fun `GET - should return 400 when course milestone id is 0`() {
        mvc.perform(get("/course-milestones/0"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseMilestoneId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `GET - should return 400 when course milestone id is negative`() {
        mvc.perform(get("/course-milestones/-1"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseMilestoneId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `GET - should return 404 when course milestone not found`() {
        mvc.perform(get("/course-milestones/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Course Milestone with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `PATCH - should return 404 when course not found`() {
        whenever(courseMilestoneService.updateCourseMilestone(any(), any())).thenThrow(CourseNotFoundException(1))

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()

        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Course with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `PATCH - should return 400 when course milestone name too long`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "a".repeat(1000))

        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("size must be between 1 and 255"))
    }

    @Test
    fun `PATCH - should return 400 when course milestone name too short`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "")

        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("size must be between 1 and 255"))
    }

    @Test
    fun `PATCH - should return 400 when course id negative`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = -1)

        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `PATCH - should return 400 when course id 0`() {
        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = 0)

        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `PATCH - should return 200 when all values are valid`() {
        val newCourseMilestone = CourseMilestoneFactory.getCourseMilestone()
        whenever(courseMilestoneService.updateCourseMilestone(any(), any())).thenReturn(newCourseMilestone)

        val updateCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto()
        mvc.perform(
            patch("/course-milestones/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCourseMilestoneDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(newCourseMilestone.id))
            .andExpect(jsonPath("$.name").value(newCourseMilestone.name))
            .andExpect(jsonPath("$.courseId").value(newCourseMilestone.course!!.id))
            .andExpect(jsonPath("$.type").value(newCourseMilestone.type!!.name))
    }

    @Test
    fun `POST - should return 400 when name is too short`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "")

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("size must be between 1 and 255"))
    }

    @Test
    fun `POST - should return 400 when name is too long`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = "a".repeat(256))

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("size must be between 1 and 255"))
    }

    @Test
    fun `POST - should return 400 when name is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(name = null)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))
    }

    @Test
    fun `POST - should return 400 when course id is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = null)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseId"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))
    }

    @Test
    fun `POST - should return 400 when course id is negative`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = -1)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `POST - should return 400 when course id is 0`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseId = 0)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("courseId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `POST - should return 400 when course milestone type is null`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getUpdateCourseMilestoneDto(courseMilestoneType = null)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("type"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))
    }

    @Test
    fun `POST - should return 201 when course milestone is valid`() {
        val createCourseMilestoneDto = CourseMilestoneFactory.getCreateCourseMilestoneDto()
        whenever(courseMilestoneService.createCourseMilestone(any())).thenReturn(1)

        mvc.perform(
            post("/course-milestones")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseMilestoneDto))
        )
            .andExpect(status().isCreated)
            .andExpect(redirectedUrl("/1"))
    }
}