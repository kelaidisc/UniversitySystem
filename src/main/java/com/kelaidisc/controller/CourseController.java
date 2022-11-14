package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.converter.course.CourseCreateDtoToCourse;
import com.kelaidisc.converter.course.CourseUpdateDtoToCourse;
import com.kelaidisc.domain.Course;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.EnrollDto;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.dto.course.CourseUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.service.CourseService;
import java.util.List;
import java.util.Objects;
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

  // TODO You can also add validation annotations in method arguments so for example you could do this
  //  @NotNull @Positive @PathVariable("id") Long id
  //  and then remove the UniversityBadRequestException
  @GetMapping("/{id}")
  public Course findById(@PathVariable("id") Long id) {
    if (id == null) {
      throw new UniversityBadRequestException(Course.class, "id", "can't be null");
    }
    return courseService.findById(id);
  }

  @PostMapping
  public Course create(@Valid @RequestBody CourseCreateDto course) {
    return courseService.create(Objects.requireNonNull(courseCreateDtoToCourse.convert(course)));
  }

  // TODO You can also add validation annotations in method arguments so for example you could do this
  //  @NotNull @Positive @PathVariable("id") Long id
  //  and then remove the UniversityBadRequestException
  @PutMapping("/{id}")
  public Course update(@PathVariable("id") Long id, @Valid @RequestBody CourseUpdateDto course) {

    if (id == null) {
      throw new RuntimeException("Path id can't be null");
    }

    if (Objects.equals(course.getId(),
        id)) { // TODO This is wrong. You need the exact opposite. if object.getId() != pathVariableId -> then throw the exception
      throw new UniversityBadRequestException(Course.class, "id",
          "Must not be the same as the path variable that is used");
    }

    return courseService.update(Objects.requireNonNull(courseUpdateDtoToCourse.convert(course)));
  }


  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {

    // TODO Remove this check and create the appropriate validation annotations in DeleteDto
    if (deleteDto.getIds().isEmpty() || deleteDto.getIds() == null) {
      throw new UniversityBadRequestException(Course.class, "ids", "can't be null or empty");
    }
    courseService.deleteByIds(deleteDto.getIds());
  }

  // TODO Validate the path variables courseId and professorId with annotations
  @PatchMapping("/{id}/professor/{professorId}")
  public void assignProfessor(@PathVariable("id") Long courseId, @PathVariable("professorId") Long professorId) {
    courseService.assignProfessorToCourse(courseId, professorId);
  }

  // TODO Validate the path variable courseId
  @PatchMapping("/{id}/professor")
  public void removeProfessor(@PathVariable("id") Long courseId) {
    courseService.removeProfessorFromCourse(courseId);
  }

  // TODO Validate the path variable id
  @PostMapping("/{id}/students")
  public void enrollStudent(@PathVariable("id") Long courseId, @Valid @RequestBody EnrollDto enrollDto) {
    courseService.enrollStudents(courseId, enrollDto.getIds());
  }

  // TODO This path is wrong, it should be /{id}/students
  //  Fix it and add the proper validation annotations and remove the manual check that exists inside the method
  @DeleteMapping
  public void disEnrollStudents(Long courseId, @Valid @RequestBody DeleteDto deleteDto) {

    if (deleteDto.getIds().isEmpty() || deleteDto.getIds() == null) {
      throw new UniversityBadRequestException(Course.class, "ids", "can't be null or empty");
    }
    courseService.disEnrollStudents(courseId, deleteDto.getIds());
  }

}
