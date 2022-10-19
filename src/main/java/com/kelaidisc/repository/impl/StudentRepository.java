package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.UserRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import lombok.SneakyThrows;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;

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

  @Override
  public List<String> getFieldNames() {
    var baseList = UserRepository.super.getFieldNames();
    baseList.add("registration_date");
    return baseList;
  }

  @Override
  @SneakyThrows
  public void setUserFieldsFromResultSet(Student student, ResultSet rs) {
    UserRepository.super.setUserFieldsFromResultSet(student, rs);
    String registrationDate = rs.getString("registration_date");
    student.setBirthday(LocalDate.parse(registrationDate, DATE_FORMATTER));
  }

  @Override
  @SneakyThrows
  public void setAllFieldsFromEntity(Student student, PreparedStatement ps) {
    UserRepository.super.setAllFieldsFromEntity(student, ps);
    ps.setDate(6, Date.valueOf(student.getRegistrationDate()));
  }
}
