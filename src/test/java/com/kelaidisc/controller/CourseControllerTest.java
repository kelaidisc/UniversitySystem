package com.kelaidisc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.EnrollDto;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.dto.course.CourseUpdateDto;
import com.kelaidisc.repository.CourseRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Import(FlywayTestConfig.class)
@AutoConfigureMockMvc
class CourseControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CourseRepository courseRepository;


  @Test
  public void givenCourseURI_whenGetAllCourses_thenVerifyResponse() throws Exception {

    final MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders
            .get("/course"))
        .andExpect(status().isOk())
        .andReturn();

    assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
  }

  @Test
  public void givenCourseEmptyName_whenGetAllCourses_thenBadRequest() throws Exception {

    String emptyName = "   ";

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/course").param("name", emptyName))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenCourseId_whenGetCourseById_thenVerifyResponse() throws Exception {

    Long courseId = 1L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/course/{id}", courseId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void givenInvalidCourseId_whenGetCourseById_thenVerifyNotFound() throws Exception {

    Long courseId = 10000000L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/course/{id}", courseId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseObject_whenCreateCourse_thenVerifyResponse() throws Exception {

    CourseCreateDto course = CourseCreateDto.builder()
        .name("Gym")
        .description("descr")
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/course")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(course)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("name").value("Gym"))
        .andExpect(MockMvcResultMatchers.jsonPath("description").value("descr"));

    //TestData has a total of 5 courses
    Optional<Course> shouldBeCourse = courseRepository.findById(6L);
    assertNotNull(shouldBeCourse);
  }


  @Test
  public void givenCourseIdAndObject_whenUpdateCourse_thenVerifyResponse() throws Exception {

    Long courseId = 1L;

    CourseUpdateDto course = CourseUpdateDto.builder()
        .id(courseId)
        .name("Logic")
        .description("DescriptionText1234")
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/course/{id}", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(course)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Logic"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("DescriptionText1234"));

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course updatedCourse = optionalCourse.get();

      assertEquals(updatedCourse.getId(), 1);
      assertEquals(updatedCourse.getName(), "Logic");
      assertEquals(updatedCourse.getDescription(), "DescriptionText1234");
    }
  }

  @Test
  public void givenMismatchCourseIdAndObject_whenUpdateCourse_thenVerifyBadRequest() throws Exception {

    CourseUpdateDto course = CourseUpdateDto.builder()
        .id(7L)
        .name("Logic")
        .description("DescriptionText1234")
        .build();

    Long courseId = 1L;

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/course/{id}", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(course)));

    response
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.businessCode").value("10001"));
  }

  @Test
  public void givenCourseIds_whenDeleteCourses_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/course")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());

    //TestData has a total of 5 courses

    int allCourses = courseRepository.findAll().size();
    assertEquals(3, allCourses);
  }

  @Test
  public void givenCourseIdsNotExisting_whenDeleteCourses_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(7L);
    ids.add(8L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/course")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto))
        );

    response
        .andExpect(status().isOk());
  }

  @Test
  public void givenCourseIdAndProfessorId_whenAssignProfessor_thenVerifyResponse() throws Exception {

    Long courseId = 1L;
    Long professorId = 1L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .patch("/course/{id}/professor/{professorId}", courseId, professorId))
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();

      assertEquals(withAssignedProfessor.getProfessorId(), 1);
    }
  }

  @Test
  public void givenInvalidCourseIdAndProfessorId_whenAssignProfessor_thenVerifyNotFound() throws Exception {

    Long courseId = 100000L;
    Long professorId = 1L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .patch("/course/{id}/professor/{professorId}", courseId, professorId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseIdAndInvalidProfessorId_whenAssignProfessor_thenVerifyNotFound() throws Exception {

    Long courseId = 1L;
    Long professorId = 100000L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .patch("/course/{id}/professor/{professorId}", courseId, professorId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseId_whenRemovingProfessor_thenVerifyResponse() throws Exception {

    Long courseId = 1L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .patch("/course/{id}/professor", courseId))
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();
      assertNull(withAssignedProfessor.getProfessorId());
    }
  }

  @Test
  public void givenInvalidCourseId_whenRemovingProfessor_thenVerifyNotFound() throws Exception {

    Long courseId = 100000L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .patch("/course/{id}/professor", courseId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseId_whenGetEnrolledStudents_thenVerifyResponse() throws Exception {

    Long courseId = 1L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/course/{id}/students", courseId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  public void givenInvalidCourseId_whenGetEnrolledStudents_thenVerifyNotFound() throws Exception {

    Long courseId = 8L;

    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/course/{id}/students", courseId))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  public void givenCourseIdAndStudentsIds_whenEnrollStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(4L);
    ids.add(5L);

    EnrollDto enrollDto = new EnrollDto(ids);

    Long courseId = 1L;

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(enrollDto)));

    response
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();
      Set<Student> courseStudents = withAssignedProfessor.getStudents();

      //TestData enrolls students with ids: 1, 2, 3 to course with id 1

      assertEquals(courseStudents.size(), 5);
    }
  }

  @Test
  public void givenInvalidCourseIdAndStudentsIds_whenEnrollStudents_thenVerifyNotFound() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(4L);
    ids.add(5L);

    EnrollDto enrollDto = new EnrollDto(ids);

    Long courseId = 111111L;

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(enrollDto)));

    response
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseIdAndStudentsIdsNotExisting_whenEnrollStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(43333L);
    ids.add(53333L);

    EnrollDto enrollDto = new EnrollDto(ids);

    Long courseId = 1L;

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(enrollDto)));

    response
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();
      Set<Student> courseStudents = withAssignedProfessor.getStudents();

      //TestData enrolls students with ids: 1, 2, 3 to course with id 1

      assertEquals(courseStudents.size(), 3);
    }
  }

  @Test
  void givenCourseIdAndStudentsIds_whenDisEnrollStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    Long courseId = 1L;

    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();
      Set<Student> courseStudents = withAssignedProfessor.getStudents();

      //TestData enrolls students with ids: 1, 2, 3 to course with id 1

      assertEquals(courseStudents.size(), 1);
    }
  }

  @Test
  void givenInvalidCourseIdAndStudentsIds_whenDisEnrollStudents_thenVerifyNotFound() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);

    DeleteDto deleteDto = new DeleteDto(ids);

    Long courseId = 100000L;
    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isNotFound());
  }

  @Test
  void givenCourseIdAndStudentsIdsNotExisting_whenDisEnrollStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1000000L);
    ids.add(2000000L);

    Long courseId = 1L;

    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/course/{id}/students", courseId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());

    Optional<Course> optionalCourse = courseRepository.findById(1L);

    if (optionalCourse.isPresent()) {
      //always true
      Course withAssignedProfessor = optionalCourse.get();
      Set<Student> courseStudents = withAssignedProfessor.getStudents();

      //TestData enrolls students with ids: 1, 2, 3 to course with id 1

      assertEquals(courseStudents.size(), 3);
    }
  }
}