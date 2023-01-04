package com.kelaidisc.controller;

import static java.util.Objects.nonNull;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.professor.ProfessorCreateDto;
import com.kelaidisc.dto.professor.ProfessorUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.service.ProfessorService;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RequestMapping("/professor")
public class ProfessorController {

  private final ProfessorService professorService;

  private final ConversionService conversionService;

  @GetMapping
  public List<Professor> findAll(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "birthday", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 LocalDate birthday) {

    validateName(name);
    validateEmail(email);
    validatePhone(phone);

    return professorService.search(name, email, phone, birthday);
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
      throw new UniversityBadRequestException("Invalid request, Id must be the same as the path variable that is used");
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

  private void validateName(String name) {

    if (nonNull(name) && name.trim().length() == 0) {
      throw new InvalidParameterException("Name can't be empty");
    }
  }

  private void validateEmail(String email) {

    if (nonNull(email) && email.trim().length() == 0) {
      throw new InvalidParameterException("Email can't be empty");
    }

    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    Pattern pattern = Pattern.compile(regex);

    if (nonNull(email)) {

      Matcher matcher = pattern.matcher(email);
      if (!matcher.matches()) {
        throw new InvalidParameterException("Invalid email");
      }
    }
  }

  private void validatePhone(String phone) {

    if (nonNull(phone) && phone.trim().length() == 0) {
      throw new InvalidParameterException("Phone can't be empty");
    }

    String regex = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$";
    Pattern pattern = Pattern.compile(regex);

    if (nonNull(phone)) {
      Matcher matcher = pattern.matcher(phone);
      if (!matcher.matches()) {
        throw new InvalidParameterException("Invalid phone");
      }
    }
  }
}