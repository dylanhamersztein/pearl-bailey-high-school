package com.pearlbailey.studentmanager.jpa

import com.pearlbailey.studentmanager.api.StudentMapper.toStudent
import com.pearlbailey.studentmanager.api.model.CreateStudentDto
import com.pearlbailey.studentmanager.api.model.PatchStudentDto
import com.pearlbailey.studentmanager.api.model.Student
import com.pearlbailey.studentmanager.api.service.StudentService
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Profile("jpa")
class StudentService(private val studentRepository: StudentRepository) :
    StudentService {

    override fun createStudent(createStudentDto: CreateStudentDto): Int =
        studentRepository.save(createStudentDto.toStudent()).id!!

    override fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): Student? = getStudentById(id)
        ?.let {
            it.firstName = patchStudentDto.firstName ?: it.firstName
            it.middleName = patchStudentDto.middleName ?: it.middleName
            it.lastName = patchStudentDto.lastName ?: it.lastName
            it.dateOfBirth = patchStudentDto.dateOfBirth ?: it.dateOfBirth
            it.status = patchStudentDto.status ?: it.status

            it
        }
        ?.let { studentRepository.save(it) }

    override fun getStudentById(id: Int): Student? = studentRepository.findByIdOrNull(id)

    override fun getAllStudents(): List<Student> = studentRepository.findAll().toList()

}
