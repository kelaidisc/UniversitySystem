package com.kelaidisc.repository.impl;

import static com.kelaidisc.common.SqlUtils.safeExecuteUpdate;
import static com.kelaidisc.common.SqlUtils.safeSetDate;
import static com.kelaidisc.common.SqlUtils.safeSetLong;
import static com.kelaidisc.common.SqlUtils.safeSetString;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.ProfessorRepository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import lombok.SneakyThrows;

public class MySqlProfessorRepositoryImpl implements ProfessorRepository {

  private static final String FIND_ALL_Q = "SELECT * FROM university.professor";
  private static final String FIND_ALL_BY_FIRST_NAME_Q =
      "SELECT * FROM university.professor where first_name like concat('%',?,'%')";
  private static final String FIND_ALL_BY_LAST_NAME_Q =
      "SELECT * FROM university.professor where last_name like concat('%',?,'%')";

  private static final String FIND_ALL_BY_BIRTHDAY_Q = "SELECT * FROM university.professor where birthday=?";

  private static final String FIND_BY_ID_Q = "SELECT * FROM university.professor where id=?";
  private static final String FIND_BY_EMAIL_Q = "SELECT * FROM university.professor where email=?";
  private static final String FIND_BY_PHONE_Q = "SELECT * FROM university.professor where phone=?";
  private static final String CREATE_Q = """
      INSERT INTO university.professor
      (first_name, last_name, email, phone, birthday)
      VALUES(?, ?, ?, ?, ?)
      """;
  private static final String UPDATE_Q = """
      UPDATE university.professor
      SET first_name=?, last_name=?, email=?, phone=?, birthday=?
      WHERE id=?""";

  private static final String DELETE_BY_IDS_Q = "DELETE FROM university.professor WHERE id in (?)";

  @Override
  public List<Professor> findAll() {
    return getProfessorsFromDatabase(FIND_ALL_Q, (ps) -> {/*No Consumer needed*/});
  }

  @Override
  public List<Professor> findAllByFirstNameLike(String firstName) {
    return getProfessorsFromDatabase(FIND_ALL_BY_FIRST_NAME_Q, (ps) -> safeSetString(ps, 1, firstName));
  }

  @Override
  public List<Professor> findAllByLastNameLike(String lastName) {
    return getProfessorsFromDatabase(FIND_ALL_BY_LAST_NAME_Q, ps -> safeSetString(ps, 1, lastName));
  }

  @Override
  public List<Professor> findAllByBirthday(LocalDate birthday) {
    return getProfessorsFromDatabase(FIND_ALL_BY_BIRTHDAY_Q, ps -> safeSetDate(ps, 1, Date.valueOf(birthday)));
  }

  @Override
  public Professor findById(Long id) {
    return getProfessorFromDatabase(FIND_BY_ID_Q, ps -> safeSetLong(ps, 1, id));
  }

  @Override
  public Professor findByEmail(String email) {
    return getProfessorFromDatabase(FIND_BY_EMAIL_Q, ps -> safeSetString(ps, 1, email));

  }

  @Override
  public Professor findByPhone(String phone) {
    return getProfessorFromDatabase(FIND_BY_PHONE_Q, ps -> safeSetString(ps, 1, phone));
  }

  @Override
  @SneakyThrows
  public Professor create(Professor professor) {
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(CREATE_Q)) {
      safeSetAllFields(professor, ps);
      safeExecuteUpdate(ps);
      return professor;
    }
  }

  @Override
  @SneakyThrows
  public Professor update(Professor professor) {
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(UPDATE_Q)) {
      safeSetAllFields(professor, ps);
      ps.setLong(6, professor.getId());
      safeExecuteUpdate(ps);
      return professor;
    }
  }

  @Override
  @SneakyThrows
  public void deleteByIds(Set<Long> ids) {
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(DELETE_BY_IDS_Q)) {
      for (Long id : ids) {
        ps.setLong(1, id);
        safeExecuteUpdate(ps);
      }
    }
  }

  @SneakyThrows
  private List<Professor> getProfessorsFromDatabase(String query, Consumer<PreparedStatement> consumer) {
    List<Professor> list = new ArrayList<>();
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
      consumer.accept(ps);
      fillProfessorList(list, ps);
      return list;
    }
  }

  private void fillProfessorList(List<Professor> list, PreparedStatement ps) throws SQLException {
    var rs = ps.executeQuery();
    while (rs.next()) {
      list.add(convertResultToProfessor(rs));
    }
    rs.close();
  }

  @SneakyThrows
  public Professor getProfessorFromDatabase(String query, Consumer<PreparedStatement> consumer) {
    Professor professor = null;
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
      consumer.accept(ps);
      var rs = ps.executeQuery();
      while (rs.next()) {
        professor = convertResultToProfessor(rs);
      }
      rs.close();
      return professor;
    }
  }

  @SneakyThrows
  private void safeSetAllFields(Professor professor, PreparedStatement ps) {
    ps.setString(1, professor.getFirstName());
    ps.setString(2, professor.getLastName());
    ps.setString(3, professor.getEmail());
    ps.setString(4, professor.getPhone());
    Date date = Date.valueOf(professor.getBirthday());
    ps.setDate(5, date);
  }

  @SneakyThrows
  private Professor convertResultToProfessor(ResultSet rs) {
    Professor professor = new Professor();
    professor.setId(rs.getLong("id"));
    professor.setFirstName(rs.getString("first_name"));
    professor.setLastName(rs.getString("last_name"));
    professor.setEmail(rs.getString("email"));
    professor.setPhone(rs.getString("phone"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = rs.getString("birthday");
    LocalDate dob = LocalDate.parse(date, formatter);
    professor.setBirthday(dob);
    return professor;
  }
}
