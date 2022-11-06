package com.kelaidisc.service;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.ProfessorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;

  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

  /*
  TODO
  1. Create a new package, call it exception
  2. Create a new class called UniversityNotFoundException and extend from RuntimeException
  3. Convert the Optional<Professor> coming from the CrudRepository to Professor by adding the .orElseThrow(() -> new NotFoundException());
 */
  public Professor findById(@NonNull Long id) {
    return professorRepository.findById(id);
  }

  public List<Professor> search(ProfessorSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> professorRepository.findAllByFirstNameLike(searchTerm);
      case LAST_NAME -> professorRepository.findAllByLastNameLike(searchTerm);
      case EMAIL -> List.of(professorRepository.findByEmail(searchTerm));
      case PHONE -> List.of(professorRepository.findByPhone(searchTerm));
      case BIRTHDAY -> professorRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER));
    };
  }

  public Professor create(Professor professor) {
    return professorRepository.save(professor);
  }

  public Professor update(Professor professor) {
    return professorRepository.save(professor);
  }

  public void deleteByIds(@NonNull Set<Long> ids) {
    professorRepository.deleteAllById(ids);
  }
}
