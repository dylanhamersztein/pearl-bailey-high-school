package com.pearlbailey.teachermanager

import com.pearlbailey.teachermanager.api.TeacherMapper.toTeacher
import com.pearlbailey.teachermanager.api.model.web.CreateTeacherDto
import com.pearlbailey.teachermanager.api.model.web.PatchTeacherDto
import com.pearlbailey.teachermanager.api.service.TeacherService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultTeacherService(private val teacherRepository: TeacherRepository) : TeacherService {

    override fun createTeacher(createTeacherDto: CreateTeacherDto) =
        teacherRepository.save(createTeacherDto.toTeacher()).id!!

    override fun updateTeacher(id: Int, patchTeacherDto: PatchTeacherDto) = getTeacherById(id)
        ?.let {
            it.firstName = patchTeacherDto.firstName ?: it.firstName
            it.middleName = patchTeacherDto.middleName ?: it.middleName
            it.lastName = patchTeacherDto.lastName ?: it.lastName
            it.dateOfBirth = patchTeacherDto.dateOfBirth ?: it.dateOfBirth

            it
        }
        ?.let { teacherRepository.save(it) }

    override fun getTeacherById(id: Int) = teacherRepository.findByIdOrNull(id)
}
