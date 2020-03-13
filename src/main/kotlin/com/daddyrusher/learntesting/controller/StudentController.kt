package com.daddyrusher.learntesting.controller

import com.daddyrusher.learntesting.model.Course
import com.daddyrusher.learntesting.service.StudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
class StudentController(private val service: StudentService) {
    @GetMapping("/students/{studentId}/courses")
    fun retrieveCoursesForStudent(@PathVariable studentId: String): List<Course> {
        return service.retrieveCourses(studentId)
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    fun retrieveDetailsForCourse(@PathVariable studentId: String, @PathVariable courseId: String): Course? {
        return service.retrieveCourse(studentId, courseId)
    }

    @PostMapping("/students/{studentId}/courses")
    fun registerStudentForCourse(@PathVariable studentId: String?, @RequestBody newCourse: Course?): ResponseEntity<Void> {
        val course = service.addCourse(studentId, newCourse) ?: return ResponseEntity.noContent().build()
        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.id).toUri()
        return ResponseEntity.created(location).build()
    }
}