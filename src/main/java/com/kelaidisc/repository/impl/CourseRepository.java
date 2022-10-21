package com.kelaidisc.repository.impl;

import com.kelaidisc.domain.Course;
import com.kelaidisc.repository.CrudRepository;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static com.kelaidisc.common.SqlUtils.safeSetString;

public class CourseRepository implements CrudRepository<Course> {

    /*TODO
    *  inject ProfessorService and Student Service*/

    private static final String FIND_ALL_BY_NAME_Q = "SELECT * FROM table_name where name like concat('%',?,'%')";
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

    public List<Course> findAllByNameLike(String name){
        return getAllFromDatabase(FIND_ALL_BY_NAME_Q.replaceFirst("table_name", getTableName()),
                ps -> safeSetString(ps, 1, name));
    }

    /*TODO
    create methods to assign course to professor and enroll student to course
    */
}
