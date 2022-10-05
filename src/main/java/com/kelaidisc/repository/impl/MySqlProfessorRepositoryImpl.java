package com.kelaidisc.repository.impl;

import static com.kelaidisc.shared.MySqlConnectionProvider.getConn;

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
import java.util.function.Function;
import java.util.function.Supplier;

public class MySqlProfessorRepositoryImpl implements ProfessorRepository {

  private final static String FIND_ALL_Q = "SELECT * FROM university.professor";
  private final static String FIND_ALL_BY_FIRST_NAME_Q =
      "SELECT * FROM university.professor where first_name like concat('%',?,'%')";
  private final static String FIND_ALL_BY_LAST_NAME_Q =
      "SELECT * FROM university.professor where last_name like concat('%',?,'%')";

  private final static String FIND_ALL_BY_BIRTHDAY_Q = "SELECT * FROM university.professor where birthday=?";

  private final static String FIND_BY_ID_Q = "SELECT * FROM university.professor where id=?";
  private static final String FIND_BY_EMAIL_Q = "SELECT * FROM university.professor where email=?";
  private static final String FIND_BY_PHONE_Q = "SELECT * FROM university.professor where phone=?";


  public List<Professor> getProfessorsFromDatabase(String query, Consumer<PreparedStatement> consumer) {
    List<Professor> list = new ArrayList<>();
    try (PreparedStatement ps = getConn().prepareStatement(query)) {
      consumer.accept(ps);
      fillProfessorList(list, ps);
      return list;
    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public List<Professor> findAll() {
    return getProfessorsFromDatabase(FIND_ALL_Q, (ps) -> {});
  }

  @Override
  public List<Professor> findAllByFirstNameLike(String firstName) {
    return getProfessorsFromDatabase(FIND_ALL_BY_FIRST_NAME_Q, (ps) -> {
      try {
        ps.setString(1, firstName);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public List<Professor> findAllByLastNameLike(String lastName) {
    List<Professor> list = new ArrayList<>();
    try (PreparedStatement ps = getConn().prepareStatement(FIND_ALL_BY_LAST_NAME_Q)) {

      ps.setString(1, lastName);
      fillProfessorList(list, ps);

    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public List<Professor> findAllByBirthday(LocalDate birthday) {
    List<Professor> list = new ArrayList<>();
    try (PreparedStatement ps = getConn().prepareStatement(FIND_ALL_BY_BIRTHDAY_Q)) {

      ps.setDate(1, java.sql.Date.valueOf(birthday));
      fillProfessorList(list, ps);

    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public Professor findById(Long id) {
    try (PreparedStatement ps = getConn().prepareStatement(FIND_BY_ID_Q)) {
      ps.setLong(1, id);
      return getProfessor(ps);
    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
      return null;
    }
  }

  @Override
  public Professor findByEmail(String email) {
    try (PreparedStatement ps = getConn().prepareStatement(FIND_BY_EMAIL_Q)) {

      ps.setString(1, email);
      return getProfessor(ps);

    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
      return null;
    }
  }

  @Override
  public Professor findByPhone(String phone) {
    try (PreparedStatement ps = getConn().prepareStatement(FIND_BY_PHONE_Q)) {

      ps.setString(1, phone);
      return getProfessor(ps);
    } catch (SQLException e) {
      System.out.println("Query failed " + e.getMessage());
      return null;
    }
  }

  @Override
  public Professor create(Professor professor) {
    String query = """
        INSERT INTO university.professor
        (first_name, last_name, email, phone, birthday) 
        VALUES(?, ?, ?, ?, ?)
        """;
    try (PreparedStatement ps = getConn().prepareStatement(query)) {
      ps.setString(1, professor.getFirstName());
      ps.setString(2, professor.getLastName());
      ps.setString(3, professor.getEmail());
      ps.setString(4, professor.getPhone());
      Date date = Date.valueOf(professor.getBirthday());
      ps.setDate(5, date);
      ps.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Creating professor failed " + e.getMessage());
    }
    return professor;
  }

  @Override
  public Professor update(Professor professor) {
    String query = """
        UPDATE university.professor
        SET first_name=?, last_name=?, email=?, phone=?, birthday=?
        WHERE id=?""";
    try (PreparedStatement ps = getConn().prepareStatement(query)) {
      ps.setString(1, professor.getFirstName());
      ps.setString(2, professor.getLastName());
      ps.setString(3, professor.getEmail());
      ps.setString(4, professor.getPhone());
      Date date = Date.valueOf(professor.getBirthday());
      ps.setDate(5, date);
      ps.setLong(6, professor.getId());
      ps.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Update failed " + e.getMessage());
    }
    return professor;
  }

  @Override
  public void deleteByIds(Set<Long> ids) {
    String query = "DELETE FROM university.professor\n" +
        "WHERE id=?";
    try (PreparedStatement ps = getConn().prepareStatement(query)) {
      for (Long id : ids) {
        ps.setLong(1, id);
        ps.executeUpdate();
      }
    } catch (SQLException e) {
      System.out.println("Delete failed " + e.getMessage());
    }
  }

  private Professor getProfessor(PreparedStatement ps) throws SQLException {
    Professor professor = null;
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      professor = convertResultToProfessor(rs);
    }
    rs.close();
    return professor;
  }

  private void fillProfessorList(List<Professor> list, PreparedStatement ps) throws SQLException {
    var rs = ps.executeQuery();
    while (rs.next()) {
      list.add(convertResultToProfessor(rs));
    }
    rs.close();
  }

  private static Professor convertResultToProfessor(ResultSet rs) throws SQLException {
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
