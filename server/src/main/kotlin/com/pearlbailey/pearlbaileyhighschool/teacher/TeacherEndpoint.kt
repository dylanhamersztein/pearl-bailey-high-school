package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.toCreateTeacherResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.toTeacherResponseDto
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@Controller
@RequestMapping("/teachers")
class TeacherEndpoint(private val teacherService: TeacherService) {

    @GetMapping("/{id}")
    fun getTeacher(@PathVariable("id") id: Int) = teacherService.getTeacherById(id)?.toTeacherResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createTeacher(@Valid @RequestBody createTeacherDto: CreateTeacherDto) =
        teacherService.createTeacher(createTeacherDto).toCreateTeacherResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateTeacher(
        @PathVariable("id") id: Int,
        @Valid @RequestBody patchTeacherDto: PatchTeacherDto
    ) = teacherService.updateTeacher(id, patchTeacherDto)?.toTeacherResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/search")
    fun searchTeachers(
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?
    ) = if (firstName == null && lastName == null) {
        ResponseEntity.badRequest().build();
    } else {
        teacherService.searchTeacherByName(firstName, lastName)?.toTeacherResponseDto()
            ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}