package com.pearlbailey.coursemanager.api.service.impl

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.coursemanager.api.CourseConstants.COURSE_RESOURCE_PATH
import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.GetCourseResponseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto
import com.pearlbailey.coursemanager.api.service.CourseWebService
import org.springframework.web.client.RestTemplate

class DefaultCourseWebService(baseUrl: String, override val restTemplate : RestTemplate) :
    AbstractWebService(baseUrl, restTemplate), CourseWebService {

    override fun createCourse(createCourseDto: CreateCourseDto) = createResource(createCourseDto).id

    override fun updateCourse(id: Int, patchCourseDto: PatchCourseDto): GetCourseResponseDto? =
        updateResource(id, patchCourseDto)

    override fun getCourseById(id: Int): GetCourseResponseDto? = getResourceById(id)

    override fun getAllCoursesWithStatus(courseStatus: CourseStatus): List<GetCourseResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getCoursesByTeacher(teacherId: Int): List<GetCourseResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getCoursesByDepartment(departmentId: Int): List<GetCourseResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getResourceName() = COURSE_RESOURCE_PATH
}