package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.UserRepository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Set;

import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

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

  @Override
  @SneakyThrows
  public void deleteByIds(Set<Long> ids) {
    try(CallableStatement cs = getInstance().getConn().prepareCall("{call professor_delete(?)}")){
      for (Long id : ids) {
        cs.setLong(1, id);
        cs.executeUpdate();
      }
    }
  }
}