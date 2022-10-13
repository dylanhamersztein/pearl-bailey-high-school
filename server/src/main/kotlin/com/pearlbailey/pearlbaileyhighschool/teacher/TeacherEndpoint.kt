package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import com.pearlbailey.pearlbaileyhighschool.teacher.model.toCreateTeacherResponseDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.toTeacherResponseDto
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
@RequestMapping("/teachers")
class TeacherEndpoint(private val teacherService: TeacherService) {

    @GetMapping("/{id}")
    fun getTeacher(@PathVariable("id") @Positive id: Int) =
        teacherService.getTeacherById(id)?.toTeacherResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw TeacherNotFoundException(id)

    @PostMapping
    fun createTeacher(@Valid @RequestBody createTeacherDto: CreateTeacherDto) =
        teacherService.createTeacher(createTeacherDto).toCreateTeacherResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateTeacher(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchTeacherDto: PatchTeacherDto
    ) = teacherService.updateTeacher(id, patchTeacherDto)?.toTeacherResponseDto()?.let { ResponseEntity.ok(it) }
        ?: throw TeacherNotFoundException(id)

    @GetMapping("/search")
    fun searchTeachers(
        @RequestParam(required = false) firstName: String?, @RequestParam(required = false) lastName: String?
    ) = if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
        ResponseEntity.badRequest().build()
    } else {
        teacherService.searchTeacherByName(firstName, lastName)?.toTeacherResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw TeacherNotFoundException(
                message = "Could not find teacher with supplied params [firstName = $firstName, lastName = $lastName]"
            )
    }
}
