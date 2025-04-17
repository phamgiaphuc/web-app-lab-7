package com.acuscoding.lab7.models;

public class Course {
    private int id;
    private String code;
    private String name;
    private String description;
    private int credits;

    public Course() {}

    public Course(int id, String code, String name, String description, int credits) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                '}';
    }
}
