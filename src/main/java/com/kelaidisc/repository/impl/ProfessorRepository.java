package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.UserRepository;
import java.sql.ResultSet;
import lombok.SneakyThrows;

public class ProfessorRepository implements UserRepository<Professor> {

  @Override
  public String getTableName() {
    return "university.professor";
  }

  @Override
  @SneakyThrows
  public Professor convertResultToEntity(ResultSet rs) {
    Professor professor = new Professor();
    setUserFieldsFromResultSet(professor, rs);
    return professor;
  }

}
