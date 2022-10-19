package com.pearlbailey.pearlbaileyhighschool.milestones

import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toCourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.CourseMilestoneMapper.toCreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CourseMilestoneNotFoundException
import com.pearlbailey.pearlbaileyhighschool.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.milestones.model.UpdateCourseMilestoneDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
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

@Validated
@Controller
@RequestMapping("/course-milestones")
@Tag(name = "Course Milestone Endpoint", description = "Perform CRUD operations on Course Milestones at Pearl Bailey High School")
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
