package com.thoughtworks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

  public static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
  public static final String URL = "jdbc:mysql://localhost:3306/student_sys";
  public static final String USER = "root";
  public static final String PASSWORD = "----------";
  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public void save(List<Student> students) {
    students.forEach(this::save);
  }

  public void save(Student student) {
    try {
      Class.forName(CLASSNAME);
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement statement = connection.createStatement();
      String sql = String.format("INSERT INTO student_info VALUES ('%s', '%s', '%s', '%d', '%s', '%s');",
              student.getId(),
              student.getName(),
              student.getGender(),
              student.getAdmissionYear(),
              FORMAT.format(student.getBirthday()),
              student.getClassId());
      statement.executeUpdate(sql);
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Student> query() {
    List<Student> students = new ArrayList<>();
    try {
      Class.forName(CLASSNAME);
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement statement = connection.createStatement();
      String sql = "SELECT * FROM student_info";
      ResultSet result = statement.executeQuery(sql);
      while (result.next()) {
        Student student = new Student(
                result.getString("id"),
                result.getString("name"),
                result.getString("gender"),
                result.getInt("admitted_date"),
                result.getString("birthday"),
                result.getString("class"));
        students.add(student);
      }
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return students;
  }

  public List<Student> queryByClassId(String classId) {
    List<Student> students = new ArrayList<>();
    try {
      Class.forName(CLASSNAME);
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement statement = connection.createStatement();
      String sql = String.format("SELECT * FROM student_info WHERE class = '%s' ORDER BY id DESC;", classId);
      ResultSet result = statement.executeQuery(sql);
      while (result.next()) {
        Student student = new Student(
                result.getString("id"),
                result.getString("name"),
                result.getString("gender"),
                result.getInt("admitted_date"),
                result.getString("birthday"),
                result.getString("class"));
        students.add(student);
      }
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return students;
  }

  public void update(String id, Student student) {
    try {
      Class.forName(CLASSNAME);
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement statement = connection.createStatement();
      String sql = String.format("UPDATE student_info SET name = '%s' WHERE id = '%s';",
              student.getName(), id);
      statement.executeUpdate(sql);
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete(String id) {
    try {
      Class.forName(CLASSNAME);
      Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
      Statement statement = connection.createStatement();
      String sql = String.format("DELETE FROM student_info WHERE id = '%s';", id);
      statement.executeUpdate(sql);
      statement.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
