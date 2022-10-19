package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.StudentMapper.toCreateStudentResponseDto
import com.pearlbailey.pearlbaileyhighschool.student.StudentMapper.toStudentResponseDto
import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.StudentNotFoundException
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
@RequestMapping("/students")
@Tag(name = "Student Endpoint", description = "Perform CRUD operations on Students at Pearl Bailey High School")
class StudentEndpoint(private val studentService: StudentService) {

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

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/search")
    @Operation(summary = "Search Student by first name or last name.")
    fun searchStudents(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
        throw StudentNotFoundException(message = "One of [firstName, lastName] must be provided as URL parameter.")
    } else {
        studentService.searchStudentByName(firstName, lastName)?.toStudentResponseDto()
            ?: throw StudentNotFoundException(message = "Could not find student with supplied params [firstName = $firstName, lastName = $lastName]")
    }
}
