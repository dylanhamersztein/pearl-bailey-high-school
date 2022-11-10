package com.pearlbailey.studentmanager.jpa

import com.pearlbailey.commontools.web.PearlBaileyController
import com.pearlbailey.studentmanager.api.StudentConstants.STUDENTS_RESOURCE_PATH
import com.pearlbailey.studentmanager.api.StudentMapper.toCreateStudentResponseDto
import com.pearlbailey.studentmanager.api.StudentMapper.toStudentResponseDto
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.StudentNotFoundException
import com.pearlbailey.studentmanager.api.service.StudentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.CrossOrigin
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

@CrossOrigin
@Profile("jpa")
@PearlBaileyController
@RequestMapping(STUDENTS_RESOURCE_PATH)
@Tag(name = "Student Endpoint", description = "Perform CRUD operations on Students at Pearl Bailey High School")
class StudentEndpoint(private val studentService: StudentService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Get all Students.")
    fun getAllStudents() = studentService.getAllStudents().map { it.toStudentResponseDto() }

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Student by ID.")
    fun getStudent(@PathVariable("id") @Positive id: Int) =
        studentService.getStudentById(id)?.toStudentResponseDto() ?: throw StudentNotFoundException(id)

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a Student.")
    fun createStudent(@Valid @RequestBody createStudentDto: CreateStudentDto) =
        studentService.createStudent(createStudentDto).toCreateStudentResponseDto()

    @ResponseBody
    @ResponseStatus(OK)
    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing Student.")
    fun updateStudent(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchStudentDto: PatchStudentDto
    ) = studentService.updateStudent(id, patchStudentDto)?.toStudentResponseDto() ?: throw StudentNotFoundException(id)

}
