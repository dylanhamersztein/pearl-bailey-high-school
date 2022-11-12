package com.pearlbailey.teachermanager

import com.pearlbailey.commontools.web.PearlBaileyController
import com.pearlbailey.teachermanager.api.TeacherConstants.TEACHERS_RESOURCE_PATH
import com.pearlbailey.teachermanager.api.TeacherMapper.toCreateTeacherResponseDto
import com.pearlbailey.teachermanager.api.TeacherMapper.toTeacherResponseDto
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto
import com.pearlbailey.teachermanager.api.model.web.TeacherNotFoundException
import com.pearlbailey.teachermanager.api.service.TeacherService
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
@RequestMapping(TEACHERS_RESOURCE_PATH)
@Tag(name = "Teacher Endpoint", description = "Perform CRUD operations on Teachers at Pearl Bailey High School")
class TeacherEndpoint(private val teacherService: TeacherService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Get all Teachers.")
    fun getAllTeachers() = teacherService.getAllTeachers().map { it.toTeacherResponseDto() }

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
}
