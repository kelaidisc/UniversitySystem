package com.kelaidisc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelaidisc.FlywayTestConfig;
import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.DeleteDto;
import java.util.HashSet;
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


  @Test
  public void givenCourseURI_whenGetAllCourses_thenVerifyResponse() throws Exception {

    final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/course"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
  }

  @Test
  public void givenCourseId_whenGetCourseById_thenVerifyResponse() throws Exception {

    this.mockMvc.perform(MockMvcRequestBuilders.get("/course/{id}", 1))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void givenInvalidCourseId_whenGetCourseById_thenVerifyNotFound() throws Exception {

    this.mockMvc.perform(MockMvcRequestBuilders.get("/course/{id}", 10000000))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenCourseObject_whenCreateCourse_thenVerifyResponse() throws Exception {

    Course course = Course.builder()
        .name("Gym")
        .description("descr")
        .build();

    ResultActions response = mockMvc.perform(
        MockMvcRequestBuilders.post("/course")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(course)));

    response
        .andDo(print())
        .andExpect(status().isOk());
  }


  @Test
  public void givenCourseIdAndObject_whenUpdateCourse_thenVerifyResponse() throws Exception {

    Course course = Course.builder().id(1L).name("Logic").description("DescriptionText1234").build();

    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/course/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(course)));

    response.andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void givenMismatchCourseIdAndObject_whenUpdateCourse_thenVerifyBadRequest() throws Exception {

    Course course = Course.builder().id(7L).name("Logic").description("DescriptionText1234").build();

    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/course/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(course)));

    response.andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  public void givenCourseIds_whenDeleteCourses_thenVerifyResponse() throws Exception {

    Set<Long> ids = new HashSet<>();
    ids.add(1L);
    ids.add(2L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/course")
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(deleteDto)));

    response.andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void givenCourseIdsNotExisting_whenDeleteCourses_thenVerifyResponse() throws Exception {
    Set<Long> ids = new HashSet<>();
    ids.add(7L);
    ids.add(8L);
    DeleteDto deleteDto = new DeleteDto(ids);

    ResultActions response = mockMvc.perform(
        MockMvcRequestBuilders
            .delete("/course")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(deleteDto))
    );

    response.andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void givenCourseId_whenGetEnrolledStudents_thenVerifyResponse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/course/{id}/students", 1)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  public void givenInvalidCourseId_whenGetEnrolledStudents_thenVerifyNotFound() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/course/{id}/students", 8)).andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }
}

