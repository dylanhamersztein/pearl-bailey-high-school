package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.toCreateDepartmentResponseDto
import com.pearlbailey.pearlbaileyhighschool.department.model.toDepartmentResponseDto
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@Controller
@RequestMapping("/departments")
class DepartmentEndpoint(private val departmentService: DepartmentService) {

    @GetMapping("/{id}")
    fun getDepartment(@PathVariable("id") id: Int) = departmentService.getDepartmentById(id)
        ?.toDepartmentResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping
    fun createDepartment(@Valid @RequestBody createDepartmentDto: CreateDepartmentDto) =
        departmentService.createDepartment(createDepartmentDto)
            ?.toCreateDepartmentResponseDto()
            ?.let { ResponseEntity.status(CREATED).body(it) } ?: ResponseEntity.notFound().build()

    @PatchMapping("/{id}")
    fun updateDepartment(
        @PathVariable("id") id: Int,
        @Valid @RequestBody patchDepartmentDto: PatchDepartmentDto
    ) = departmentService.updateDepartment(id, patchDepartmentDto)
        ?.toDepartmentResponseDto()
        ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/search")
    fun searchDepartments(@RequestParam name: String) =
        departmentService.searchDepartmentByName(name)
            ?.toDepartmentResponseDto()
            ?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
}