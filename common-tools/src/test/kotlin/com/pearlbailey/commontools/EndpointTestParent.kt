package com.pearlbailey.commontools

import com.fasterxml.jackson.databind.ObjectMapper
import com.pearlbailey.commontools.web.ErrorResponseFactory
import org.mockito.Answers.CALLS_REAL_METHODS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@Suppress("SpringJavaAutowiredMembersInspection")
open class EndpointTestParent {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean(answer = CALLS_REAL_METHODS)
    private lateinit var errorResponseFactory: ErrorResponseFactory

    private fun toJson(input: Any?): String = objectMapper.writeValueAsString(input)

    protected fun doGet(url: String) = mvc.perform(MockMvcRequestBuilders.get(url))

    protected fun doPost(path: String, body: Any) =
        mvc.perform(post(path).contentType(APPLICATION_JSON).content(toJson(body)))

    protected fun doPatch(path: String, body: Any? = null) =
        mvc.perform(patch(path).contentType(APPLICATION_JSON).content(toJson(body)))

    protected fun ResultActions.verifyBadRequestOnPost(resourcePath: String, fieldName: String, errorMessage: String) {
        verifyBadRequest(fieldName, errorMessage).verifyMessageField(resourcePath, "POST")
    }

    protected fun ResultActions.verifyBadRequestOnPatch(resourcePath: String, fieldName: String, errorMessage: String) {
        verifyBadRequest(fieldName, errorMessage).verifyMessageField(resourcePath, "PATCH")
    }

    protected fun ResultActions.verifyBadRequestOnGet(resourcePath: String, fieldName: String, errorMessage: String) {
        verifyBadRequest(fieldName, errorMessage).verifyMessageField(resourcePath, "GET")
    }

    private fun ResultActions.verifyMessageField(resourcePath: String, requestMethod: String) =
        andExpect(jsonPath("$.message").value("Failed to invoke $requestMethod $resourcePath"))

    private fun ResultActions.verifyBadRequest(fieldName: String, errorMessage: String) =
        andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.size()").value(1))
            .andExpect(jsonPath("$.errors[0].fieldName").value(fieldName))
            .andExpect(jsonPath("$.errors[0].error").value(errorMessage))
}