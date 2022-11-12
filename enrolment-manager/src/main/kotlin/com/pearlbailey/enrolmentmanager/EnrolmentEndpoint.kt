package com.pearlbailey.enrolmentmanager

import com.pearlbailey.commontools.web.PearlBaileyController
import com.pearlbailey.enrolmentmanager.api.EnrolmentConstants.ENROLLMENTS_RESOURCE_PATH
import com.pearlbailey.enrolmentmanager.api.EnrolmentMapper.toCreateEnrolmentResponseDto
import com.pearlbailey.enrolmentmanager.api.EnrolmentMapper.toEnrolmentResponseDto
import com.pearlbailey.enrolmentmanager.api.model.CreateEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.model.EnrolmentNotFoundException
import com.pearlbailey.enrolmentmanager.api.model.PatchEnrolmentDto
import com.pearlbailey.enrolmentmanager.api.service.EnrolmentService
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
@RequestMapping(ENROLLMENTS_RESOURCE_PATH)
@Tag(name = "Enrollments Endpoint", description = "Perform CRUD operations on Enrollments at Pearl Bailey High School")
class EnrolmentEndpoint(private val enrolmentService: EnrolmentService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Get all Enrolments")
    fun getAllEnrolments() = enrolmentService.getAllEnrolments().map { it.toEnrolmentResponseDto() }

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    @Operation(summary = "Get Enrolment by ID.")
    fun getEnrolmentById(@PathVariable @Positive id: Int) =
        enrolmentService.getEnrolmentById(id)?.toEnrolmentResponseDto() ?: throw EnrolmentNotFoundException(id)

    @PostMapping
    @ResponseBody
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a new Enrolment.")
    fun createEnrolment(@RequestBody @Valid createEnrolmentDto: CreateEnrolmentDto) =
        enrolmentService.createEnrolment(createEnrolmentDto).toCreateEnrolmentResponseDto()

    @ResponseBody
    @ResponseStatus(OK)
    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing Enrolment.")
    fun updateEnrolment(@PathVariable @Positive id: Int, @RequestBody @Valid patchEnrolmentDto: PatchEnrolmentDto) =
        enrolmentService.updateEnrolment(id, patchEnrolmentDto)?.toEnrolmentResponseDto()
            ?: throw EnrolmentNotFoundException(id)

}
