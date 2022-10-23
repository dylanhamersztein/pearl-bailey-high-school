package com.pearlbailey.departmentmanager

import com.pearlbailey.departmentmanager.api.DepartmentMapper.toDepartment
import com.pearlbailey.departmentmanager.api.model.CreateDepartmentDto
import com.pearlbailey.departmentmanager.api.model.PatchDepartmentDto
import com.pearlbailey.departmentmanager.api.service.DepartmentService
import com.pearlbailey.teachermanager.api.TeacherWebService
import com.pearlbailey.teachermanager.api.model.web.TeacherNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultDepartmentService(
    private val departmentRepository: DepartmentRepository,
    private val teacherWebService: TeacherWebService
) : DepartmentService {

    override fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int {
        val teacher = teacherWebService.getTeacherById(createDepartmentDto.headOfDepartmentId!!)
            ?: throw TeacherNotFoundException(createDepartmentDto.headOfDepartmentId)

        val department = createDepartmentDto.toDepartment(teacher)

        return departmentRepository.save(department).id!!
    }

    override fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto) = getDepartmentById(id)
        ?.let {
            val headOfDepartment = patchDepartmentDto.headOfDepartmentId
                ?.let { newId -> teacherWebService.getTeacherById(newId)?.id ?: throw TeacherNotFoundException(newId) }
                ?: it.headOfDepartmentId!!

            it.name = patchDepartmentDto.name ?: it.name
            it.headOfDepartmentId = headOfDepartment

            it
        }
        ?.let { departmentRepository.save(it) }

    override fun getDepartmentById(id: Int) = departmentRepository.findByIdOrNull(id)

    override fun searchDepartmentByName(name: String) = departmentRepository.findByName(name)
}
