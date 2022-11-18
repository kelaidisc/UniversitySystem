package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.converter.course.CourseCreateDtoToCourse;
import com.kelaidisc.converter.course.CourseUpdateDtoToCourse;
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
@RequestMapping("/api/v1/course")
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
  //  and then remove the UniversityBadRequestException ok
  @GetMapping("/{id}")
  public Course findById(@NotNull @Positive @PathVariable("id") Long id) {
    return courseService.findOrThrow(id);
  }

  @PostMapping
  public Course create(@Valid @RequestBody CourseCreateDto course) {
    return courseService.create(Objects.requireNonNull(courseCreateDtoToCourse.convert(course)));
  }

  // TODO You can also add validation annotations in method arguments so for example you could do this
  //  @NotNull @Positive @PathVariable("id") Long id
  //  and then remove the UniversityBadRequestException ok
  @PutMapping("/{id}")
  public Course update(@NotNull @Positive @PathVariable("id") Long id, @Valid @RequestBody CourseUpdateDto course) {

    if (!Objects.equals(course.getId(),
        id)) { // TODO This is wrong. You need the exact opposite. if object.getId() != pathVariableId -> then throw the exception ok
      throw new UniversityBadRequestException(Course.class, "id",
          "Must be the same as the path variable that is used");
    }

    return courseService.update(Objects.requireNonNull(courseUpdateDtoToCourse.convert(course)));
  }


  // TODO we do not want this to fail when the id does not exist
  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {

    // TODO Remove this check and create the appropriate validation annotations in DeleteDto ok
    courseService.deleteByIds(deleteDto.getIds());
  }

  // TODO Validate the path variables courseId and professorId with annotations ok
  @PatchMapping("/{id}/professor/{professorId}")
  public void assignProfessor(@NotNull @Positive @PathVariable("id") Long courseId,
                              @NotNull @Positive @PathVariable("professorId") Long professorId) {
    courseService.assignProfessorToCourse(courseId, professorId);
  }

  // TODO Validate the path variable courseId ok
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

  // TODO Validate the path variable id ok
  @PostMapping("/{id}/students")
  public void enrollStudent(@NotNull @Positive @PathVariable("id") Long courseId,
                            @Valid @RequestBody EnrollDto enrollDto) {
    courseService.enrollStudents(courseId, enrollDto.getIds());
  }

  // TODO This path is wrong, it should be /{id}/students
  //  Fix it and add the proper validation annotations and remove the manual check that exists inside the method ok
  @DeleteMapping("/{id}/students")
  public void disEnrollStudents(@NotNull @Positive @PathVariable("id") Long courseId,
                                @Valid @RequestBody DeleteDto deleteDto) {
    courseService.disEnrollStudents(courseId, deleteDto.getIds());
  }

}
