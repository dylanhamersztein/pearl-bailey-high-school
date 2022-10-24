package com.pearlbailey.departmentmanager.api.service.impl

import com.pearlbailey.commontools.web.AbstractWebService
import com.pearlbailey.departmentmanager.api.DepartmentConstants.DEPARTMENTS_RESOURCE_PATH
import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.GetDepartmentResponseDto
import com.pearlbailey.departmentmanager.api.model.PatchDepartmentDto
import com.pearlbailey.departmentmanager.api.service.DepartmentWebService
import org.springframework.web.client.RestTemplate

class DefaultDepartmentWebService(baseUrl: String, restTemplate: RestTemplate) :
    AbstractWebService(baseUrl, restTemplate), DepartmentWebService {

    override fun createDepartment(createDepartmentDto: CreateDepartmentDto) = createResource(createDepartmentDto).id

    override fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto): GetDepartmentResponseDto? =
        updateResource(id, patchDepartmentDto)

    override fun getDepartmentById(id: Int): GetDepartmentResponseDto? = getResourceById(id)

    override fun getResourceName() = DEPARTMENTS_RESOURCE_PATH

}