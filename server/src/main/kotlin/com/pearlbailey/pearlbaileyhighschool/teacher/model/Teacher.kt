package com.pearlbailey.pearlbaileyhighschool.teacher.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "teachers")
class Teacher {

    var firstName: String? = null

    @Column(nullable = true)
    var middleName: String? = null


    var lastName: String? = null

    @Column(nullable = false)
    var dateOfBirth: LocalDate? = null

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null

    fun toTeacherResponseDto() = TeacherResponseDto(firstName!!, middleName, lastName!!, dateOfBirth!!)

}
