package com.kelaidisc.repository;

import com.kelaidisc.domain.Professor;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProfessorRepository {

  List<Professor> findAll();

  List<Professor> findAllByFirstNameLike(String firstName);

  List<Professor> findAllByLastNameLike(String lastName);

  List<Professor> findAllByBirthday(LocalDate birthday);

  Professor findById(Long id);

  Professor findByEmail(String email);

  Professor findByPhone(String phone);

  Professor create(Professor professor);

  Professor update(Professor professor);

  void deleteByIds(Set<Long> ids);

}
