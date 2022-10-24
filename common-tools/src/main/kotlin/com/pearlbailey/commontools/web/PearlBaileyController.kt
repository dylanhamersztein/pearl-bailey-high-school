package com.pearlbailey.commontools.web

import org.springframework.context.annotation.Import
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated

@Validated
@Controller
@Import(BaseErrorHandler::class)
annotation class PearlBaileyController
