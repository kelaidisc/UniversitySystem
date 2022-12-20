package com.kelaidisc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.repository.StudentRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Import(FlywayTestConfig.class)
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private StudentRepository studentRepository;

  @Test
  public void givenStudentURI_whenGetAllStudents_thenVerifyResponse() throws Exception {

    final MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders
            .get("/student"))
        .andExpect(status().isOk())
        .andReturn();

    assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
  }

  @Test
  public void givenStudentId_whenGetStudentById_thenVerifyResponse() throws Exception {

    Long studentId = 1L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/student/{id}", studentId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void givenInvalidStudentId_whenGetStudentById_thenVerifyNotFound() throws Exception {

    Long studentId = 1000L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/student/{id}", studentId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenStudentObject_WhenCreateStudent_thenVerifyResponse() throws Exception {

    Student student = Student.builder()
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+306977123434")
        .birthday(LocalDate.of(1967, 7, 9))
        .registrationDate(LocalDate.of(2020, 10, 5))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/student")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(student)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Marina"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gioka"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marinag@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+306977123434"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1967-07-09"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationDate").value("2020-10-05"));

    //TestData has a total of 5 students
    Optional<Student> shouldBeStudent = studentRepository.findById(6L);
    assertNotNull(shouldBeStudent);
  }

  @Test
  public void givenStudentIdAndObject_whenUpdateStudent_thenVerifyResponse() throws Exception {

    Long studentId = 1L;

    Student student = Student.builder()
        .id(studentId)
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+306977123434")
        .birthday(LocalDate.of(1967, 7, 9))
        .registrationDate(LocalDate.of(2020, 10, 5))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/student/{id}", studentId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(student)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Marina"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gioka"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marinag@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+306977123434"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1967-07-09"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationDate").value("2020-10-05"));


    Optional<Student> optionalStudent = studentRepository.findById(1L);

    if (optionalStudent.isPresent()) {
      //always true
      Student updatedStudent = optionalStudent.get();

      assertEquals(updatedStudent.getId(), 1);
      assertEquals(updatedStudent.getFirstName(), "Marina");
      assertEquals(updatedStudent.getLastName(), "Gioka");
      assertEquals(updatedStudent.getEmail(), "marinag@gmail.com");
      assertEquals(updatedStudent.getPhone(), "+306977123434");
      assertEquals(updatedStudent.getBirthday(), LocalDate.of(1967, 7, 9));
      assertEquals(updatedStudent.getRegistrationDate(), LocalDate.of(2020, 10, 5));
    }
  }

  @Test
  public void givenMismatchStudentIdAndObject_whenUpdateStudent_thenVerifyBadRequest() throws Exception {

    Long studentId = 1L;

    Student student = Student.builder()
        .id(7L)
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+30 6977123434")
        .birthday(LocalDate.of(1967, 7, 31))
        .registrationDate(LocalDate.of(2020, 10, 5))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/student/{id}", studentId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(student)));

    response
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenStudentIds_whenDeleteStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/student")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());

    //TestData has a total of 5 students

    int allCourses = studentRepository.findAll().size();
    assertEquals(3, allCourses);
  }

  @Test
  public void givenStudentIdsNotExisting_whenDeleteStudents_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(7L);
    ids.add(8L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/student")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());
  }

  @Test
  public void givenStudentId_whenGetEnrolledCourses_thenVerifyResponse() throws Exception {

    Long studentId = 1L;

    mockMvc.perform(MockMvcRequestBuilders
            .get("/student/{id}/courses", studentId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  public void givenInvalidStudentId_whenGetEnrolledCourses_thenVerifyNotFound() throws Exception {

    Long studentId = 7L;

    mockMvc.perform(MockMvcRequestBuilders
            .get("/student/{id}/courses", studentId))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }
}