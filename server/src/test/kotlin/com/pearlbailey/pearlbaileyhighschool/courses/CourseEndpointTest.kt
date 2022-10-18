package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.courses.util.CourseFactory
import com.pearlbailey.pearlbaileyhighschool.department.model.DepartmentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneService
import com.pearlbailey.pearlbaileyhighschool.milestones.util.CourseMilestoneFactory
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.anyOrNull
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

    @MockBean
    private lateinit var courseMilestoneService: CourseMilestoneService

    @Test
    fun `POST - should return 400 when name is blank`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(name = "")
        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacherId is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(teacherId = null)

        mvc.perform(post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("teacherId"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacherId is negative`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(teacherId = -1)

        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("teacherId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when departmentId is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(departmentId = null)

        mvc.perform(post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("departmentId"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when departmentId is negative`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(departmentId = -1)

        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("departmentId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when departmentId does not exist in DB`() {
        whenever(courseService.createCourse(anyOrNull())).thenThrow(DepartmentNotFoundException(1))
        val createCourseDto = CourseFactory.getCreateCourseDto()

        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Department with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `POST - should return 400 when description is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(description = null)

        mvc.perform(post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when description is blank`() {
        val createCourseDto = CourseFactory.getCreateCourseDto().copy(description = "")

        mvc.perform(post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /courses"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("description"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(courseService)
    }

    @Test
    fun `POST - should return 400 when teacher does not exist`() {
        whenever(courseService.createCourse(anyOrNull())).thenThrow(TeacherNotFoundException(1))

        mvc.perform(
            post(COURSES).contentType(APPLICATION_JSON)
                .content(toJson(CourseFactory.getCreateCourseDto()))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Teacher with id 1 not found."))
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    fun `POST - should return 400 when courseStatus is null`() {
        val createCourseDto = CourseFactory.getCreateCourseDto(courseStatus = null)

        mvc.perform(post(COURSES).contentType(APPLICATION_JSON).content(toJson(createCourseDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(courseService)
    }

    @Test
    fun `PATCH - should return 200 when patch course information is valid`() {
        val updatedCourse = CourseFactory.getCourse()
        val patchCourseDto = CourseFactory.getPatchCourseDto()

        whenever(courseService.updateCourse(updatedCourse.id!!, patchCourseDto)).thenReturn(updatedCourse)

        mvc.perform(
            patch("$COURSES/${updatedCourse.id!!}").contentType(APPLICATION_JSON)
                .content(toJson(patchCourseDto))
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
    fun `PATCH - should return 400 when id is 0`() {
        mvc.perform(
            patch("$COURSES/0").contentType(APPLICATION_JSON)
                .content(toJson(CourseFactory.getPatchCourseDto()))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `PATCH - should return 400 when id is negative`() {
        mvc.perform(
            patch("$COURSES/-1").contentType(APPLICATION_JSON)
                .content(toJson(CourseFactory.getPatchCourseDto()))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("id"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `GET - should return 400 when id is 0`() {
        mvc.perform(get("$COURSES/0"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("id"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `GET - should return 400 when id is negative`() {
        mvc.perform(get("$COURSES/-1"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("id"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))
    }

    @Test
    fun `GET - should return 200`() {
        val storedCourse = CourseFactory.getCourse()
        val courseMilestones = listOf(CourseMilestoneFactory.getCourseMilestone(courseId = storedCourse.id!!))
        whenever(courseService.getCourseById(storedCourse.id!!)).thenReturn(storedCourse)
        whenever(courseMilestoneService.getCourseMilestonesByCourseId(storedCourse.id!!)).thenReturn(courseMilestones)

        mvc.perform(get("$COURSES/${storedCourse.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(storedCourse.name))
            .andExpect(jsonPath("$.departmentId").value(storedCourse.department!!.id))
            .andExpect(jsonPath("$.teacherId").value(storedCourse.taughtBy!!.id))
            .andExpect(jsonPath("$.description").value(storedCourse.description))
            .andExpect(jsonPath("$.courseStatus").value(storedCourse.courseStatus!!.name))
            .andExpect(jsonPath("$.courseMilestones").isArray)
            .andExpect(jsonPath("$.courseMilestones.size()").value(1))
            .andExpect(jsonPath("$.courseMilestones.[0].id").value(courseMilestones.first().id))
            .andExpect(jsonPath("$.courseMilestones.[0].name").value(courseMilestones.first().name))
            .andExpect(jsonPath("$.courseMilestones.[0].courseId").value(courseMilestones.first().courseId))
            .andExpect(jsonPath("$.courseMilestones.[0].type").value(courseMilestones.first().type!!.name))
    }

    @Test
    fun `GET - should return 404 when no course with id`() {
        mvc.perform(get("$COURSES/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Course with id 1 not found."))

        verify(courseService).getCourseById(1)
    }

    companion object {
        private const val COURSES = "/courses"
    }
}