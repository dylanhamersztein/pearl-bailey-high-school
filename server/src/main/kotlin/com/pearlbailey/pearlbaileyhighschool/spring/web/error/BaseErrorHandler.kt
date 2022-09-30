package com.pearlbailey.pearlbaileyhighschool.spring.web.error

import com.pearlbailey.pearlbaileyhighschool.courses.model.CourseNotFoundException
import com.pearlbailey.pearlbaileyhighschool.department.model.DepartmentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.student.model.StudentNotFoundException
import com.pearlbailey.pearlbaileyhighschool.teacher.model.TeacherNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException


@ControllerAdvice
class BaseErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationException(
        constraintViolationException: ConstraintViolationException,
        req: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            constraintViolationException,
            constraintViolationException.message,
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            req
        )
    }

    @ExceptionHandler(TeacherNotFoundException::class)
    fun teacherNotFoundException(
        teacherNotFoundException: TeacherNotFoundException,
        req: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            teacherNotFoundException,
            teacherNotFoundException.message,
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            req
        )
    }

    @ExceptionHandler(CourseNotFoundException::class)
    fun courseNotFoundException(
        courseNotFoundException: CourseNotFoundException,
        req: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            courseNotFoundException,
            courseNotFoundException.message,
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            req
        )
    }

    @ExceptionHandler(DepartmentNotFoundException::class)
    fun departmentNotFoundException(
        departmentNotFoundException: DepartmentNotFoundException,
        req: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            departmentNotFoundException,
            departmentNotFoundException.message,
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            req
        )
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun studentNotFoundException(
        studentNotFoundException: StudentNotFoundException,
        req: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            studentNotFoundException,
            studentNotFoundException.message,
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            req
        )
    }
}
