package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.service.ProfessorService;
import com.kelaidisc.service.StudentService;
import com.kelaidisc.shared.MySqlConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MySqlCourseRepositoryImpl implements CourseRepository {
    private ProfessorService professorService;
    private StudentService studentService;
    
    static Connection conn = MySqlConnectionProvider.getConn();
    @Override
    public List<Course> findAll() {
        String query = "SELECT *\n" +
                "FROM university.course";
        List<Course> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            getCourses(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    private static void getCourses(List<Course> list, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Course course = new Course();
            course.setId(rs.getLong("id"));
            course.setName(rs.getString("name"));
            list.add(course);
        }
        rs.close();
    }

    @Override
    public List<Course> findAllByNameLike(String name) {
        String query = "SELECT *\n" +
                "FROM university.course where name like concat('%',?,'%')";
        List<Course> list = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, name);
            getCourses(list, ps);

        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return list;
    }

    @Override
    public Course findById(Long id) {
        String query = "SELECT *\n" +
                "FROM university.course where id=?";
        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setLong(1, id);
            boolean exists = false;
            Course course = new Course();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                exists = true;

                course.setId(rs.getLong("id"));
                course.setName(rs.getString("name"));
            }
            
            rs.close();
            
            if (exists) return course;
        } catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
        }
        return null;
    }

    @Override
    public Course create(Course course) {
        String query = """
            INSERT INTO university.course
            (name)
            VALUES(?)""";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, course.getName());
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("Creating course failed " + e.getMessage());
        }
        return course;
    }

    @Override
    public Course update(Course course) {
        String query = """
            UPDATE university.course
            SET name=?
            WHERE id=?""";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, course.getName());
            ps.setLong(2,course.getId());
            ps.executeUpdate();

        }catch (SQLException e){
            System.out.println("Update failed " + e.getMessage());
        }
        return course;
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        String query = "DELETE FROM university.course\n" +
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

    @Override
    public Professor assignToCourse(Professor professor) {
        return null;
    }

    @Override
    public List<Student> enrollToCourse(List<Student> students) {
        return null;
    }
}
