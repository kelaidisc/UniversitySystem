package com.kelaidisc.service;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.exception.UniversityDuplicateResourceException;
import com.kelaidisc.exception.UniversityNotFoundException;
import com.kelaidisc.repository.ProfessorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

  private final ProfessorRepository professorRepository;

  public Professor findOrThrow(Long id) {
    return professorRepository.findById(id)
        .orElseThrow(() -> new UniversityNotFoundException(Professor.class, id));
  }

  public List<Professor> search(String name, String email, String phone, LocalDate birthday) {
    return professorRepository.findAllByNameOrEmailOrPhoneOrBirthday(name, email, phone, birthday);
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