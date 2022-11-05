package com.kelaidisc.repository.previousRepos;

import static com.kelaidisc.common.SqlUtils.safeSetLong;
import static com.kelaidisc.shared.MySqlConnectionProvider.getInstance;
import static java.lang.String.format;

import com.kelaidisc.domain.BaseEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

/*
  In order to make this easier, we will support only Long (Auto Increment) for IDs
 */
public interface CrudRepositoryMine<T extends BaseEntity> {

  String FIND_ALL_Q = "SELECT * FROM %s";
  String FIND_BY_ID_Q = "SELECT * FROM %s where id=?";
  String CREATE_Q = "INSERT INTO %s (%s) VALUES(%s)";
  String UPDATE_Q = "UPDATE %s SET %s WHERE id=?";
  String DELETE_BY_IDS_Q = "DELETE FROM %s WHERE id in (?)";

  String getTableName();

  List<String> getFieldNames();

  T convertResultToEntity(ResultSet rs);

  void setAllFieldsFromEntity(T entity, PreparedStatement ps);

  default List<T> findAll() {
    return getAllFromDatabase(format(FIND_ALL_Q, getTableName()), (ps) -> {/*No Consumer needed*/});
  }

  default T findById(Long id) {
    return getOneFromDatabase(format(FIND_BY_ID_Q, getTableName()), ps -> safeSetLong(ps, 1, id));
  }

  @SneakyThrows
  default T create(T entity) {
    var fieldNames = String.join(",", getFieldNames());
    var questionMarks = getFieldNames()
        .stream()
        .map(e -> "?")
        .collect(Collectors.joining(","));

    try (PreparedStatement preparedStatement = getInstance().getConn().prepareStatement(
        format(CREATE_Q, getTableName(), fieldNames, questionMarks))
    ) {
      setAllFieldsFromEntity(entity, preparedStatement);
      preparedStatement.executeUpdate();
      return entity;
    }
  }


  @SneakyThrows
  default T update(T entity) {
    var fieldNames = getFieldNames()
        .stream()
        .map(e -> e + "=?")
        .collect(Collectors.joining(","));

    try (PreparedStatement preparedStatement = getInstance().getConn().prepareStatement(
        format(UPDATE_Q, getTableName(), fieldNames))
    ) {
      setAllFieldsFromEntity(entity, preparedStatement);
      preparedStatement.setLong(getFieldNames().size() + 1, entity.getId());
      preparedStatement.executeUpdate();
      return entity;
    }
  }

  @SneakyThrows
  default void deleteByIds(Set<Long> ids) {
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(format(DELETE_BY_IDS_Q, getTableName()))) {
      for (Long id : ids) {
        ps.setLong(1, id);
        ps.executeUpdate();
      }
    }
  }

  @SneakyThrows
  default T getOneFromDatabase(String query, Consumer<PreparedStatement> consumer) {
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
      consumer.accept(ps);
      try (var rs = ps.executeQuery()) {
        if (rs.next()) {
          return convertResultToEntity(rs);
        }
      }
    }
    return null;
  }

  @SneakyThrows
  default List<T> getAllFromDatabase(String query, Consumer<PreparedStatement> consumer) {
    var list = new ArrayList<T>();
    try (PreparedStatement ps = getInstance().getConn().prepareStatement(query)) {
      consumer.accept(ps);
      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(convertResultToEntity(rs));
        }
      }
    }
    return list;
  }


}
