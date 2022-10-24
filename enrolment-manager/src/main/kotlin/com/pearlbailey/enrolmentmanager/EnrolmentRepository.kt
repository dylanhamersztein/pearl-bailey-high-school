package com.pearlbailey.enrolmentmanager

import com.pearlbailey.enrolmentmanager.api.model.Enrolment
import org.springframework.data.repository.CrudRepository

interface EnrolmentRepository : CrudRepository<Enrolment, Int> {
    fun findAllByStudentId(id: Int): List<Enrolment>
    fun findAllByCourseId(id: Int): List<Enrolment>
}