package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@Controller
@RequestMapping("/students")
class StudentEndpoint(private val studentService: StudentService) {

    @GetMapping("/{id}")
    fun getStudent(@PathVariable("id") id: Int) =
        studentService.getStudentById(id)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw StudentNotFoundException(id)

    @PostMapping
    fun createStudent(@Valid @RequestBody createStudentDto: CreateStudentDto) =
        studentService.createStudent(createStudentDto).toCreateStudentResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateStudent(
        @PathVariable("id") id: Int, @Valid @RequestBody patchStudentDto: PatchStudentDto
    ) = studentService.updateStudent(id, patchStudentDto)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
        ?: throw StudentNotFoundException(id)

    @GetMapping("/search")
    fun searchStudents(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName == null && lastName == null) {
        throw StudentNotFoundException(message = "One of [firstName, lastName] must be supplied.")
    } else {
        studentService.searchStudentByName(firstName, lastName)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw StudentNotFoundException(message = "Could not find student with supplied params [firstName = $firstName, lastName = $lastName]")
    }
}