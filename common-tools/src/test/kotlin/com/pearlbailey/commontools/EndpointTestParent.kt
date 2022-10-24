package com.pearlbailey.commontools

import com.fasterxml.jackson.databind.ObjectMapper
import com.pearlbailey.commontools.web.ErrorResponseFactory
import org.mockito.Answers.CALLS_REAL_METHODS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@Suppress("SpringJavaAutowiredMembersInspection")
open class EndpointTestParent {

    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean(answer = CALLS_REAL_METHODS)
    protected lateinit var errorResponseFactory: ErrorResponseFactory

    protected fun toJson(input: Any): String = objectMapper.writeValueAsString(input)
}