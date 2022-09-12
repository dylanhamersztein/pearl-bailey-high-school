package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.toCreateStudentResponseDto
import com.pearlbailey.pearlbaileyhighschool.student.model.toStudentResponseDto
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
    fun getStudent(@PathVariable("id") id: Int) = studentService.getStudentById(id)?.toStudentResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createStudent(@Valid @RequestBody createStudentDto: CreateStudentDto) =
        studentService.createStudent(createStudentDto).toCreateStudentResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateStudent(
        @PathVariable("id") id: Int,
        @Valid @RequestBody patchStudentDto: PatchStudentDto
    ) = studentService.updateStudent(id, patchStudentDto)?.toStudentResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/search")
    fun searchStudents(
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?
    ) = if (firstName == null && lastName == null) {
        ResponseEntity.badRequest().build();
    } else {
        studentService.searchStudentByName(firstName, lastName)?.toStudentResponseDto()
            ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}