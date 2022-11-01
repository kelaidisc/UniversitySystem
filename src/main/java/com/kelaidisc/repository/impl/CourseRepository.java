package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Course;
import com.kelaidisc.repository.CrudRepository;
import lombok.SneakyThrows;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import static com.kelaidisc.common.SqlUtils.safeSetString;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

public class CourseRepository implements CrudRepository<Course> {

    private static final String FIND_ALL_BY_NAME_Q = "SELECT * FROM table_name where name like concat('%',?,'%')";

    // TODO Use ? (with the prepared statement) instead of %d ok
    private static final String ASSIGN_PROFESSOR_Q = "UPDATE course SET professor_id = ? WHERE id = ?";

    // TODO Use ? (with the prepared statement) instead of %d ok
    // TODO What happens if there is already an entry with the given course_id & student_id? Can you make this query idempotent? Search it out. ok
    private static final String ENROLL_STUDENTS_Q = "INSERT IGNORE INTO course_students (`course_id`, `student_id`) VALUES (?,?)";

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
    public void assignProfessor(Long courseId, Long professorId) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(ASSIGN_PROFESSOR_Q)) {
            ps.setLong(1, professorId);
            ps.setLong(2, courseId);
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    // TODO Can you implement this differently in order to do everything in one Query? ok
    public void enrollStudents(Long courseId, List<Long> studentsIds) {

        try (PreparedStatement ps = getInstance().getConn().prepareStatement(ENROLL_STUDENTS_Q)) {
            for (Long studentId : studentsIds) {
                ps.setLong(1, courseId);
                ps.setLong(2, studentId);
                ps.executeUpdate();
            }
        }
    }

    @Override
    @SneakyThrows
    public void deleteByIds(Set<Long> ids) {
        try (CallableStatement cs = getInstance().getConn().prepareCall("{call course_delete(?)}")) {
            for (Long id : ids) {
                cs.setLong(1, id);
                cs.executeUpdate();
            }
        }
    }
}
