package com.acuscoding.lab7.models;

import java.time.LocalDateTime;

public class Student {
    private int id;
    private String name;
    private String email;
    private int courseId; // Changed from String course to int courseId
    private String courseName; // Optional: for display purposes
    private LocalDateTime registrationDate;

    // Default constructor
    public Student() {}

    // Constructor with fields
    public Student(int id, String name, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courseId = courseId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + "', email='" +
                email + "', courseId=" + courseId + ", courseName='" + courseName + "'}";
    }
}