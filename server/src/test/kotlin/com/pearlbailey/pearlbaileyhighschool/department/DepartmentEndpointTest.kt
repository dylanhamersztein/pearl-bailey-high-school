package com.pearlbailey.pearlbaileyhighschool.department

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.pearlbailey.pearlbaileyhighschool.department.util.DepartmentFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DepartmentEndpoint::class)
internal class DepartmentEndpointTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var departmentService: DepartmentService

    @Test
    fun `should return 400 when name is blank on create`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(name = "")
        mvc.perform(post(DEPARTMENTS, objectMapper.writeValueAsString(createDepartmentDto)))
            .andExpect(status().isBadRequest)

        verifyNoInteractions(departmentService)
    }

    @Test
    fun `should return 400 when headOfDepartmentId is null on create`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto()
        val jsonBody = (objectMapper.readTree(objectMapper.writeValueAsString(createDepartmentDto)) as ObjectNode)
            .remove("headOfDepartmentId")

        mvc.perform(post(DEPARTMENTS, jsonBody)).andExpect(status().isBadRequest)
        verifyNoInteractions(departmentService)
    }

    @Test
    fun `should return 400 when headOfDepartmentId is negative on create`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto(headOfDepartmentId = -1)

        mvc.perform(post(DEPARTMENTS, objectMapper.writeValueAsString(createDepartmentDto)))
            .andExpect(status().isBadRequest)
        verifyNoInteractions(departmentService)
    }

    @Test
    fun `should return 200 when department information is valid`() {
        val createDepartmentDto = DepartmentFactory.getCreateDepartmentDto()

        whenever(departmentService.createDepartment(createDepartmentDto)).thenReturn(1)

        mvc.perform(
            post(DEPARTMENTS).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDepartmentDto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))

        verify(departmentService).createDepartment(createDepartmentDto)
    }


    @Test
    fun `should return 400 on search when first name is null`() {
        mvc.perform(get("$DEPARTMENTS/search"))
            .andExpect(status().isBadRequest)
        verifyNoInteractions(departmentService)
    }

    @Test
    fun `should return 400 on search when first name is empty`() {
        mvc.perform(get("$DEPARTMENTS/search").param("firstName", ""))
            .andExpect(status().isBadRequest)
    }

    companion object {
        private const val DEPARTMENTS = "/departments"
    }
}