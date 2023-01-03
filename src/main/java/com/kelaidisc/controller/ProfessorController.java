package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.professor.ProfessorCreateDto;
import com.kelaidisc.dto.professor.ProfessorUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.service.ProfessorService;
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
@RequestMapping("/professor")
public class ProfessorController {

  private final ProfessorService professorService;

  private final ConversionService conversionService;

  @GetMapping
  public List<Professor> findAll(
      @RequestParam(value = "professorSearchField", required = false) ProfessorSearchField professorSearchField,
      @RequestParam(value = "searchTerm", required = false) String searchTerm) {
    if (nonNull(professorSearchField)) {
      return professorService.search(professorSearchField, searchTerm);
    }
    return professorService.findAll();
  }

  @GetMapping("/{id}")
  public Professor findById(@NotNull @Positive @PathVariable Long id) {
    return professorService.findOrThrow(id);
  }

  @PostMapping
  public Professor create(@Valid @RequestBody ProfessorCreateDto professor) {
    return professorService.create(Objects.requireNonNull(conversionService.convert(professor, Professor.class)));
  }

  @PutMapping("/{id}")
  public Professor update(@NotNull @Positive @PathVariable("id") Long id,
                          @Valid @RequestBody ProfessorUpdateDto professor) {

    if (!Objects.equals(professor.getId(), id)) {
      throw new UniversityBadRequestException(Professor.class, "id",
          "Must be the same as the path variable that is used");
    }
    return
        professorService.update(Objects.requireNonNull(conversionService.convert(professor, Professor.class)));
  }

  @Transactional
  @DeleteMapping
  public void delete(@Valid @RequestBody DeleteDto deleteDto) {
    professorService.deleteAllByIdIn(deleteDto.getIds());
  }

  @Transactional
  @GetMapping("/{id}/courses")
  public List<Course> getEnrolledCourses(@NotNull @Positive @PathVariable("id") Long professorId) {
    return professorService.findOrThrow(professorId).getCourses()
        .stream()
        .toList();
  }
}