package com.pearlbailey.commontools

import com.fasterxml.jackson.databind.ObjectMapper
import com.pearlbailey.commontools.web.ErrorResponseFactory
import org.mockito.Answers.CALLS_REAL_METHODS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@Suppress("SpringJavaAutowiredMembersInspection")
open class EndpointTestParent {

    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean(answer = CALLS_REAL_METHODS)
    protected lateinit var errorResponseFactory: ErrorResponseFactory

    protected fun toJson(input: Any): String = objectMapper.writeValueAsString(input)

    protected fun doPost(path: String, body: Any) =
        mvc.perform(post(path).contentType(APPLICATION_JSON).content(toJson(body)))

    protected fun doPatch(path: String, body: Any) =
        mvc.perform(patch(path).contentType(APPLICATION_JSON).content(toJson(body)))

    protected fun ResultActions.verifyBadRequestOnPost(resourcePath: String, fieldName: String, errorMessage: String) {
        andExpect(jsonPath("$.message").value("Failed to invoke POST $resourcePath"))
            .verifyBadRequest(fieldName, errorMessage)
    }

    protected fun ResultActions.verifyBadRequestOnPatch(resourcePath: String, fieldName: String, errorMessage: String) {
        andExpect(jsonPath("$.message").value("Failed to invoke PATCH $resourcePath"))
            .verifyBadRequest(fieldName, errorMessage)
    }

    protected fun ResultActions.verifyBadRequestOnGet(resourcePath: String, fieldName: String, errorMessage: String) {
        andExpect(jsonPath("$.message").value("Failed to invoke GET $resourcePath"))
            .verifyBadRequest(fieldName, errorMessage)
    }

    private fun ResultActions.verifyBadRequest(fieldName: String, errorMessage: String) {
        andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value(fieldName))
            .andExpect(jsonPath("$.errors[0].error").value(errorMessage))
    }
}