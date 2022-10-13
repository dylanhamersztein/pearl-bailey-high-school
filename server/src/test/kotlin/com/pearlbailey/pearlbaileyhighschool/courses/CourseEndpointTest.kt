package com.pearlbailey.pearlbaileyhighschool.courses

import com.fasterxml.jackson.databind.node.ObjectNode
import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.courses.util.CourseFactory
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CourseEndpoint::class)
internal class CourseEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var courseService: CourseService

    @Test
    fun `should return 400 when name is blank on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(name = "")
        mvc.perform(post(COURSES, objectMapper.writeValueAsString(createCourseDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when teacherId is null on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()
        val jsonNode = toObjectNode(createCourseDto).apply {
            remove("teacherId")
        }

        mvc.perform(post(COURSES, jsonNode.toPrettyString()))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when teacherId is negative on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(teacherId = -1)

        mvc.perform(post(COURSES, objectMapper.writeValueAsString(createCourseDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when departmentId is null on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()
        val jsonNode = toObjectNode(createCourseDto).apply {
            remove("departmentId")
        }

        mvc.perform(post(COURSES, jsonNode.toPrettyString()))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when departmentId is negative on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(departmentId = -1)

        mvc.perform(post(COURSES, objectMapper.writeValueAsString(createCourseDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when description is null on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()
        val jsonNode = toObjectNode(createCourseDto).apply {
            remove("description")
        }

        mvc.perform(post(COURSES, jsonNode.toPrettyString()))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when description is blank on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(description = "")

        mvc.perform(post(COURSES, objectMapper.writeValueAsString(createCourseDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 400 when courseStatus is null on create`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()
        val jsonNode = toObjectNode(createCourseDto).apply {
            remove("courseStatus")
        }

        mvc.perform(post(COURSES, jsonNode.toPrettyString()))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `should return 200 when course information is valid`() {
        val createCourseDto = CourseFactory.getCreateCourseDto()

        whenever(courseService.createCourse(createCourseDto)).thenReturn(1)

        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(courseService).createCourse(createCourseDto)
    }

    @Test
    fun `should return 200 when patch course information is valid`() {
        val updatedCourse = CourseFactory.getCourse()
        val patchCourseDto = CourseFactory.getPatchCourseDto()

        whenever(courseService.updateCourse(updatedCourse.id!!, patchCourseDto)).thenReturn(updatedCourse)

        mvc.perform(
            patch("$COURSES/${updatedCourse.id!!}").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchCourseDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(updatedCourse.name))
            .andExpect(jsonPath("$.departmentId").value(updatedCourse.department!!.id))
            .andExpect(jsonPath("$.teacherId").value(updatedCourse.taughtBy!!.id))
            .andExpect(jsonPath("$.description").value(updatedCourse.description))
            .andExpect(jsonPath("$.courseStatus").value(updatedCourse.courseStatus!!.name))


        verify(courseService).updateCourse(updatedCourse.id!!, patchCourseDto)
    }

    @Test
    fun `should return 400 on get when id is 0`() {
        mvc.perform(get("$COURSES/0")).andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 on get when id is negative`() {
        mvc.perform(get("$COURSES/-1")).andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 on patch when id is 0`() {
        mvc.perform(
            patch("$COURSES/0").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CourseFactory.getPatchCourseDto()))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 on patch when id is negative`() {
        mvc.perform(
            patch("$COURSES/-1").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CourseFactory.getPatchCourseDto()))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 200 on get`() {
        val storedCourse = CourseFactory.getCourse()
        whenever(courseService.getCourseById(storedCourse.id!!)).thenReturn(storedCourse)

        mvc.perform(get("$COURSES/${storedCourse.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(storedCourse.name))
            .andExpect(jsonPath("$.departmentId").value(storedCourse.department!!.id))
            .andExpect(jsonPath("$.teacherId").value(storedCourse.taughtBy!!.id))
            .andExpect(jsonPath("$.description").value(storedCourse.description))
            .andExpect(jsonPath("$.courseStatus").value(storedCourse.courseStatus!!.name))
    }

    @Test
    fun `should return 404 on get when no course with id`() {
        mvc.perform(get("$COURSES/1")).andExpect(status().isNotFound)
        verify(courseService).getCourseById(1)
    }

    private fun toObjectNode(obj: Any) = objectMapper.readTree(objectMapper.writeValueAsString(obj)) as ObjectNode

    companion object {
        private const val COURSES = "/courses"
    }
}