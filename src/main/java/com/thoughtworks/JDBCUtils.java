package com.thoughtworks;

import java.sql.*;

public class JDBCUtils {

  public static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
  public static final String URL = "jdbc:mysql://localhost:3306/student_sys";
  public static final String USER = "root";
  public static final String PASSWORD = "----------";
  public Connection connection;
  public Statement statement;

  public JDBCUtils() {
    try {
      Class.forName(CLASSNAME);
      this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void executeStatement(String sql) {
    try {
      this.statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet executeSelectStatement(String sql) {
    ResultSet resultSet = null;
    try {
      this.statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resultSet;
  }

  public void closeAll() {
    try {
      this.statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      this.connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeAll(ResultSet resultSet) {
    try {
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      this.statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      this.connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
