package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/teachers")
class TeacherEndpoint(private val teacherService: TeacherService) {

    @GetMapping("/{id}")
    fun getTeacher(@PathVariable("id") @Positive id: Int) =
        teacherService.getTeacherById(id)?.toTeacherResponseDto()?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createTeacher(@Valid @RequestBody createTeacherDto: CreateTeacherDto) =
        teacherService.createTeacher(createTeacherDto).toCreateTeacherResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateTeacher(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchTeacherDto: PatchTeacherDto
    ) = teacherService.updateTeacher(id, patchTeacherDto).toTeacherResponseDto().let { ResponseEntity.ok(it) }

    @GetMapping("/search")
    fun searchTeachers(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName == null && lastName == null) {
        throw TeacherNotFoundException(message = "One of [firstName, lastName] must be supplied.")
    } else {
        teacherService.searchTeacherByName(firstName, lastName)?.toTeacherResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw TeacherNotFoundException(message = "Could not find teacher with supplied params [firstName = $firstName, lastName = $lastName]")
    }
}