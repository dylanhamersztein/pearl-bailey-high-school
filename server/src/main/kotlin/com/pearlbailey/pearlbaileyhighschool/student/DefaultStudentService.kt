package com.pearlbailey.pearlbaileyhighschool.student

import com.pearlbailey.pearlbaileyhighschool.student.model.CreateStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.PatchStudentDto
import com.pearlbailey.pearlbaileyhighschool.student.model.Student
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultStudentService(private val studentRepository: StudentRepository) : StudentService {

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

    override fun searchStudentByName(firstName: String?, lastName: String?): Student? = when {
        firstName != null && lastName != null -> studentRepository.findByFirstNameAndLastName(firstName, lastName)
        firstName == null -> studentRepository.findByLastName(lastName!!)
        else -> studentRepository.findByFirstName(firstName)
    }
}
