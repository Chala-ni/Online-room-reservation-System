package com.oceanview.resort.service;

import com.oceanview.resort.dao.UserDAO;
import com.oceanview.resort.model.User;
import com.oceanview.resort.util.PasswordHasher;

import java.sql.SQLException;

/**
 * Service class for authentication and user management.
 * Contains business logic for login, password management, and session handling.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Authenticate a user with username and plain-text password.
     *
     * @param username the username
     * @param password the plain-text password
     * @return the authenticated User object, or null if authentication fails
     */
    public User authenticate(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        String passwordHash = PasswordHasher.hash(password);
        return userDAO.authenticate(username.trim(), passwordHash);
    }

    /**
     * Create a new user account.
     */
    public int createUser(User user, String plainPassword) throws SQLException {
        user.setPassword(PasswordHasher.hash(plainPassword));
        return userDAO.create(user);
    }

    /**
     * Update a user's password.
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) throws SQLException {
        User user = userDAO.findById(userId);
        if (user == null) return false;

        // Verify old password
        if (!PasswordHasher.verify(oldPassword, user.getPassword())) {
            return false;
        }

        // Update with new password hash
        return userDAO.updatePassword(userId, PasswordHasher.hash(newPassword));
    }

    /**
     * Reset a user's password (admin function).
     */
    public boolean resetPassword(int userId, String newPassword) throws SQLException {
        return userDAO.updatePassword(userId, PasswordHasher.hash(newPassword));
    }
}
