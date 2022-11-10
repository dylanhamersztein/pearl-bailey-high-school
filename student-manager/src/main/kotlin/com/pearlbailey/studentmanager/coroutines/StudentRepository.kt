package com.pearlbailey.studentmanager.coroutines

import com.pearlbailey.studentmanager.api.model.Student
import com.pearlbailey.studentmanager.api.model.StudentStatus
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.bind
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
@Profile("coroutines")
class StudentRepository(private val client: DatabaseClient) {

    suspend fun save(student: Student): Student = with(student) {
        client.sql("INSERT INTO students(first_name, middle_name, last_name, date_of_birth, status) VALUES ($1, $2, $3, $4, $5)")
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bindStudentDetails(this)
            .fetch()
            .first()
            .map(studentRowMapper)
            .awaitSingle()
    }

    suspend fun update(student: Student) =
        with(student) {
            client.sql("UPDATE students SET (first_name, middle_name, last_name, date_of_birth, status) = ($1, $2, $3, $4, $5) WHERE id = $6")
                .bind(5, id)
                .bindStudentDetails(this)
                .fetch()
                .first()
                .map(studentRowMapper)
                .awaitSingleOrNull()
        }

    suspend fun findById(id: Int) =
        client.sql("SELECT * from students where id = $1")
            .bind(0, id)
            .fetch()
            .one()
            .map(studentRowMapper)
            .awaitSingleOrNull()

    private suspend fun DatabaseClient.GenericExecuteSpec.bindStudentDetails(student: Student) =
        with(student) {
            bind(0, firstName)
                .bind(1, middleName)
                .bind(2, lastName)
                .bind(3, dateOfBirth)
                .bind(4, status!!.name)
        }

    companion object {
        private val studentRowMapper: (MutableMap<String, Any>) -> Student = { row ->
            Student().apply {
                this.id = row["id"] as Int?
                this.firstName = row["first_name"] as String?
                this.middleName = row["middle_name"] as String?
                this.lastName = row["last_name"] as String?
                this.dateOfBirth = row["date_of_birth"] as LocalDate?
                this.status = (row["status"] as String?)?.let { StudentStatus.valueOf(it) }
            }
        }
    }

}
