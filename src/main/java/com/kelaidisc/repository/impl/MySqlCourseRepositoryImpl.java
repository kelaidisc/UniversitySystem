package com.kelaidisc.repository.impl;

import static com.kelaidisc.common.SqlUtils.*;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.CourseRepository;
import com.kelaidisc.service.ProfessorService;
import com.kelaidisc.service.StudentService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import lombok.SneakyThrows;

public class MySqlCourseRepositoryImpl implements CourseRepository {

    private ProfessorService professorService; //make this final for dependency injection
    private StudentService studentService;     //same
    private static final String FIND_ALL_Q = "SELECT * FROM university.course";
    private static final String FIND_ALL_BY_NAME_Q = "SELECT * FROM university.course where name like concat('%',?,'%')";
    private static final String FIND_BY_ID_Q = "SELECT * FROM university.course where id=?";
    private static final String CREATE_Q = """
            INSERT INTO university.course
            (name)
            VALUES(?)""";

    private static final String UPDATE_Q = """
            UPDATE university.course
            SET name=?
            WHERE id=?""";
    private static final String DELETE_BY_IDS_Q = "DELETE FROM university.course WHERE id in (?)";

    @Override
    public List<Course> findAll() {
        return getCoursesFromDatabase(FIND_ALL_Q, (ps) -> {/*No needed Consumer */});
    }

    @Override
    @SneakyThrows
    public List<Course> findAllByNameLike(String name) {
        return getCoursesFromDatabase(FIND_ALL_BY_NAME_Q, (ps) -> safeSetString(ps, 1, name));
    }
    @Override
    @SneakyThrows
    public Course findById(Long id) {
        return getCourseFromDatabase(FIND_BY_ID_Q,(ps) -> safeSetLong(ps, 1, id));
    }
    @Override
    @SneakyThrows
    public Course create(Course course) {
        try(PreparedStatement ps = getInstance().getConn().prepareStatement(CREATE_Q)) {
            ps.setString(1, course.getName());
            safeExecuteUpdate(ps);
            return course;
        }
    }

    @Override
    @SneakyThrows
    public Course update(Course course) {
        try(PreparedStatement ps = getInstance().getConn().prepareStatement(UPDATE_Q)){
            ps.setString(1, course.getName());
            ps.setLong(2,course.getId());
            safeExecuteUpdate(ps);
            return course;
        }
    }
    @Override
    @SneakyThrows
    public void deleteByIds(Set<Long> ids) {
        try(PreparedStatement ps = getInstance().getConn().prepareStatement(DELETE_BY_IDS_Q)){
            for(Long id : ids){
                ps.setLong(1, id);
                safeExecuteUpdate(ps);
            }
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

    @SneakyThrows
    public List<Course> getCoursesFromDatabase(String query, Consumer<PreparedStatement> consumer) {
        List<Course> list = new ArrayList<>();
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
            consumer.accept(ps);
            fillCoursesList(list, ps);
            return list;
        }
    }

    @SneakyThrows
    private void fillCoursesList(List<Course> list, PreparedStatement ps) {
        var rs = ps.executeQuery();
        while (rs.next()) {
            list.add(convertResultToCourse(rs));
        }
        rs.close();
    }

    @SneakyThrows
    public Course getCourseFromDatabase(String query, Consumer<PreparedStatement> consumer) {
        Course course = null;
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
            consumer.accept(ps);
            var rs = ps.executeQuery();
            while (rs.next()) {
                course = convertResultToCourse(rs);
            }
            rs.close();
            return course;
        }
    }

    @SneakyThrows
    private Course convertResultToCourse(ResultSet rs) {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setName(rs.getString("name"));
        return course;
    }
}
