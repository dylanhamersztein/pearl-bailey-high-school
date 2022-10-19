package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.CourseMapper.toCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.CourseMapper.toCreateCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
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

@Validated
@Controller
@RequestMapping("/courses")
@Tag(name = "Course Endpoint", description = "Perform CRUD operations on Courses at Pearl Bailey High School")
class CourseEndpoint(
    private val courseService: CourseService,
    private val courseMilestoneService: CourseMilestoneService
) {

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Course by ID.")
    fun getCourse(@PathVariable("id") @Positive id: Int) = courseService.getCourseById(id)
        ?.toCourseResponseDto(courseMilestoneService.getCourseMilestonesByCourseId(id))
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
