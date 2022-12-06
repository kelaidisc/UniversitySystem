package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.student.StudentCreateDto;
import com.kelaidisc.dto.student.StudentUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.service.StudentService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

  private final StudentService studentService;

  private final ConversionService conversionService;

  @GetMapping
  public List<Student> findAll(
      @RequestParam(value = "studentSearchField", required = false) StudentSearchField studentSearchField,
      @RequestParam(value = "searchTerm", required = false) String searchTerm) {
    if (nonNull(studentSearchField)) {
      return studentService.search(studentSearchField, searchTerm);
    }
    return studentService.findAll();
  }

  @GetMapping("/{id}")
  public Student findById(@NotNull @Positive @PathVariable("id") Long id) {
    return studentService.findOrThrow(id);
  }

  @PostMapping
  public Student create(@Valid @RequestBody StudentCreateDto student) {
    return studentService.create(Objects.requireNonNull(conversionService.convert(student, Student.class)));
  }

  @PutMapping("/{id}")
  public Student update(@NotNull @Positive @PathVariable("id") Long id, @Valid @RequestBody StudentUpdateDto student) {

    if (!Objects.equals(student.getId(), id)) {
      throw new UniversityBadRequestException(Student.class, "id",
          "Must be the same as the path variable that is used");
    }
    return studentService.update(Objects.requireNonNull(conversionService.convert(student, Student.class)));
  }

  @Transactional
  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {
    studentService.deleteAllByIdIn(deleteDto.getIds());
  }

  @Transactional
  @GetMapping("/{id}/courses")
  public List<Course> getEnrolledCourses(@NotNull @Positive @PathVariable("id") Long studentId) {
    return studentService.findOrThrow(studentId).getCourses()
        .stream()
        .toList();
  }


}
