package com.pearlbailey.studentmanager.coroutines

import com.pearlbailey.studentmanager.api.StudentMapper.toCreateStudentResponseDto
import com.pearlbailey.studentmanager.api.StudentMapper.toStudent
import com.pearlbailey.studentmanager.api.StudentMapper.toStudentResponseDto
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.StudentNotFoundException
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus.CREATED
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json

@Service
@Profile("coroutines")
class StudentHandler(private val studentRepository: StudentRepository) {

    suspend fun getStudentById(request: ServerRequest) =
        request.pathVariable("id").toInt()
            .let { doGetById(it) ?: throw StudentNotFoundException(it) }
            .toStudentResponseDto()
            .let { ServerResponse.ok().json().bodyValueAndAwait(it) }

    suspend fun createStudent(request: ServerRequest) =
        doCreate(request.awaitBody())
            .toCreateStudentResponseDto()
            .let { ServerResponse.status(CREATED).json().bodyValueAndAwait(it) }

    suspend fun updateStudent(request: ServerRequest) =
        (request.pathVariable("id").toInt() to request.awaitBody<PatchStudentDto>())
            .let { (id, dto) -> doUpdate(id, dto) ?: throw StudentNotFoundException(id) }
            .toStudentResponseDto()
            .let { ServerResponse.ok().json().bodyValueAndAwait(it) }

    private suspend fun doGetById(id: Int) = studentRepository.findById(id)

    private suspend fun doCreate(createStudentDto: CreateStudentDto) =
        studentRepository.save(createStudentDto.toStudent()).id!!

    private suspend fun doUpdate(id: Int, patchStudentDto: PatchStudentDto) =
        doGetById(id)
            ?.apply {
                firstName = patchStudentDto.firstName ?: firstName
                middleName = patchStudentDto.middleName ?: middleName
                lastName = patchStudentDto.lastName ?: lastName
                dateOfBirth = patchStudentDto.dateOfBirth ?: dateOfBirth
                status = patchStudentDto.status ?: status
            }
            ?.let { studentRepository.update(it) }
}
