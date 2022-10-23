package com.pearlbailey.studentmanager.api.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "students")
class Student {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null

    @Column(name = "middle_name", nullable = true)
    var middleName: String? = null

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null

    @Column(name = "date_of_birth", nullable = false)
    var dateOfBirth: LocalDate? = null

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    var status: StudentStatus? = null

}
