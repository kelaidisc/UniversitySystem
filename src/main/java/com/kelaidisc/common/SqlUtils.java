package com.kelaidisc.common;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlUtils {

  @SneakyThrows
  public static void safeSetString(PreparedStatement preparedStatement, int index, String stringToSet) {
    preparedStatement.setString(index, stringToSet);
  }

  @SneakyThrows
  public static void safeSetDate(PreparedStatement preparedStatement, int index, Date dateToSet) {
    preparedStatement.setDate(index, dateToSet);
  }

  @SneakyThrows
  public static void safeSetLong(PreparedStatement preparedStatement, int index, Long longToSet) {
    preparedStatement.setLong(index, longToSet);
  }
}
