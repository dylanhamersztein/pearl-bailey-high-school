package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherMapper.toCreateTeacherResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherMapper.toTeacherResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/teachers")
@Tag(name = "Teacher Endpoint", description = "Perform CRUD operations on Teachers at Pearl Bailey High School")
class TeacherEndpoint(private val teacherService: TeacherService) {

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Teacher by ID.")
    fun getTeacher(@PathVariable("id") @Positive id: Int) =
        teacherService.getTeacherById(id)?.toTeacherResponseDto() ?: throw TeacherNotFoundException(id)

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a Teacher.")
    fun createTeacher(@Valid @RequestBody createTeacherDto: CreateTeacherDto) =
        teacherService.createTeacher(createTeacherDto).toCreateTeacherResponseDto()

    @ResponseBody
    @ResponseStatus(OK)
    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing Teacher.")
    fun updateTeacher(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchTeacherDto: PatchTeacherDto
    ) = teacherService.updateTeacher(id, patchTeacherDto)?.toTeacherResponseDto() ?: throw TeacherNotFoundException(id)

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/search")
    @Operation(summary = "Search Teacher by first name or last name.")
    fun searchTeachers(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
        throw TeacherNotFoundException(message = "One of [firstName, lastName] must be provided as URL parameter.")
    } else {
        teacherService.searchTeacherByName(firstName, lastName)?.toTeacherResponseDto()
            ?: throw TeacherNotFoundException(
                message = "Could not find teacher with supplied params [firstName = $firstName, lastName = $lastName]"
            )
    }
}
