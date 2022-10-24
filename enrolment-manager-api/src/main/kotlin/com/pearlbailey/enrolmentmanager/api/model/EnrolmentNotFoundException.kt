package com.pearlbailey.enrolmentmanager.api.model

import com.pearlbailey.commontools.exception.NotFoundException

class EnrolmentNotFoundException(enrolmentId: Int? = null, message: String? = null) :
    NotFoundException(message ?: "Enrolment with id $enrolmentId not found.")
