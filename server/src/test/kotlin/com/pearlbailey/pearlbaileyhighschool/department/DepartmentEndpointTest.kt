package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.common.EndpointTestParent
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DepartmentEndpoint::class)
internal class DepartmentEndpointTest : EndpointTestParent() {

    @MockBean
    private lateinit var departmentService: DepartmentService

    @Test
    fun `POST - should return 400 when name is blank`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(name = "")
        mvc.perform(post(DEPARTMENTS).contentType(APPLICATION_JSON).content(toJson(createDepartmentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /departments"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("name"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be blank"))

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 400 when headOfDepartmentId is null`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(headOfDepartmentId = null)

        mvc.perform(post(DEPARTMENTS).contentType(APPLICATION_JSON).content(toJson(createDepartmentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /departments"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("headOfDepartmentId"))
            .andExpect(jsonPath("$.errors[0].error").value("must not be null"))

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 400 when headOfDepartmentId is negative`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(headOfDepartmentId = -1)

        mvc.perform(post(DEPARTMENTS).contentType(APPLICATION_JSON).content(toJson(createDepartmentDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Failed to invoke POST /departments"))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value("headOfDepartmentId"))
            .andExpect(jsonPath("$.errors[0].error").value("must be greater than 0"))

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `POST - should return 201 when department information is valid`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto()

        whenever(departmentService.createDepartment(createDepartmentDto)).thenReturn(1)

        mvc.perform(
            post(DEPARTMENTS).contentType(APPLICATION_JSON).content(toJson(createDepartmentDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(departmentService).createDepartment(createDepartmentDto)
    }
    
    @Test
    fun `GET - should return 400 on search when first name is null`() {
        mvc.perform(get("$DEPARTMENTS/search"))
            .andExpect(status().isBadRequest)
        verifyNoInteractions(departmentService)
    }

    @Test
    fun `GET - should return 400 on search when first name is empty`() {
        mvc.perform(get("$DEPARTMENTS/search").param("firstName", ""))
            .andExpect(status().isBadRequest)
    }

    companion object {
        private const val DEPARTMENTS = "/departments"
    }
}