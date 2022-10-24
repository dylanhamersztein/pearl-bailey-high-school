package com.pearlbailey.coursemilestonemanager

import com.pearlbailey.commontools.web.PearlBaileyController
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneConstants.COURSE_MILESTONES_RESOURCE_PATH
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneMapper.toCourseMilestoneResponse
import com.pearlbailey.coursemilestonemanager.api.CourseMilestoneMapper.toCreatedResourceResponse
import com.pearlbailey.coursemilestonemanager.api.model.CourseMilestoneNotFoundException
import com.pearlbailey.coursemilestonemanager.api.model.CreateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.model.UpdateCourseMilestoneDto
import com.pearlbailey.coursemilestonemanager.api.service.CourseMilestoneService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.net.URI
import javax.validation.Valid
import javax.validation.constraints.Positive

@PearlBaileyController
@RequestMapping(COURSE_MILESTONES_RESOURCE_PATH)
@Tag(
    name = "Course Milestone Endpoint",
    description = "Perform CRUD operations on Course Milestones at Pearl Bailey High School"
)
class CourseMilestoneEndpoint(private val courseMilestoneService: CourseMilestoneService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    @Operation(summary = "Get all stored Course Milestones.")
    fun getAll() = courseMilestoneService.getAllCourseMilestones().map { it.toCourseMilestoneResponse() }

    @ResponseBody
    @ResponseStatus(OK)
    @GetMapping("/{courseMilestoneId}")
    @Operation(summary = "Get Course Milestone by ID.")
    fun getById(@PathVariable @Positive courseMilestoneId: Int) =
        courseMilestoneService.getCourseMilestoneById(courseMilestoneId)?.toCourseMilestoneResponse()
            ?: throw CourseMilestoneNotFoundException(courseMilestoneId)

    @PostMapping
    @Operation(summary = "Create a new Course Milestone.")
    fun createCourseMilestone(@RequestBody @Valid createCourseMilestoneDto: CreateCourseMilestoneDto) =
        courseMilestoneService.createCourseMilestone(createCourseMilestoneDto).toCreatedResourceResponse()
            .let { ResponseEntity.created(URI.create("/${it.id}")).build<Any>() }

    @ResponseBody
    @ResponseStatus(OK)
    @PatchMapping("/{courseMilestoneId}")
    @Operation(summary = "Update an existing Course Milestone.")
    fun updateCourseMilestone(
        @PathVariable @Positive courseMilestoneId: Int,
        @RequestBody @Valid updateCourseMilestoneDto: UpdateCourseMilestoneDto
    ) = courseMilestoneService.updateCourseMilestone(courseMilestoneId, updateCourseMilestoneDto)
        ?.toCourseMilestoneResponse() ?: throw CourseMilestoneNotFoundException(courseMilestoneId)
}
