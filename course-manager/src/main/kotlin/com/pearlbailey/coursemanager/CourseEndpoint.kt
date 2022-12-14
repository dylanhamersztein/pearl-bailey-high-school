package com.pearlbailey.coursemanager

import com.pearlbailey.commontools.web.PearlBaileyController
import com.pearlbailey.coursemanager.api.CourseConstants.COURSES_RESOURCE_PATH
import com.pearlbailey.coursemanager.api.CourseMapper.toCourseResponseDto
import com.pearlbailey.coursemanager.api.CourseMapper.toCreateCourseResponseDto
import com.pearlbailey.coursemanager.api.model.CourseNotFoundException
import com.pearlbailey.coursemanager.api.model.CreateCourseDto
import com.pearlbailey.coursemanager.api.model.PatchCourseDto
import com.pearlbailey.coursemanager.api.service.CourseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.Positive

@PearlBaileyController
@RequestMapping(COURSES_RESOURCE_PATH)
@Tag(name = "Course Endpoint", description = "Perform CRUD operations on Courses at Pearl Bailey High School")
class CourseEndpoint(private val courseService: CourseService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Get all Courses.")
    fun getAllCourses() = courseService.getAllCourses().map { it.toCourseResponseDto() }

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Course by ID.")
    fun getCourse(@PathVariable("id") @Positive id: Int) = courseService.getCourseById(id)
        ?.toCourseResponseDto()
        ?: throw CourseNotFoundException(id)

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a new Course.")
    fun createCourse(@Valid @RequestBody createCourseDto: CreateCourseDto) =
        courseService.createCourse(createCourseDto).toCreateCourseResponseDto()

    @ResponseBody
    @ResponseStatus(OK)
    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing Course.")
    fun updateCourse(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchCourseDto: PatchCourseDto
    ) = courseService.updateCourse(id, patchCourseDto)?.toCourseResponseDto() ?: throw CourseNotFoundException(id)
}
