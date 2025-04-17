package com.acuscoding.lab7.controllers;

import com.acuscoding.lab7.dao.CourseDAO;
import com.acuscoding.lab7.models.Course;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CourseController", urlPatterns = {"/courses", "/course/*"})
public class CourseController extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String action = request.getParameter("action");

        if (pathInfo == null && action == null) {
            // List all courses
            listCourses(request, response);
        } else if (pathInfo != null && pathInfo.equals("/new")) {
            // Show form to add a new course
            showNewForm(request, response);
        } else if (pathInfo != null && pathInfo.equals("/edit")) {
            // Show form to edit an existing course
            showEditForm(request, response);
        } else if (pathInfo != null && pathInfo.equals("/delete")) {
            // Delete a course
            deleteCourse(request, response);
        } else if (pathInfo != null && pathInfo.equals("/view")) {
            // View a specific course
            viewCourse(request, response);
        } else {
            // Default - show all courses
            listCourses(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/add")) {
            // Add a new course
            addCourse(request, response);
        } else if (pathInfo != null && pathInfo.equals("/update")) {
            // Update an existing course
            updateCourse(request, response);
        } else {
            // Default - show all courses
            listCourses(request, response);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Course> courses = courseDAO.getAllCourses();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/course-list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error listing courses", e);
        }
    }

    private void viewCourse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.getCourseById(id);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/course-view.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/course-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseDAO.getCourseById(id);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/course-form.jsp").forward(request, response);
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int credits = Integer.parseInt(request.getParameter("credits"));

        Course newCourse = new Course();
        newCourse.setCode(code);
        newCourse.setName(name);
        newCourse.setDescription(description);
        newCourse.setCredits(credits);

        courseDAO.addCourse(newCourse);
        response.sendRedirect(request.getContextPath() + "/courses");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int credits = Integer.parseInt(request.getParameter("credits"));

        Course course = new Course();
        course.setId(id);
        course.setCode(code);
        course.setName(name);
        course.setDescription(description);
        course.setCredits(credits);

        courseDAO.updateCourse(course);
        response.sendRedirect(request.getContextPath() + "/courses");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        courseDAO.deleteCourse(id);
        response.sendRedirect(request.getContextPath() + "/courses");
    }
}