package com.acuscoding.lab7.dao;

import com.acuscoding.lab7.models.Student;
import com.acuscoding.lab7.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    // Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.email, s.course_id, c.name AS course_name, s.registration_date " +
                "FROM students s JOIN courses c ON s.course_id = c.id";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setCourseId(rs.getInt("course_id"));
                student.setCourseName(rs.getString("course_name"));
                if (rs.getTimestamp("registration_date") != null) {
                    student.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
                }
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving students from database", e);
        }
        return students;
    }

    // Get student by ID
    public Student getStudentById(int id) {
        String sql = "SELECT s.id, s.name, s.email, s.course_id, c.name AS course_name, s.registration_date " +
                "FROM students s JOIN courses c ON s.course_id = c.id WHERE s.id = ?";
        Student student = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setCourseId(rs.getInt("course_id"));
                    student.setCourseName(rs.getString("course_name"));
                    if (rs.getTimestamp("registration_date") != null) {
                        student.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving student with ID " + id, e);
        }
        return student;
    }

    // Add a new student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, course_id) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getCourseId()); // Use course_id

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding student", e);
        }
    }

    // Update an existing student
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, course_id = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getCourseId()); // Use course_id
            pstmt.setInt(4, student.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student with ID " + student.getId(), e);
        }
    }

    // Delete a student
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student with ID " + id, e);
        }
    }
}