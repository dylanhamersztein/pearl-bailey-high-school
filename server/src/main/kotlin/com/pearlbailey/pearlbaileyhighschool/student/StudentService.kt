package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import com.pearlbailey.pearlbaileyhighschool.student.model.toStudent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

sealed interface StudentService {
    fun createStudent(createStudentDto: CreateStudentDto): Int
    fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): Student?
    fun getStudentById(id: Int): Student?
    fun searchStudentByName(firstName: String?, lastName: String?): Student?
}

@Service
class DefaultStudentService(private val studentRepository: StudentRepository) : StudentService {

    override fun createStudent(createStudentDto: CreateStudentDto): Int =
        studentRepository.save(createStudentDto.toStudent()).id!!

    override fun updateStudent(id: Int, patchStudentDto: PatchStudentDto): Student? = getStudentById(id)
        ?.let {
            Student(
                id = it.id,
                firstName = patchStudentDto.firstName ?: it.firstName,
                middleName = patchStudentDto.middleName ?: it.middleName,
                lastName = patchStudentDto.lastName ?: it.lastName,
                dateOfBirth = patchStudentDto.dateOfBirth ?: it.dateOfBirth,
                status = patchStudentDto.status ?: it.status
            )
        }
        ?.let { studentRepository.save(it) }

    override fun getStudentById(id: Int): Student? = studentRepository.findByIdOrNull(id)

    override fun searchStudentByName(firstName: String?, lastName: String?): Student? = when {
        firstName != null && lastName != null -> studentRepository.findByFirstNameAndLastName(firstName, lastName)
        firstName == null -> studentRepository.findByLastName(lastName!!)
        else -> studentRepository.findByFirstName(firstName)
    }
}