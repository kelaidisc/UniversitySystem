package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.EnrollDto;
import com.kelaidisc.dto.course.CourseCreateDto;
import com.kelaidisc.dto.course.CourseUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.service.CourseService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
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
  private final ConversionService conversionService;

  @GetMapping
  public List<Course> findAll(@RequestParam(value = "name", required = false) String name) {
    if (nonNull(name) && name.trim().length() > 0) {
      return courseService.findAllByNameLike(name);
    }
    return courseService.findAll();
  }

  @GetMapping("/{id}")
  public Course findById(@NotNull @Positive @PathVariable("id") Long id) {
    return courseService.findOrThrow(id);
  }

  @PostMapping
  public Course create(@Valid @RequestBody CourseCreateDto course) {
    return courseService.create(Objects.requireNonNull(conversionService.convert(course, Course.class)));
  }

  @PutMapping("/{id}")
  public Course update(@NotNull @Positive @PathVariable("id") Long id, @Valid @RequestBody CourseUpdateDto course) {
    if (!Objects.equals(course.getId(), id)) {
      throw new UniversityBadRequestException(Course.class, "id",
          "Must be the same as the path variable that is used");
    }

    return courseService.update(Objects.requireNonNull(conversionService.convert(course, Course.class)));
  }

  @Transactional
  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {
    courseService.deleteByIds(deleteDto.getIds());
  }

  @PatchMapping("/{id}/professor/{professorId}")
  public void assignProfessor(@NotNull @Positive @PathVariable("id") Long courseId,
                              @NotNull @Positive @PathVariable("professorId") Long professorId) {
    courseService.assignProfessorToCourse(courseId, professorId);
  }

  @PatchMapping("/{id}/professor")
  public void removeProfessor(@NotNull @Positive @PathVariable("id") Long courseId) {
    courseService.removeProfessorFromCourse(courseId);
  }


  @Transactional
  @GetMapping("/{id}/students")
  public List<Student> getEnrolledStudents(@NotNull @Positive @PathVariable("id") Long courseId) {
    return courseService.findOrThrow(courseId).getStudents()
        .stream()
        .toList();
  }

  @Transactional
  @PostMapping("/{id}/students")
  public void enrollStudent(@NotNull @Positive @PathVariable("id") Long courseId,
                            @Valid @RequestBody EnrollDto enrollDto) {
    courseService.enrollStudents(courseId, enrollDto.getIds());
  }

  @Transactional
  @DeleteMapping("/{id}/students")
  public void disEnrollStudents(@NotNull @Positive @PathVariable("id") Long courseId,
                                @Valid @RequestBody DeleteDto deleteDto) {
    courseService.disEnrollStudents(courseId, deleteDto.getIds());
  }

}
