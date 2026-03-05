package com.oceanview.resort.service;

import com.oceanview.resort.dao.AuditLogDAO;
import com.oceanview.resort.dao.UserDAO;
import com.oceanview.resort.model.AuditLog;
import com.oceanview.resort.model.User;
import com.oceanview.resort.util.PasswordHasher;

import java.sql.SQLException;

/**
 * Service class for authentication and user management.
 * Contains business logic for login, password management, and session handling.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

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
    public boolean resetPassword(int userId, String newPassword, int performedBy) throws SQLException {
        User user = userDAO.findById(userId);
        boolean result = userDAO.updatePassword(userId, PasswordHasher.hash(newPassword));
        if (result && user != null) {
            logAudit("PASSWORD_RESET", "USER", userId, performedBy, "Password reset for user '" + user.getUsername() + "'");
        }
        return result;
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
                           String fullName, String email, int performedBy) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordHasher.hash(password));
        user.setRole(role);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setActive(true);
        int id = userDAO.create(user);
        
        // Audit log
        logAudit("CREATE", "USER", id, performedBy, "User '" + username + "' created with role " + role);
        return id;
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
    public boolean deactivateUser(int userId, int performedBy) throws SQLException {
        User user = userDAO.findById(userId);
        boolean result = userDAO.deactivate(userId);
        if (result && user != null) {
            logAudit("DEACTIVATE", "USER", userId, performedBy, "User '" + user.getUsername() + "' deactivated");
        }
        return result;
    }

    /**
     * Activate a previously deactivated user account.
     */
    public boolean activateUser(int userId, int performedBy) throws SQLException {
        User user = userDAO.findById(userId);
        boolean result = userDAO.activate(userId);
        if (result && user != null) {
            logAudit("ACTIVATE", "USER", userId, performedBy, "User '" + user.getUsername() + "' activated");
        }
        return result;
    }

    /**
     * Log an audit entry.
     */
    private void logAudit(String action, String entityType, int entityId, int performedBy, String details) {
        try {
            AuditLog log = new AuditLog(action, entityType, entityId, performedBy, details);
            auditLogDAO.create(log);
        } catch (Exception e) {
            System.err.println("Failed to log audit: " + e.getMessage());
        }
    }
}
