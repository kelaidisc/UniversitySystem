package com.kelaidisc.service;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.ProfessorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;

  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

  public Professor findOrThrow(Long id) {
    return professorRepository.findById(id)
        .orElseThrow(() -> new UniversityNotFoundException(Professor.class, id));
  }

  public List<Professor> search(ProfessorSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> professorRepository.findAllByFirstNameContainingIgnoreCase(searchTerm);
      case LAST_NAME -> professorRepository.findAllByLastNameContainingIgnoreCase(searchTerm);
      case EMAIL ->
          Stream.of(professorRepository.findByEmail(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case PHONE ->
          Stream.of(professorRepository.findByPhone(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case BIRTHDAY -> professorRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER));
    };
  }

  public Professor create(Professor professor) {
    validateEmail(professor);
    validatePhone(professor);
    return professorRepository.save(professor);
  }

  public Professor update(Professor professor) {
    validateEmail(professor);
    validatePhone(professor);
    return professorRepository.save(professor);
  }


  private void validateEmail(@NotNull Professor professor) {
    if (professorRepository.existsByEmailAndIdIsNot(professor.getEmail(), professor.getId())) {
      throw new UniversityDuplicateResourceException(Professor.class, "email", professor.getEmail());
    }
  }

  private void validatePhone(@NotNull Professor professor) {
    if (professorRepository.existsByPhoneAndIdIsNot(professor.getPhone(), professor.getId())) {
      throw new UniversityDuplicateResourceException(Professor.class, "phone", professor.getPhone());
    }
  }

  public void deleteAllByIdIn(Set<Long> ids) {
    professorRepository.deleteAllByIdIn(ids);
  }
}
