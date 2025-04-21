package com.acuscoding.lab7.controllers;

import com.acuscoding.lab7.dao.CourseDAO;
import com.acuscoding.lab7.dao.StudentDAO;
import com.acuscoding.lab7.models.Course;
import com.acuscoding.lab7.models.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentController", urlPatterns = {"/students", "/student/*"})
public class StudentController extends HttpServlet {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String action = request.getParameter("action");

        if (pathInfo == null && action == null) {
            listStudents(request, response);
        } else if (pathInfo != null && pathInfo.equals("/new")) {
            showNewForm(request, response);
        } else if (pathInfo != null && pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo != null && pathInfo.equals("/delete")) {
            deleteStudent(request, response);
        } else if (pathInfo != null && pathInfo.equals("/view")) {
            viewStudent(request, response);
        } else {
            listStudents(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/add")) {
            // Add a new student
            addStudent(request, response);
        } else if (pathInfo != null && pathInfo.equals("/update")) {
            // Update an existing student
            updateStudent(request, response);
        } else {
            // Default - show all students
            listStudents(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Student> students = studentDAO.getAllStudents();
            request.setAttribute("students", students);
            request.getRequestDispatcher("/student-list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error listing students", e);
        }
    }

    private void viewStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/student-view.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        System.out.println(student);
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("student", student);
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setEmail(email);
        newStudent.setCourseId(courseId);
        studentDAO.addStudent(newStudent);
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int courseId = Integer.parseInt(request.getParameter("courseId")); // Changed to courseId

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setCourseId(courseId); // Set courseId (int)

        studentDAO.updateStudent(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect(request.getContextPath() + "/students");
    }
}