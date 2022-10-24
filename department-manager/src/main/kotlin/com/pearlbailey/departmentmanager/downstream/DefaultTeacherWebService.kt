package com.pearlbailey.departmentmanager.downstream

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.teachermanager.api.TeacherConstants.TEACHERS_RESOURCE_PATH
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.GetTeacherResponseDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto
import com.pearlbailey.teachermanager.api.service.TeacherWebService
import org.springframework.web.client.RestTemplate

class DefaultTeacherWebService(teacherManagerUrl: String, restTemplate: RestTemplate) :
    AbstractWebService(teacherManagerUrl, restTemplate), TeacherWebService {

    override fun createTeacher(createTeacherDto: CreateTeacherDto) = createResource(createTeacherDto).id

    override fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): GetTeacherResponseDto? =
        updateResource(id, patchTeacherDto)

    override fun getTeacherById(id: Int): GetTeacherResponseDto? = getResourceById(id)

    override fun getResourceName() = TEACHERS_RESOURCE_PATH

}