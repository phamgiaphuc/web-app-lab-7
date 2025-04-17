package com.acuscoding.lab7.dao;

import com.acuscoding.lab7.models.Course;
import com.acuscoding.lab7.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCode(rs.getString("code"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setCredits(rs.getInt("credits"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving courses from database", e);
        }
        return courses;
    }

    // Get course by ID
    public Course getCourseById(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        Course course = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCode(rs.getString("code"));
                    course.setName(rs.getString("name"));
                    course.setDescription(rs.getString("description"));
                    course.setCredits(rs.getInt("credits"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving course with ID " + id, e);
        }

        return course;
    }

    // Add a new course
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (code, name, description, credits) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getCredits());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding course", e);
        }
    }

    // Update an existing course
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET code = ?, name = ?, description = ?, credits = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getCredits());
            pstmt.setInt(5, course.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course with ID " + course.getId(), e);
        }
    }

    // Delete a course
    public boolean deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting course with ID " + id, e);
        }
    }
}