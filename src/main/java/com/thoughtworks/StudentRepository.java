package com.thoughtworks;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public void save(List<Student> students) {
    students.forEach(this::save);
  }

  public void save(Student student) {
    String sql = String.format("INSERT INTO student_info VALUES ('%s', '%s', '%s', '%d', '%s', '%s');",
            student.getId(),
            student.getName(),
            student.getGender(),
            student.getAdmissionYear(),
            FORMAT.format(student.getBirthday()),
            student.getClassId());

    JDBCUtils utils = new JDBCUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }

  public List<Student> query() throws SQLException {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT * FROM student_info";
    JDBCUtils utils = new JDBCUtils();
    ResultSet result = utils.executeSelectStatement(sql);

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

    utils.closeAll(result);
    return students;
  }

  public List<Student> queryByClassId(String classId) throws SQLException {
    List<Student> students = new ArrayList<>();
    String sql = String.format("SELECT * FROM student_info WHERE class = '%s' ORDER BY id DESC;", classId);
    JDBCUtils utils = new JDBCUtils();
    ResultSet result = utils.executeSelectStatement(sql);

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

    utils.closeAll(result);
    return students;
  }

  public void update(String id, Student student) {
    String sql = String.format("UPDATE student_info SET name = '%s' WHERE id = '%s';",
            student.getName(), id);

    JDBCUtils utils = new JDBCUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }

  public void delete(String id) {
    String sql = String.format("DELETE FROM student_info WHERE id = '%s';", id);

    JDBCUtils utils = new JDBCUtils();
    utils.executeStatement(sql);
    utils.closeAll();
  }
}
