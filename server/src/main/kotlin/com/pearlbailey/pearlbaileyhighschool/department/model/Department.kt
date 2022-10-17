package com.pearlbailey.pearlbaileyhighschool.department.model

import com.pearlbailey.pearlbaileyhighschool.teacher.model.Teacher
import javax.persistence.CascadeType.MERGE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "departments")
class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @OneToOne(fetch = LAZY, cascade = [MERGE])
    @JoinColumn(name = "head_of_department_id", referencedColumnName = "id")
    var headOfDepartment: Teacher? = null

}
