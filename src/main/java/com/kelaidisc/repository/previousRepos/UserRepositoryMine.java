package com.kelaidisc.repository.previousRepos;

import static com.kelaidisc.common.Constants.DATE_FORMATTER;
import static com.kelaidisc.common.SqlUtils.safeSetDate;
import static com.kelaidisc.common.SqlUtils.safeSetString;

import com.kelaidisc.domain.User;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import lombok.SneakyThrows;

public interface UserRepositoryMine<T extends User> extends CrudRepositoryMine<T> {

  String FIND_ALL_BY_FIRST_NAME_Q = "SELECT * FROM table_name where first_name like concat('%',?,'%')";
  String FIND_ALL_BY_LAST_NAME_Q = "SELECT * FROM table_name where last_name like concat('%',?,'%')";
  String FIND_BY_EMAIL_Q = "SELECT * FROM table_name where email=?";
  String FIND_BY_PHONE_Q = "SELECT * FROM table_name where phone=?";
  String FIND_ALL_BY_BIRTHDAY_Q = "SELECT * FROM table_name where birthday=?";


  @Override
  default List<String> getFieldNames() {
    return List.of(
        "first_name", "last_name", "email", "phone", "birthday"
    );
  }

  @SneakyThrows
  default void setUserFieldsFromResultSet(T user, ResultSet rs) {
    user.setId(rs.getLong("id"));
    user.setFirstName(rs.getString("first_name"));
    user.setLastName(rs.getString("last_name"));
    user.setEmail(rs.getString("email"));
    user.setPhone(rs.getString("phone"));
    String date = rs.getString("birthday");
    user.setBirthday(LocalDate.parse(date, DATE_FORMATTER));
  }

  @Override
  @SneakyThrows
  default void setAllFieldsFromEntity(T entity, PreparedStatement ps) {
    ps.setString(1, entity.getFirstName());
    ps.setString(2, entity.getLastName());
    ps.setString(3, entity.getEmail());
    ps.setString(4, entity.getPhone());
    ps.setDate(5, Date.valueOf(entity.getBirthday()));
  }

  default List<T> findAllByFirstNameLike(String firstName) {
    return getAllFromDatabase(FIND_ALL_BY_FIRST_NAME_Q.replaceFirst("table_name", getTableName()),
        (ps) -> safeSetString(ps, 1, firstName));
  }

  default List<T> findAllByLastNameLike(String lastName) {
    return getAllFromDatabase(FIND_ALL_BY_LAST_NAME_Q.replaceFirst("table_name", getTableName()),
        ps -> safeSetString(ps, 1, lastName));
  }

  default List<T> findAllByBirthday(LocalDate birthday) {
    return getAllFromDatabase(FIND_ALL_BY_BIRTHDAY_Q.replaceFirst("table_name", getTableName()),
        ps -> safeSetDate(ps, 1, Date.valueOf(birthday)));
  }

  default T findByEmail(String email) {
    return getOneFromDatabase(
        FIND_BY_EMAIL_Q.replaceFirst("table_name", getTableName()), ps -> safeSetString(ps, 1, email));
  }

  default T findByPhone(String phone) {
    return getOneFromDatabase(
        FIND_BY_PHONE_Q.replaceFirst("table_name", getTableName()), ps -> safeSetString(ps, 1, phone));
  }

}
