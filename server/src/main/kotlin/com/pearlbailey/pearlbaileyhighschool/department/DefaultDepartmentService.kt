package com.pearlbailey.pearlbaileyhighschool.department

import com.pearlbailey.pearlbaileyhighschool.department.model.CreateDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.department.model.PatchDepartmentDto
import com.pearlbailey.pearlbaileyhighschool.teacher.TeacherService
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultDepartmentService(
    private val departmentRepository: DepartmentRepository,
    private val teacherService: TeacherService
) : DepartmentService {

    override fun createDepartment(createDepartmentDto: CreateDepartmentDto): Int {
        val teacher = teacherService.getTeacherById(createDepartmentDto.headOfDepartmentId)
            ?: throw TeacherNotFoundException(createDepartmentDto.headOfDepartmentId)

        val department = createDepartmentDto.toDepartment(teacher)

        return departmentRepository.save(department).id!!
    }

    override fun updateDepartment(id: Int, patchDepartmentDto: PatchDepartmentDto) = getDepartmentById(id)
        ?.let {
            val headOfDepartment = patchDepartmentDto.headOfDepartmentId
                ?.let { newId -> teacherService.getTeacherById(newId) } ?: it.headOfDepartment!!

            it.name = patchDepartmentDto.name ?: it.name
            it.headOfDepartment = headOfDepartment

            it
        }
        ?.let { departmentRepository.save(it) }

    override fun getDepartmentById(id: Int) = departmentRepository.findByIdOrNull(id)

    override fun searchDepartmentByName(name: String) = departmentRepository.findByName(name)
}
