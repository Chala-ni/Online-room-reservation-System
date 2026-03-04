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

    /**
     * Find all users.
     */
    public java.util.List<User> findAllUsers() throws SQLException {
        return userDAO.findAll();
    }

    /**
     * Create a new user with individual parameters.
     */
    public int createUser(String username, String password, com.oceanview.resort.model.enums.UserRole role,
                           String fullName, String email) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordHasher.hash(password));
        user.setRole(role);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setActive(true);
        return userDAO.create(user);
    }

    /**
     * Find a user by username.
     */
    public User findByUsername(String username) throws SQLException {
        return userDAO.findByUsername(username);
    }

    /**
     * Find a user by email.
     */
    public User findByEmail(String email) throws SQLException {
        return userDAO.findByEmail(email);
    }

    /**
     * Deactivate a user account.
     */
    public boolean deactivateUser(int userId) throws SQLException {
        return userDAO.deactivate(userId);
    }

    /**
     * Activate a previously deactivated user account.
     */
    public boolean activateUser(int userId) throws SQLException {
        return userDAO.activate(userId);
    }
}
