package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.StudentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.student.model.toCreateStudentResponseDto
import com.pearlbailey.pearlbaileyhighschool.student.model.toStudentResponseDto
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
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/students")
class StudentEndpoint(private val studentService: StudentService) {

    @GetMapping("/{id}")
    fun getStudent(@PathVariable("id") @Positive id: Int) =
        studentService.getStudentById(id)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw StudentNotFoundException(id)

    @PostMapping
    fun createStudent(@Valid @RequestBody createStudentDto: CreateStudentDto) =
        studentService.createStudent(createStudentDto).toCreateStudentResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateStudent(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchStudentDto: PatchStudentDto
    ) = studentService.updateStudent(id, patchStudentDto)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
        ?: throw StudentNotFoundException(id)

    @GetMapping("/search")
    fun searchStudents(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
        ResponseEntity.badRequest().build();
    } else {
        studentService.searchStudentByName(firstName, lastName)?.toStudentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw StudentNotFoundException(
                message = "Could not find student with supplied params [firstName = $firstName, lastName = $lastName]"
            )
    }
}
