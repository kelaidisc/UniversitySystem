package com.kelaidisc.repository.previousRepos;

import com.kelaidisc.domain.Student;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

public class StudentRepositoryMine implements UserRepositoryMine<Student> {


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
    var baseList = UserRepositoryMine.super.getFieldNames();
    baseList.add("registration_date");
    return baseList;
  }

  @Override
  @SneakyThrows
  public void setUserFieldsFromResultSet(Student student, ResultSet rs) {
    UserRepositoryMine.super.setUserFieldsFromResultSet(student, rs);
    String registrationDate = rs.getString("registration_date");
    student.setBirthday(LocalDate.parse(registrationDate, DATE_FORMATTER));
  }

  @Override
  @SneakyThrows
  public void setAllFieldsFromEntity(Student student, PreparedStatement ps) {
    UserRepositoryMine.super.setAllFieldsFromEntity(student, ps);
    ps.setDate(6, Date.valueOf(student.getRegistrationDate()));
  }

  @Override
  @SneakyThrows
  public void deleteByIds(Set<Long> ids) {
    try(CallableStatement cs = getInstance().getConn().prepareCall("{call student_delete(?)}")){
      for (Long id : ids){
        cs.setLong(1, id);
        cs.executeUpdate();
      }
    }
  }
}
