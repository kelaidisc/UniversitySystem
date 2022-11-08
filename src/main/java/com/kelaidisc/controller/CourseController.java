package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.converter.course.CourseCreateDtoToCourse;
import com.kelaidisc.converter.course.CourseUpdateDtoToCourse;
import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.dto.course.CourseUpdateDto;
import com.kelaidisc.service.CourseService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

  // TODO Check these out (Just for reference, do not copy/paste or deal with everything in there, because there are things you do not know yet)
  // Validation
  //   * https://www.baeldung.com/spring-boot-bean-validation
  //   * https://reflectoring.io/bean-validation-with-spring-boot
  //     * Check especially the "Validation Groups" section.
  // RESTful API
  //   * https://dilankam.medium.com/restful-api-design-best-practices-principles-ded471f573f3
  // Spring Boot exposing REST API (& test w/ Postman)
  //   * https://learnjava.co.in/how-to-create-springboot-rest-service-and-test-it-via-postman/

  private final CourseService courseService;
  private final CourseCreateDtoToCourse courseCreateDtoToCourse;
  private final CourseUpdateDtoToCourse courseUpdateDtoToCourse;

  @GetMapping
  public List<Course> findAll(@RequestParam(value = "name", required = false) String name) {
    if (nonNull(name) && name.trim().length() > 0) {
      return courseService.findAllByNameLike(name);
    }
    return courseService.findAll();
  }

  // TODO Check if the id is not null
  @GetMapping("/{id}")
  public Course findById(@PathVariable("id") Long id) {
    return courseService.findById(id);
  }

  /*
  // TODO Check if the Course has a non null id and throw a UniversityBadRequestException with the following arguments (Class, fieldName, errorMessage) e.g. (Course, id, "Must be null")
  */
  @PostMapping
  public Course create(@Valid @RequestBody CourseCreateDto course) {
    return courseService.create(courseCreateDtoToCourse.convert(course));
  }

  /*
  // TODO Check if the id is not Null
  // TODO Check if the Course has a null ID and throw a UniversityBadRequestException with the following arguments (Class, fieldName, errorMessage) e.g. (Course, id, "Must not be null")
  // TODO Check if the Course.id == id (@PathVariable) and throw a UniversityBadRequestException with the following arguments (Class, fieldName, errorMessage) e.g. (Course, id, "Must not the same as the path variable that is used")
  */
  @PutMapping("/{id}")
  public Course update(@PathVariable("id") Long id, @Valid @RequestBody CourseUpdateDto course) {
    return courseService.update(courseUpdateDtoToCourse.convert(course));
  }

  /*
  // TODO Check if the DeleteDto.ids set is not null and not empty
  */
  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {
    courseService.deleteByIds(deleteDto.getIds());
  }

  @PatchMapping("/{id}/professor/{professorId}")
  public void assignProfessor(@PathVariable("id") Long courseId, @PathVariable("professorId") Long professorId) {
    courseService.assignProfessorToCourse(courseId, professorId);
  }

  @PatchMapping("/{id}/professor")
  public void removeProfessor(@PathVariable("id") Long courseId) {
    courseService.removeProfessorFromCourse(courseId);
  }

  // TODO Do something similar for Student Enrollment/Dis-enrollment as with Professors (Maybe need to create a different dto class for this, not necessarily though)

}
