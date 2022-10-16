package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.UserRepository;
import java.sql.ResultSet;
import lombok.SneakyThrows;

public class StudentRepository implements UserRepository<Student> {


  @Override
  public String getTableName() {
    return "university.student";
  }

  @Override
  @SneakyThrows
  public Student convertResultToEntity(ResultSet rs) {
    var student = new Student();
    setUserFieldsFromResultSet(student, rs);
    return student;
  }

}
