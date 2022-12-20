package com.kelaidisc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.repository.ProfessorRepository;
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
class ProfessorControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProfessorRepository professorRepository;

  @Test
  public void givenProfessorURI_whenGetAllProfessors_thenVerifyResponse() throws Exception {

    final MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders
            .get("/professor"))
        .andExpect(status().isOk())
        .andReturn();

    assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
  }

  @Test
  public void givenProfessorId_whenGetProfessorById_thenVerifyResponse() throws Exception {

    Long professorId = 1L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/professor/{id}", professorId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void givenInvalidProfessorId_whenGetProfessorById_thenVerifyNotFound() throws Exception {

    Long professorId = 1000L;

    this.mockMvc.perform(MockMvcRequestBuilders
            .get("/professor/{id}", professorId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenProfessorObject_WhenCreateProfessor_thenVerifyResponse() throws Exception {

    Professor professor = Professor.builder()
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+306977123434")
        .birthday(LocalDate.of(1967, 7, 9))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/professor")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(professor)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Marina"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gioka"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marinag@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+306977123434"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1967-07-09"));

    //TestData has a total of 5 professors
    Optional<Professor> shouldBeProfessor = professorRepository.findById(6L);
    assertNotNull(shouldBeProfessor);
  }

  @Test
  public void givenProfessorIdAndObject_whenUpdateProfessor_thenVerifyResponse() throws Exception {

    Long professorId = 1L;

    Professor professor = Professor.builder()
        .id(professorId)
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+306977123434")
        .birthday(LocalDate.of(1967, 7, 9))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/professor/{id}", professorId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(professor)));

    response
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Marina"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Gioka"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marinag@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+306977123434"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1967-07-09"));


    Optional<Professor> optionalProfessor = professorRepository.findById(1L);

    if (optionalProfessor.isPresent()) {
      //always true
      Professor updatedProfessor = optionalProfessor.get();

      assertEquals(updatedProfessor.getId(), 1);
      assertEquals(updatedProfessor.getFirstName(), "Marina");
      assertEquals(updatedProfessor.getLastName(), "Gioka");
      assertEquals(updatedProfessor.getEmail(), "marinag@gmail.com");
      assertEquals(updatedProfessor.getPhone(), "+306977123434");
      assertEquals(updatedProfessor.getBirthday(), LocalDate.of(1967, 7, 9));
    }
  }

  @Test
  public void givenMismatchProfessorIdAndObject_whenUpdateProfessor_thenVerifyBadRequest() throws Exception {

    Long professorId = 1L;

    Professor professor = Professor.builder()
        .id(7L)
        .firstName("Marina")
        .lastName("Gioka")
        .email("marinag@gmail.com")
        .phone("+30 6977123434")
        .birthday(LocalDate.of(1967, 7, 31))
        .build();

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .put("/professor/{id}", professorId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(professor)));

    response
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenProfessorIds_whenDeleteProfessors_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/professor")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());

    //TestData has a total of 5 professors

    int allCourses = professorRepository.findAll().size();
    assertEquals(3, allCourses);
  }

  @Test
  public void givenProfessorIdsNotExisting_whenDeleteProfessors_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(7L);
    ids.add(8L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/professor")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto)));

    response
        .andExpect(status().isOk());
  }

  @Test
  public void givenProfessorId_whenGetEnrolledCourses_thenVerifyResponse() throws Exception {

    Long professorId = 1L;

    mockMvc.perform(MockMvcRequestBuilders
            .get("/professor/{id}/courses", professorId))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  public void givenInvalidProfessorId_whenGetEnrolledCourses_thenVerifyNotFound() throws Exception {

    Long professorId = 7L;

    mockMvc.perform(MockMvcRequestBuilders
            .get("/professor/{id}/courses", professorId))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers
            .content()
            .contentType(MediaType.APPLICATION_JSON_VALUE));
  }
}