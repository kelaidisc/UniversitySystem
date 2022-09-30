package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Professor;
import com.kelaidisc.repository.ProfessorRepository;
import com.kelaidisc.shared.MySqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Set;

public class MySqlProfessorRepositoryImpl implements ProfessorRepository {

  static Connection conn = MySqlConnectionProvider.getConn();
  @Override
  public List<Professor> findAll () {
    String query = "SELECT *\n" +
            "FROM university.professor";
    List<Professor> list = new ArrayList<>();
    try(PreparedStatement ps = conn.prepareStatement(query)){

      getProfessors(list, ps);

    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  private static void getProfessors(List<Professor> list, PreparedStatement ps) throws SQLException {
    ResultSet rs = ps.executeQuery();

    while(rs.next()){
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
      list.add(professor);
    }
    rs.close();
  }


  @Override
  public List<Professor> findAllByFirstNameLike(String firstName) {
    String query = "SELECT *\n" +
            "FROM university.professor where first_name like concat('%',?,'%')";
    List<Professor> list = new ArrayList<>();
    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setString(1, firstName);
      getProfessors(list, ps);

    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public List<Professor> findAllByLastNameLike(String lastName) {
    String query = "SELECT *\n" +
            "FROM university.professor where last_name like concat('%',?,'%')";
    List<Professor> list = new ArrayList<>();
    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setString(1, lastName);
      getProfessors(list, ps);

    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public List<Professor> findAllByBirthday(LocalDate birthday) {
    String query = "SELECT *\n" +
            "FROM university.professor where birthday=?";
    List<Professor> list = new ArrayList<>();
    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setDate(1, java.sql.Date.valueOf(birthday));
      getProfessors(list, ps);

    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return list;
  }

  @Override
  public Professor findById(Long id) {
    String query = "SELECT *\n" +
            "FROM university.professor where id=?";
    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setLong(1, id);
      Professor professor = getProfessor(ps);
      if (professor != null) return professor;
    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return null;
  }

  private static Professor getProfessor(PreparedStatement ps) throws SQLException {
    boolean exists = false;
    Professor professor = new Professor();
    ResultSet rs = ps.executeQuery();
    while (rs.next()){
      exists = true;

      professor.setId(rs.getLong("id"));
      professor.setFirstName(rs.getString("first_name"));
      professor.setLastName(rs.getString("last_name"));
      professor.setEmail(rs.getString("email"));
      professor.setPhone(rs.getString("phone"));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String date = rs.getString("birthday");
      LocalDate birthday = LocalDate.parse(date, formatter);
      professor.setBirthday(birthday);
    }
    if(exists){
      return professor;
    }
    rs.close();
    return null;
  }


  @Override
  public Professor findByEmail(String email) {
    String query = "SELECT *\n" +
            "FROM university.professor where email=?";
    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setString(1, email);
      Professor professor = getProfessor(ps);
      if (professor != null) return professor;
    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return null;
  }

  @Override
  public Professor findByPhone(String phone) {
    String query = "SELECT *\n" +
            "FROM university.professor where phone=?";

    try(PreparedStatement ps = conn.prepareStatement(query)){

      ps.setString(1, phone);
      Professor professor = getProfessor(ps);
      if (professor != null) return professor;
    } catch (SQLException e){
      System.out.println("Query failed " + e.getMessage());
    }
    return null;
  }

  @Override
  public Professor create(Professor professor) {
    String query = """
            INSERT INTO university.professor
            (first_name, last_name, email, phone, birthday)
            VALUES(?, ?, ?, ?, ?)""";
    try(PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, professor.getFirstName());
      ps.setString(2, professor.getLastName());
      ps.setString(3, professor.getEmail());
      ps.setString(4, professor.getPhone());
      Date date = Date.valueOf(professor.getBirthday());
      ps.setDate(5, date);
      ps.executeUpdate();

    }catch (SQLException e){
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
    try(PreparedStatement ps = conn.prepareStatement(query)){
      ps.setString(1, professor.getFirstName());
      ps.setString(2, professor.getLastName());
      ps.setString(3, professor.getEmail());
      ps.setString(4, professor.getPhone());
      Date date = Date.valueOf(professor.getBirthday());
      ps.setDate(5, date);
      ps.setLong(6, professor.getId());
      ps.executeUpdate();

    }catch (SQLException e){
      System.out.println("Update failed " + e.getMessage());
    }
    return professor;
  }

  @Override
  public void deleteByIds(Set<Long> ids) {
    String query = "DELETE FROM university.professor\n" +
            "WHERE id=?";
    try(PreparedStatement ps = conn.prepareStatement(query)){
      for(Long id : ids){
        ps.setLong(1, id);
        ps.executeUpdate();
      }
    }catch (SQLException e){
      System.out.println("Delete failed " + e.getMessage());
    }
  }
}
