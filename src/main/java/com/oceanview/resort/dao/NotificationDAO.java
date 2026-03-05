package com.oceanview.resort.dao;

import com.oceanview.resort.model.Notification;
import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Notification entity.
 * Handles all database operations related to notifications.
 */
public class NotificationDAO {

    public List<Notification> findAll() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC LIMIT 50";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                notifications.add(mapResultSet(rs));
            }
        }
        return notifications;
    }

    public List<Notification> findUnread() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE is_read = FALSE ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                notifications.add(mapResultSet(rs));
            }
        }
        return notifications;
    }

    public int countUnread() throws SQLException {
        String sql = "SELECT COUNT(*) FROM notifications WHERE is_read = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int create(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (type, title, message, related_entity_id, is_read) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, notification.getType());
            stmt.setString(2, notification.getTitle());
            stmt.setString(3, notification.getMessage());
            stmt.setInt(4, notification.getRelatedEntityId());
            stmt.setBoolean(5, notification.isRead());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    public void markAsRead(int id) throws SQLException {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void markAllAsRead() throws SQLException {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE is_read = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    private Notification mapResultSet(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setId(rs.getInt("id"));
        n.setType(rs.getString("type"));
        n.setTitle(rs.getString("title"));
        n.setMessage(rs.getString("message"));
        n.setRelatedEntityId(rs.getInt("related_entity_id"));
        n.setRead(rs.getBoolean("is_read"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}
