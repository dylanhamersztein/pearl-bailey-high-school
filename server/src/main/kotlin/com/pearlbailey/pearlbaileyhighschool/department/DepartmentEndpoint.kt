package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.DepartmentMapper.toCreateDepartmentResponseDto
import com.pearlbailey.pearlbaileyhighschool.department.DepartmentMapper.toDepartmentResponseDto
import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.DepartmentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
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
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

@Validated
@Controller
@RequestMapping("/departments")
@Tag(name = "Department Endpoint", description = "Perform CRUD operations on Departments at Pearl Bailey High School")
class DepartmentEndpoint(private val departmentService: DepartmentService) {

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Department by ID.")
    fun getDepartment(@PathVariable("id") @Positive id: Int) =
        departmentService.getDepartmentById(id)?.toDepartmentResponseDto()
            ?: throw DepartmentNotFoundException(id)

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a new Department.")
    fun createDepartment(@Valid @RequestBody createDepartmentDto: CreateDepartmentDto) =
        departmentService.createDepartment(createDepartmentDto).toCreateDepartmentResponseDto()

    @PatchMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Update an existing Department.")
    fun updateDepartment(
        @PathVariable("id") @Positive id: Int, @Valid @RequestBody patchDepartmentDto: PatchDepartmentDto
    ) = departmentService.updateDepartment(id, patchDepartmentDto)?.toDepartmentResponseDto()
        ?: throw DepartmentNotFoundException(id)

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/search")
    @Operation(summary = "Search for a Department by name.")
    fun searchDepartments(@RequestParam @NotEmpty name: String) =
        departmentService.searchDepartmentByName(name)?.toDepartmentResponseDto()
            ?: throw DepartmentNotFoundException(message = "Could not find teacher with supplied params [name = $name]")
}
