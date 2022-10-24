package com.pearlbailey.enrolmentmanager.downstream

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.coursemanager.api.model.CourseStatus
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.GetCourseResponseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto
import com.pearlbailey.coursemanager.api.service.CourseWebService
import com.pearlbailey.enrolmentmanager.api.EnrolmentConstants.ENROLLMENTS_RESOURCE_PATH
import org.springframework.web.client.RestTemplate

class DefaultCourseWebService(baseUrl: String, restTemplate: RestTemplate) :
    AbstractWebService(baseUrl, restTemplate), CourseWebService {

    override fun getResourceName() = ENROLLMENTS_RESOURCE_PATH

    override fun getCourseById(id: Int): GetCourseResponseDto? = getResourceById(id)

    override fun createCourse(createCourseDto: CreateCourseDto) = throw UnsupportedOperationException()

    override fun updateCourse(id: Int, patchCourseDto: PatchCourseDto) = throw UnsupportedOperationException()

    override fun getAllCoursesWithStatus(courseStatus: CourseStatus) = throw UnsupportedOperationException()

    override fun getCoursesByTeacher(teacherId: Int) = throw UnsupportedOperationException()

    override fun getCoursesByDepartment(departmentId: Int) = throw UnsupportedOperationException()

}