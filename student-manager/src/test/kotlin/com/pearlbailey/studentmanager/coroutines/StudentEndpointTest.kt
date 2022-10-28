package com.pearlbailey.studentmanager.coroutines

import com.pearlbailey.studentmanager.api.StudentConstants.STUDENTS_RESOURCE_PATH
import com.pearlbailey.studentmanager.api.StudentFactory
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@Disabled("Need to get embedded postgres working first.")
@TestMethodOrder(OrderAnnotation::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
internal class StudentEndpointTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    fun `POST - should create student`() {
        val createStudentDto = StudentFactory.getCreateStudentDto()

        webTestClient.post()
            .uri { it.path(STUDENTS_RESOURCE_PATH).build() }
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .bodyValue(createStudentDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
    }

    @Test
    @Order(2)
    fun `GET - should retrieve student`() {
        webTestClient.get()
            .uri { it.path("$STUDENTS_RESOURCE_PATH/1").build() }
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
    }

    @Test
    @Order(3)
    fun `PATCH - should return 404 when student to update not found`() {
        val patchStudentDto = StudentFactory.getPatchStudentDto()

        webTestClient.patch()
            .uri { it.path("$STUDENTS_RESOURCE_PATH/{id}").build(1) }
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .bodyValue(patchStudentDto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.message").isEqualTo("Student with id 1 not found.")
    }

}
