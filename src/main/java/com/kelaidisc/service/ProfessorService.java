package com.kelaidisc.service;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.UserRepository;
import com.kelaidisc.repository.impl.ProfessorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.print.attribute.HashDocAttributeSet;
import lombok.NonNull;

public class ProfessorService {

  private final UserRepository<Professor> professorRepository = new ProfessorRepository();

  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

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
    return professorRepository.create(professor);
  }

  public Professor update(Professor professor) {
    return professorRepository.update(professor);
  }

  // TODO Now that a Professor is part of the Course, in order to delete a Professor you have to set the professor_id = NULL first
  // TODO This operation needs to be transactional. Why? What does it mean? (Google it and ask if any questions) and try to create a transaction
  public void deleteByIds(@NonNull Set<Long> ids) {
    professorRepository.deleteByIds(ids);
  }
}
