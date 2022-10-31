package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Course;
import com.kelaidisc.domain.Professor;
import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.CrudRepository;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static com.kelaidisc.common.SqlUtils.safeSetString;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;
import static java.lang.String.format;

public class CourseRepository implements CrudRepository<Course> {

    private static final String FIND_ALL_BY_NAME_Q = "SELECT * FROM table_name where name like concat('%',?,'%')";
    private static final String ASSIGN_PROFESSOR_Q = "UPDATE course SET professor_id = %d WHERE id = %d";
    private static final String ENROLL_STUDENTS_Q = "INSERT INTO course_students (`course_id`, `student_id`) VALUES (%d,%d)";

    @Override
    public String getTableName() {
        return "university.course";
    }

    @Override
    public List<String> getFieldNames() {
        return List.of("name");
    }

    @Override
    @SneakyThrows
    public Course convertResultToEntity(ResultSet rs) {
        var course = new Course();
        course.setId(rs.getLong("id"));
        course.setName(rs.getString("name"));
        return course;
    }

    @Override
    @SneakyThrows
    public void setAllFieldsFromEntity(Course course, PreparedStatement ps) {
        ps.setString(1, course.getName());
    }

    public List<Course> findAllByNameLike(String name) {
        return getAllFromDatabase(FIND_ALL_BY_NAME_Q.replaceFirst("table_name", getTableName()),
                ps -> safeSetString(ps, 1, name));
    }

    @SneakyThrows
    public void assignProfessor(Course course, Professor professor) {
        try (PreparedStatement preparedStatement = getInstance().getConn().prepareStatement(
                format(ASSIGN_PROFESSOR_Q, professor.getId(), course.getId()))) {
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public void enrollStudents(Course course, List<Student> students) {
        for(Student student : students){
            try (PreparedStatement preparedStatement = getInstance().getConn().prepareStatement(format(ENROLL_STUDENTS_Q, course.getId(), student.getId()))) {
                preparedStatement.executeUpdate();
            }
        }
    }
}
