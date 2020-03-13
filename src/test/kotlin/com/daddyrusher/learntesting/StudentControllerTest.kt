package com.daddyrusher.learntesting

import com.daddyrusher.learntesting.model.Course
import com.daddyrusher.learntesting.service.StudentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentControllerTest {
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var studentService: StudentService

    @Autowired
    lateinit var wac: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    val mockCourse: Course = Course(
            "Course1",
            "Spring",
            "10 Steps",
            listOf("Learn Maven", "Import Project", "First Example", "Second Example"))
    var exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}"


    @Test
    fun `Retrieve Details For Course`() {
        `when`(studentService.retrieveCourse(Mockito.anyString(), Mockito.anyString())).thenReturn(mockCourse)

        val requestBuilder: RequestBuilder = MockMvcRequestBuilders.get("/students/Student1/courses/Course1").accept(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(requestBuilder).andReturn()
        val expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}"
        val squaresTestData = listOf(
                1 to 1,
                2 to 4,
                3 to 9,
                4 to 16,
                5 to 25)
        println(squaresTestData)

        println(result.response.contentAsString)

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}
        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    @Test
    fun `Create Student Course`() {
        val mockCourse = Course("1", "Smallest Number", "1", listOf("1", "2", "3", "4"))

        `when`(studentService.addCourse(Mockito.anyString(), Mockito.any(Course::class.java))).thenReturn(mockCourse)

        // Send course as body to /students/Student1/courses
        val requestBuilder: RequestBuilder = MockMvcRequestBuilders
                .post("/students/Student1/courses")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(requestBuilder).andReturn()
        val response = result.response
        assertEquals(HttpStatus.CREATED.value(), response.status)
        assertEquals("http://localhost/students/Student1/courses/1", response.getHeader(HttpHeaders.LOCATION))
    }
}