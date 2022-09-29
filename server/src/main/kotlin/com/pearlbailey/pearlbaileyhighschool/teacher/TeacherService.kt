package com.pearlbailey.pearlbaileyhighschool.teacher

import com.pearlbailey.pearlbaileyhighschool.teacher.model.CreateTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.PatchTeacherDto
import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import com.pearlbailey.pearlbaileyhighschool.teacher.model.toTeacher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

sealed interface TeacherService {
    fun createTeacher(createTeacherDto: CreateTeacherDto): Int
    fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): Teacher
    fun getTeacherById(id: Int): Teacher?
    fun searchTeacherByName(firstName: String?, lastName: String?): Teacher?
}

@Service
class DefaultTeacherService(private val teacherRepository: TeacherRepository) : TeacherService {

    override fun createTeacher(createTeacherDto: CreateTeacherDto): Int =
        teacherRepository.save(createTeacherDto.toTeacher()).id!!

    override fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto): Teacher = getTeacherById(id)
        ?.let {
            it.firstName = patchTeacherDto.firstName ?: it.firstName
            it.middleName = patchTeacherDto.middleName ?: it.middleName
            it.lastName = patchTeacherDto.lastName ?: it.lastName
            it.dateOfBirth = patchTeacherDto.dateOfBirth ?: it.dateOfBirth

            it
        }
        ?.let { teacherRepository.save(it) } ?: throw TeacherNotFoundException(id)

    override fun getTeacherById(id: Int): Teacher? = teacherRepository.findByIdOrNull(id)

    override fun searchTeacherByName(firstName: String?, lastName: String?): Teacher? = when {
        firstName != null && lastName != null -> teacherRepository.findByFirstNameAndLastName(firstName, lastName)
        firstName == null -> teacherRepository.findByLastName(lastName!!)
        else -> teacherRepository.findByFirstName(firstName)
    }
}
