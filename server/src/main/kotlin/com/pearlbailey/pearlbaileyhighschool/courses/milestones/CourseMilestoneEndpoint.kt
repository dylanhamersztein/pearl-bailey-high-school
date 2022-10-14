package com.pearlbailey.pearlbaileyhighschool.courses.milestones

import com.pearlbailey.pearlbaileyhighschool.courses.milestones.CourseMilestoneMapper.toCourseMilestoneResponse
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.CourseMilestoneMapper.toCreatedResourceResponse
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CourseMilestoneNotFoundException
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.CreateCourseMilestoneDto
import com.pearlbailey.pearlbaileyhighschool.courses.milestones.model.UpdateCourseMilestoneDto
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
class CourseMilestoneEndpoint(private val courseMilestoneService: CourseMilestoneService) {

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun getAll() = courseMilestoneService.getAllCourseMilestones()

    @GetMapping("/{courseMilestoneId}")
    @ResponseBody
    @ResponseStatus(OK)
    fun getById(@PathVariable @Positive courseMilestoneId: Int) =
        courseMilestoneService.getCourseMilestoneById(courseMilestoneId)
            ?: throw CourseMilestoneNotFoundException(courseMilestoneId)

    @PostMapping
    fun createCourseMilestone(@RequestBody @Valid createCourseMilestoneDto: CreateCourseMilestoneDto): ResponseEntity<Any> {
        return courseMilestoneService.createCourseMilestone(createCourseMilestoneDto).toCreatedResourceResponse().let {
            ResponseEntity.created(URI.create("/${it.id}")).build()
        }
    }

    @PatchMapping("/{courseMilestoneId}")
    @ResponseBody
    @ResponseStatus(OK)
    fun updateCourseMilestone(
        @PathVariable @Positive courseMilestoneId: Int,
        @RequestBody @Valid updateCourseMilestoneDto: UpdateCourseMilestoneDto
    ) = courseMilestoneService.updateCourseMilestone(courseMilestoneId, updateCourseMilestoneDto)
        ?.toCourseMilestoneResponse() ?: throw CourseMilestoneNotFoundException(courseMilestoneId)
}