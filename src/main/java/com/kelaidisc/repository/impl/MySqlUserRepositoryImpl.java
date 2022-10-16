package com.kelaidisc.repository.impl;

import static com.kelaidisc.common.SqlUtils.*;
import static com.kelaidisc.common.SqlUtils.safeExecuteUpdate;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;
import com.kelaidisc.domain.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import lombok.SneakyThrows;

public abstract class MySqlUserRepositoryImpl<T extends User, ID extends Long> {
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

    
     List<T> findAll() {
        return getTsFromDatabase(FIND_ALL_Q, (ps) -> {/*No Consumer needed*/});
    }

    
     List<T> findAllByFirstNameLike(String firstName) {
        return getTsFromDatabase(FIND_ALL_BY_FIRST_NAME_Q, (ps) -> safeSetString(ps, 1, firstName));
    }

    
     List<T> findAllByLastNameLike(String lastName) {
        return getTsFromDatabase(FIND_ALL_BY_LAST_NAME_Q, ps -> safeSetString(ps, 1, lastName));
    }

    
     List<T> findAllByBirthday(LocalDate birthday) {
        return getTsFromDatabase(FIND_ALL_BY_BIRTHDAY_Q, ps -> safeSetDate(ps, 1, Date.valueOf(birthday)));
    }

    
     T findById(ID id) {
        return getTFromDatabase(FIND_BY_ID_Q, ps -> safeSetLong(ps, 1, id));
    }

    
     T findByEmail(String email) {
        return getTFromDatabase(FIND_BY_EMAIL_Q, ps -> safeSetString(ps, 1, email));

    }

    
     T findByPhone(String phone) {
        return getTFromDatabase(FIND_BY_PHONE_Q, ps -> safeSetString(ps, 1, phone));
    }

    
    @SneakyThrows
     T create(T T) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(CREATE_Q)) {
            safeSetAllFields(T, ps);
            safeExecuteUpdate(ps);
            return T;
        }
    }

    
    @SneakyThrows
     T update(T T) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(UPDATE_Q)) {
            safeSetAllFields(T, ps);
            ps.setLong(6, T.getId());
            safeExecuteUpdate(ps);
            return T;
        }
    }

    
    @SneakyThrows
     void deleteByIds(Set<ID> ids) {
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(DELETE_BY_IDS_Q)) {
            for (ID id : ids) {
                ps.setLong(1, id);
                safeExecuteUpdate(ps);
            }
        }
    }

    @SneakyThrows
     List<T> getTsFromDatabase(String query, Consumer<PreparedStatement> consumer) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
            consumer.accept(ps);
            fillTList(list, ps);
            return list;
        }
    }
    @SneakyThrows
     void fillTList(List<T> list, PreparedStatement ps) {
        var rs = ps.executeQuery();
        while (rs.next()) {
            list.add(convertResultToT(rs));
        }
        rs.close();
    }

    @SneakyThrows
     T getTFromDatabase(String query, Consumer<PreparedStatement> consumer) {
        T T = null;
        try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
            consumer.accept(ps);
            var rs = ps.executeQuery();
            while (rs.next()) {
                T = convertResultToT(rs);
            }
            rs.close();
            return T;
        }
    }

    @SneakyThrows
     void safeSetAllFields(T T, PreparedStatement ps) {
        ps.setString(1, T.getFirstName());
        ps.setString(2, T.getLastName());
        ps.setString(3, T.getEmail());
        ps.setString(4, T.getPhone());
        Date date = Date.valueOf(T.getBirthday());
        ps.setDate(5, date);
    }

    @SneakyThrows
     T convertResultToT(ResultSet rs) {
        T T = new T();
        T.setId(rs.getLong("id"));
        T.setFirstName(rs.getString("first_name"));
        T.setLastName(rs.getString("last_name"));
        T.setEmail(rs.getString("email"));
        T.setPhone(rs.getString("phone"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = rs.getString("birthday");
        LocalDate dob = LocalDate.parse(date, formatter);
        T.setBirthday(dob);
        return T;
    }
}
