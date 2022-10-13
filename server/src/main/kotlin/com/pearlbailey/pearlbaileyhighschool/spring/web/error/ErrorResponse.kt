package com.pearlbailey.pearlbaileyhighschool.spring.web.error

class ErrorResponse(val status: Int, val message: String, val errors: List<Error>? = null)

class Error(
    val fieldName: String? = null,
    val fieldValue: Any? = null,
    val error: String? = null
)