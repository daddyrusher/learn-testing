package com.daddyrusher.learntesting.service

import com.daddyrusher.learntesting.model.Course
import com.daddyrusher.learntesting.model.Student
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

@Service
class StudentService {
    private val students: MutableList<Student> = mutableListOf()

    init {
        val course1 = Course("Course1", "Spring", "10 Steps", listOf("Learn Maven", "Import Project", "First Example", "Second Example"))
        val course2 = Course("Course2", "Spring MVC", "10 Examples", listOf("Learn Maven", "Import Project", "First Example", "Second Example"))
        val course3 = Course("Course3", "Spring Boot", "6K Students", listOf("Learn Maven", "Learn Spring", "Learn Spring MVC", "First Example", "Second Example"))
        val course4 = Course("Course4", "Maven", "Most popular maven course on internet!", listOf("Pom.xml", "Build Life Cycle", "Parent POM", "Importing into Eclipse"))

        val ranga = Student("Student1", "Ranga Karanam",
                "Hiker, Programmer and Architect", ArrayList(listOf(course1, course2, course3, course4)))

        val satish = Student("Student2", "Satish T",
                "Hiker, Programmer and Architect", ArrayList(listOf(course1, course2, course3, course4)))

        students.add(ranga)
        students.add(satish)
    }

    fun retrieveAllStudents(): List<Student> = students

    fun retrieveStudent(studentId: String?): Student? = students.find { it.id == studentId }

    fun retrieveCourses(studentId: String): List<Course> = retrieveStudent(studentId)?.courses ?: listOf()

    fun retrieveCourse(studentId: String, courseId: String): Course? {
        val student = retrieveStudent(studentId) ?: return null
        return student.courses?.find { it.id == courseId }
    }

    private val random: SecureRandom = SecureRandom()

    fun addCourse(studentId: String?, course: Course?): Course? {
        val student = retrieveStudent(studentId) ?: return null
        val randomId: String = BigInteger(130, random).toString(32)
        course?.id = randomId
        course?.let { student.courses?.add(it) }
        return course
    }
}