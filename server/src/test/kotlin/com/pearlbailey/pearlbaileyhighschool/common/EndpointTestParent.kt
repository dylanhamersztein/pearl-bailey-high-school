package com.pearlbailey.pearlbaileyhighschool.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.pearlbailey.pearlbaileyhighschool.spring.web.error.ErrorResponseFactory
import org.mockito.Answers.CALLS_REAL_METHODS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@Suppress("SpringJavaAutowiredMembersInspection")
internal open class EndpointTestParent {

    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @MockBean(answer = CALLS_REAL_METHODS)
    protected lateinit var errorResponseFactory: ErrorResponseFactory
}