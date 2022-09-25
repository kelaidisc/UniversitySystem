package com.kelaidisc.service;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.ProfessorRepository;
import com.kelaidisc.repository.impl.MysqlProfessorRepositoryImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ProfessorService {

  private final ProfessorRepository professorRepository = new MysqlProfessorRepositoryImpl();

  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

  public Professor findById(Long id) {
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

  public void deleteByIds(Set<Long> ids) {
    professorRepository.deleteByIds(ids);
  }
}
