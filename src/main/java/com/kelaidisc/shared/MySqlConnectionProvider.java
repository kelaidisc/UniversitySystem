package com.kelaidisc.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.SneakyThrows;

public class MySqlConnectionProvider {

  private static MySqlConnectionProvider instance;
  private Connection conn;
  private static final String URL = "jdbc:mysql://localhost:3306/university?allowMultiQueries=true";
  private static final String USER = "root";
  private static final String PASS = "root";

  private MySqlConnectionProvider() throws SQLException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.conn = DriverManager.getConnection(URL, USER, PASS);
      System.out.println("Connected");
    } catch (ClassNotFoundException e) {
      System.out.println("Something is wrong with the DB connection String " + e.getMessage());
    }
  }

  public Connection getConn() {
    return conn;
  }

  @SneakyThrows
  public static MySqlConnectionProvider getInstance() {
    if (instance == null) {
      instance = new MySqlConnectionProvider();
    } else if (instance.getConn().isClosed()) {
      instance = new MySqlConnectionProvider();
    }
    return instance;
  }
}
