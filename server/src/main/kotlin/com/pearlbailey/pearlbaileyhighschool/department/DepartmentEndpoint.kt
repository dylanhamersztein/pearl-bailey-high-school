package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/departments")
class DepartmentEndpoint(private val departmentService: DepartmentService) {

    @GetMapping("/{id}")
    fun getDepartment(@PathVariable("id") id: Int) =
        departmentService.getDepartmentById(id)?.toDepartmentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw DepartmentNotFoundException(id)

    @PostMapping
    fun createDepartment(@Valid @RequestBody createDepartmentDto: CreateDepartmentDto) =
        departmentService.createDepartment(createDepartmentDto).toCreateDepartmentResponseDto()
            .let { ResponseEntity.status(CREATED).body(it) }

    @PatchMapping("/{id}")
    fun updateDepartment(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchDepartmentDto: PatchDepartmentDto
    ) = departmentService.updateDepartment(id, patchDepartmentDto)?.toDepartmentResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: throw DepartmentNotFoundException(id)

    @GetMapping("/search")
    fun searchDepartments(@RequestParam name: String) =
        departmentService.searchDepartmentByName(name)?.toDepartmentResponseDto()?.let { ResponseEntity.ok(it) }
            ?: throw DepartmentNotFoundException(message = "Could not find teacher with supplied params [name = $name]")
}