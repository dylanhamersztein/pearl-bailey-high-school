package com.pearlbailey.pearlbaileyhighschool.student.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class CreateStudentDto(

    @field:NotBlank
    @Schema(description = "The student's first name.", example = "Steven")
    val firstName: String?,

    @Schema(description = "The student's middle name.", example = "Anita")
    val middleName: String?,

    @field:NotBlank
    @Schema(description = "The student's last name.", example = "Smith")
    val lastName: String?,

    @field:Past
    @field:NotNull
    @field:JsonDeserialize(using = LocalDateDeserializer::class)
    @Schema(description = "The student's date of birth.", example = "1994/10/19", pattern = "yyyy/MM/dd")
    val dateOfBirth: LocalDate?,

    @field:NotNull
    @Schema(description = "What status the student is in.", example = "PROSPECT")
    val status: StudentStatus?
)
