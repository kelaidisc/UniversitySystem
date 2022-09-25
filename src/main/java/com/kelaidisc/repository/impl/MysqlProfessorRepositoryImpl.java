package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.ProfessorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class MysqlProfessorRepositoryImpl implements ProfessorRepository {

  @Override
  public List<Professor> findAll() {
    return null;
  }

  @Override
  public List<Professor> findAllByFirstNameLike(String firstName) {
    return null;
  }

  @Override
  public List<Professor> findAllByLastNameLike(String lastName) {
    return null;
  }

  @Override
  public List<Professor> findAllByBirthday(LocalDate birthday) {
    return null;
  }

  @Override
  public Professor findById(Long id) {
    return null;
  }

  @Override
  public Professor findByEmail(String email) {
    return null;
  }

  @Override
  public Professor findByPhone(String phone) {
    return null;
  }

  @Override
  public Professor create(Professor professor) {
    return null;
  }

  @Override
  public Professor update(Professor professor) {
    return null;
  }

  @Override
  public void deleteByIds(Set<Long> ids) {

  }
}
