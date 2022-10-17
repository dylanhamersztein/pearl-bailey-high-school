package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.CourseMapper.toCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.CourseMapper.toCreateCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.GetCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/courses")
class CourseEndpoint(
    private val courseService: CourseService,
    private val courseMilestoneService: CourseMilestoneService
) {

    @GetMapping("/{id}")
    fun getCourse(@PathVariable("id") @Positive id: Int): ResponseEntity<GetCourseResponseDto> {
        val courseMilestones = courseMilestoneService.getCourseMilestonesByCourseId(id)

        return courseService.getCourseById(id)?.toCourseResponseDto(courseMilestones)?.let { ResponseEntity.ok(it) }
            ?: throw CourseNotFoundException(id)
    }

    @PostMapping
    fun createCourse(@Valid @RequestBody createCourseDto: CreateCourseDto) =
        courseService.createCourse(createCourseDto).toCreateCourseResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateCourse(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchCourseDto: PatchCourseDto
    ) = courseService.updateCourse(id, patchCourseDto)?.toCourseResponseDto()?.let { ResponseEntity.ok(it) }
        ?: throw CourseNotFoundException(id)
}
