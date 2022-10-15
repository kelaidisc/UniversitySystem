package com.kelaidisc.repository.impl;

import static com.kelaidisc.common.SqlUtils.*;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;

import com.kelaidisc.domain.Student;
import com.kelaidisc.repository.StudentRepository;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import lombok.SneakyThrows;

public class MySqlStudentRepositoryImpl implements StudentRepository {

    private static final String FIND_ALL_Q = "SELECT * FROM university.student";
    private static final String FIND_ALL_BY_FIRST_NAME_Q =
            "SELECT * FROM university.student where first_name like concat('%',?,'%')";
    private static final String FIND_ALL_BY_LAST_NAME_Q =
            "SELECT * FROM university.student where last_name like concat('%',?,'%')";

    private static final String FIND_ALL_BY_BIRTHDAY_Q = "SELECT * FROM university.student where birthday=?";

    private static final String FIND_BY_ID_Q = "SELECT * FROM university.student where id=?";
    private static final String FIND_BY_EMAIL_Q = "SELECT * FROM university.student where email=?";
    private static final String FIND_BY_PHONE_Q = "SELECT * FROM university.student where phone=?";
    private static final String CREATE_Q = """
            INSERT INTO university.student
            (first_name, last_name, email, phone, birthday)
            VALUES(?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_Q = """
            UPDATE university.student
            SET first_name=?, last_name=?, email=?, phone=?, birthday=?
            WHERE id=?""";

    private static final String DELETE_BY_IDS_Q = "DELETE FROM university.student\n" +
            "WHERE id in (?)";

    @Override
    public List<Student> findAll() {
        return getStudentsFromDatabase(FIND_ALL_Q, (ps) -> {/*No Consumer arg*/});
    }

    @Override
    public List<Student> findAllByFirstNameLike(String firstName) {
        return getStudentsFromDatabase(FIND_ALL_BY_FIRST_NAME_Q, (ps) -> safeSetString(ps, 1, firstName));
    }

    @Override
    public List<Student> findAllByLastNameLike(String lastName) {
       return getStudentsFromDatabase(FIND_ALL_BY_LAST_NAME_Q, (ps) -> safeSetString(ps, 1, lastName));
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
       return getStudentsFromDatabase(FIND_ALL_BY_BIRTHDAY_Q, (ps) -> safeSetDate(ps, 1, Date.valueOf(birthday)));
    }

    @Override
    public Student findById(Long id) {
        return getStudentFromDatabase(FIND_BY_ID_Q, (ps) -> safeSetLong(ps, 1, id));
    }


    @Override
    public Student findByEmail(String email) {
        return getStudentFromDatabase(FIND_BY_EMAIL_Q, (ps) -> safeSetString(ps, 1, email));
    }

    @Override
    public Student findByPhone(String phone) {
        return getStudentFromDatabase(FIND_BY_PHONE_Q, (ps) -> safeSetString(ps, 1, phone));
    }

    @Override
    @SneakyThrows
    public Student create(Student student) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(CREATE_Q)) {
            safeSetAllFields(student, ps);
            safeExecuteUpdate(ps);
            return student;
        }
    }

    @Override
    @SneakyThrows
    public Student update(Student student) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(UPDATE_Q)) {
            safeSetAllFields(student, ps);
            ps.setLong(6, student.getId());
            safeExecuteUpdate(ps);
            return student;
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
    public Student getStudentFromDatabase(String query,Consumer<PreparedStatement> consumer){
        Student student = null;
        try(PreparedStatement ps = getInstance().getConn().prepareStatement(query)){
            consumer.accept(ps);
            var rs = ps.executeQuery();
            while (rs.next()){
                student = convertResultToStudent(rs);
            }
            rs.close();
            return student;
        }
    }

    @SneakyThrows
    private static void safeSetAllFields(Student student, PreparedStatement ps) {
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getLastName());
        ps.setString(3, student.getEmail());
        ps.setString(4, student.getPhone());
        Date date = Date.valueOf(student.getBirthday());
        ps.setDate(5, date);
    }
    private void fillStudentList(List<Student> list, PreparedStatement ps) throws SQLException {
        var rs = ps.executeQuery();
        while (rs.next()) {
            list.add(convertResultToStudent(rs));
        }
        rs.close();
    }

    @SneakyThrows
    private List<Student> getStudentsFromDatabase(String query, Consumer<PreparedStatement> consumer) {
        List<Student> list = new ArrayList<>();
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
            consumer.accept(ps);
            fillStudentList(list, ps);
            return list;
        }
    }
    @SneakyThrows
    private Student convertResultToStudent(ResultSet rs) {
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
        return student;
    }
}