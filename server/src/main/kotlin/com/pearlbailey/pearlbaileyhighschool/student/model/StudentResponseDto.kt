package com.pearlbailey.pearlbaileyhighschool.student.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class StudentResponseDto(

    @Schema(description = "The student's first name.", example = "Steven")
    val firstName: String,

    @Schema(description = "The student's middle name.", example = "Anita")
    val middleName: String?,

    @Schema(description = "The student's last name.", example = "Smith")
    val lastName: String,

    @Schema(description = "The student's date of birth.", example = "1994/10/19", pattern = "yyyy/MM/dd")
    val dateOfBirth: LocalDate,

    @Schema(description = "What status the student is in.", example = "PROSPECT")
    val status: StudentStatus
)
