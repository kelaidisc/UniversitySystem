package com.kelaidisc.service;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.model.ProfessorSearchField;
import com.kelaidisc.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

@Service
@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;

  public List<Professor> findAll() {
    return professorRepository.findAll();
  }

  public Optional<Professor> find(Long id){
    return professorRepository.findById(id);
  }

  public Professor findOrThrow(Long id) {
    return professorRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(Professor.class, id));
  }

  public List<Professor> search(ProfessorSearchField searchField, String searchTerm) {
    return switch (searchField) {
      case FIRST_NAME -> professorRepository.findAllByFirstNameEqualsIgnoreCaseLike(searchTerm);
      case LAST_NAME -> professorRepository.findAllByLastNameEqualsIgnoreCaseLike(searchTerm);
      case EMAIL -> Stream.of(professorRepository.findByEmail(searchTerm)).filter(Objects:: nonNull).collect(Collectors.toList());
      case PHONE -> Stream.of(professorRepository.findByPhone(searchTerm)).filter(Objects::nonNull).collect(Collectors.toList());
      case BIRTHDAY -> professorRepository.findAllByBirthday(LocalDate.parse(searchTerm, DATE_FORMATTER));
    };
  }

  public Professor create(Professor professor) {

    if(professorRepository.existsByLastNameAndFirstName(professor.getLastName(), professor.getFirstName())) {
      throw new UniversityDuplicateResourceException
              (Professor.class, "name", professor.getLastName() + " " + professor.getFirstName());
    }
    return professorRepository.save(professor);

  }

  public Professor update(Professor professor) {
    //TODO this test doesn't make sense
    if(professorRepository.existsByLastNameAndFirstName(professor.getLastName(), professor.getFirstName())) {
      throw new UniversityDuplicateResourceException
              (Professor.class, "name", professor.getLastName() + " " + professor.getFirstName());
    }
    return professorRepository.save(professor);

  }

  public void deleteAllByIdIn(Set<Long> ids) {
    professorRepository.deleteAllByIdIn(ids);
  }
}
