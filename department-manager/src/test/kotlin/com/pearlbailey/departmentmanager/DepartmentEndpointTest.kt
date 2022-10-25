package com.pearlbailey.departmentmanager

import com.pearlbailey.commontools.EndpointTestParent
import com.pearlbailey.departmentmanager.api.DepartmentConstants.DEPARTMENTS_RESOURCE_PATH
import com.pearlbailey.departmentmanager.api.DepartmentFactory
import com.pearlbailey.departmentmanager.api.service.DepartmentService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DepartmentEndpoint::class)
internal class DepartmentEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var departmentService: DepartmentService

    @Test
    fun `POST - should return 400 when name is blank`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(name = "")

        doPost(DEPARTMENTS_RESOURCE_PATH, createDepartmentDto)
            .verifyBadRequestOnPost(DEPARTMENTS_RESOURCE_PATH, "name", "must not be blank")

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 400 when headOfDepartmentId is null`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(headOfDepartmentId = null)

        doPost(DEPARTMENTS_RESOURCE_PATH, createDepartmentDto)
            .verifyBadRequestOnPost(DEPARTMENTS_RESOURCE_PATH, "headOfDepartmentId", "must not be null")

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 400 when headOfDepartmentId is negative`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(headOfDepartmentId = -1)

        doPost(DEPARTMENTS_RESOURCE_PATH, createDepartmentDto)
            .verifyBadRequestOnPost(DEPARTMENTS_RESOURCE_PATH, "headOfDepartmentId", "must be greater than 0")

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 201 when department information is valid`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto()

        whenever(departmentService.createDepartment(createDepartmentDto)).thenReturn(1)

        doPost(DEPARTMENTS_RESOURCE_PATH, createDepartmentDto)
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(departmentService).createDepartment(createDepartmentDto)
    }

    @Test
    fun `GET - should return 400 on search when first name is null`() {
        doGet("$DEPARTMENTS_RESOURCE_PATH/search").andExpect(status().isBadRequest)

        verifyNoInteractions(departmentService)
    }

}