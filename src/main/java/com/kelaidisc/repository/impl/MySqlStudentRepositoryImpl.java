package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.StudentRepository;
import com.kelaidisc.shared.MySqlConnectionProvider;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MySqlStudentRepositoryImpl implements StudentRepository {
    static Connection conn = MySqlConnectionProvider.getConn();
    @Override
    public List<Student> findAll() {
        String query = "SELECT *\n" +
                "FROM university.student";
        List<Student> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            getStudents(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    private static void getStudents(List<Student> list, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = rs.getString("birthday");
            LocalDate dob = LocalDate.parse(date, formatter);
            student.setBirthday(dob);
            list.add(student);
        }
        rs.close();
    }

    @Override
    public List<Student> findAllByFirstNameLike(String firstName) {
        String query = "SELECT *\n" +
                "FROM university.student where first_name like concat('%',?,'%')";
        List<Student> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, firstName);
            getStudents(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> findAllByLastNameLike(String lastName) {
        String query = "SELECT *\n" +
                "FROM university.student where last_name like concat('%',?,'%')";
        List<Student> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, lastName);
            getStudents(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
        String query = "SELECT *\n" +
                "FROM university.student where birthday=?";
        List<Student> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setDate(1, java.sql.Date.valueOf(birthday));
            getStudents(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    @Override
    public Student findById(Long id) {
        String query = "SELECT *\n" +
                "FROM university.student where id=?";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setLong(1, id);
            Student student = getStudent(ps);
            if (student != null) return student;
        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return null;
    }
    private static Student getStudent(PreparedStatement ps) throws SQLException {
        boolean exists = false;
        Student student = new Student();
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            exists = true;

            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = rs.getString("birthday");
            LocalDate birthday = LocalDate.parse(date, formatter);
            student.setBirthday(birthday);
        }
        if(exists){
            return student;
        }
        rs.close();
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        String query = "SELECT *\n" +
                "FROM university.student where email=?";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, email);
            Student student = getStudent(ps);
            if (student != null) return student;
        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return null;
    }

    @Override
    public Student findByPhone(String phone) {
        String query = "SELECT *\n" +
                "FROM university.student where phone=?";

        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, phone);
            Student student = getStudent(ps);
            if (student != null) return student;
        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return null;
    }

    @Override
    public Student create(Student student) {
        String query = """
            INSERT INTO university.student
            (first_name, last_name, email, phone, birthday)
            VALUES(?, ?, ?, ?, ?)""";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhone());
            Date date = Date.valueOf(student.getBirthday());
            ps.setDate(5, date);
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("Creating student failed " + e.getMessage());
        }
        return student;
    }

    @Override
    public Student update(Student student) {
        String query = """
            UPDATE university.student
            SET first_name=?, last_name=?, email=?, phone=?, birthday=?
            WHERE id=?""";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhone());
            Date date = Date.valueOf(student.getBirthday());
            ps.setDate(5, date);
            ps.setLong(6, student.getId());
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("Update failed " + e.getMessage());
        }
        return student;
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        String query = "DELETE FROM university.student\n" +
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
