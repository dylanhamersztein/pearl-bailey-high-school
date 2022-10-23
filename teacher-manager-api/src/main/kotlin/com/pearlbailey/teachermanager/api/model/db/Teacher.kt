package com.pearlbailey.teachermanager.api.model.db

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

}
