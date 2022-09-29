package com.pearlbailey.pearlbaileyhighschool.courses

import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.model.CreateCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.PatchCourseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.toCourseResponseDto
import com.pearlbailey.pearlbaileyhighschool.courses.model.toCreateCourseResponseDto
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

@Validated
@Controller
@RequestMapping("/courses")
class CourseEndpoint(private val courseService: CourseService) {

    @GetMapping("/{id}")
    fun getCourse(@PathVariable("id") id: Int) =
        courseService.getCourseById(id)?.toCourseResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw CourseNotFoundException(id)

    @PostMapping
    fun createCourse(@Valid @RequestBody createCourseDto: CreateCourseDto) =
        courseService.createCourse(createCourseDto).toCreateCourseResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateCourse(
        @PathVariable("id") id: Int, @Valid @RequestBody patchCourseDto: PatchCourseDto
    ) = courseService.updateCourse(id, patchCourseDto)?.toCourseResponseDto()?.let { ResponseEntity.ok(it) }
        ?: throw CourseNotFoundException(id)
}
