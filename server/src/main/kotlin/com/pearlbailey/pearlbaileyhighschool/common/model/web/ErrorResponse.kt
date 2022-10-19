package com.pearlbailey.pearlbaileyhighschool.common.model.web

class ErrorResponse(val status: Int, val message: String, val errors: List<Error>? = null)

class Error(
    val fieldName: String? = null,
    val error: String? = null
)
