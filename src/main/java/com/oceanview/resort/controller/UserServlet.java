package com.oceanview.resort.controller;

import com.oceanview.resort.model.User;
import com.oceanview.resort.model.enums.UserRole;
import com.oceanview.resort.service.AuthService;
import com.oceanview.resort.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * User Management Servlet - admin-only operations for managing users.
 */
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Admin only
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Admin+access+required");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listUsers(request, response);
            } else if (pathInfo.equals("/new")) {
                showCreateForm(request, response);
            } else if (pathInfo.equals("/deactivate")) {
                deactivateUser(request, response);
            } else if (pathInfo.equals("/password")) {
                showPasswordForm(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Admin+access+required");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/new")) {
                createUser(request, response);
            } else if (pathInfo.equals("/password")) {
                resetPassword(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<User> users = authService.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("roles", UserRole.values());
        request.getRequestDispatcher("/WEB-INF/views/user/form.jsp").forward(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String roleStr = request.getParameter("role");

        // Validate
        StringBuilder errors = new StringBuilder();
        if (!ValidationUtil.isValidUsername(username)) errors.append("Username must be 3-50 alphanumeric characters. ");
        if (!ValidationUtil.isValidPassword(password)) errors.append("Password must be at least 6 characters. ");
        if (!ValidationUtil.isValidName(fullName)) errors.append("Full name is required. ");
        if (!ValidationUtil.isValidEmail(email)) errors.append("Valid email is required. ");
        if (roleStr == null) errors.append("Role is required. ");

        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            showCreateForm(request, response);
            return;
        }

        try {
            authService.createUser(username, password, UserRole.valueOf(roleStr), fullName, email);
            response.sendRedirect(request.getContextPath() + "/users?success=User+created+successfully");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            showCreateForm(request, response);
        }
    }

    private void deactivateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        if (id == currentUser.getId()) {
            response.sendRedirect(request.getContextPath() + "/users?error=Cannot+deactivate+your+own+account");
            return;
        }

        authService.deactivateUser(id);
        response.sendRedirect(request.getContextPath() + "/users?success=User+deactivated+successfully");
    }

    private void showPasswordForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/user/password.jsp").forward(request, response);
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String newPassword = request.getParameter("newPassword");

        if (!ValidationUtil.isValidPassword(newPassword)) {
            request.setAttribute("error", "Password must be at least 6 characters.");
            request.setAttribute("userId", userId);
            showPasswordForm(request, response);
            return;
        }

        authService.resetPassword(userId, newPassword);
        response.sendRedirect(request.getContextPath() + "/users?success=Password+reset+successfully");
    }

    private boolean isAdmin(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole");
        return "ADMIN".equals(role);
    }
}
