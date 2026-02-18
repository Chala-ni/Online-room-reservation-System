package com.oceanview.resort.dao;

import com.oceanview.resort.model.AuditLog;
import com.oceanview.resort.pattern.singleton.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for AuditLog entity.
 * Handles all database operations related to audit trail logging.
 */
public class AuditLogDAO {

    public List<AuditLog> findAll() throws SQLException {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT a.*, u.full_name AS performed_by_name " +
                     "FROM audit_log a LEFT JOIN users u ON a.performed_by = u.id " +
                     "ORDER BY a.created_at DESC LIMIT 100";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logs.add(mapResultSetToAuditLog(rs));
            }
        }
        return logs;
    }

    public List<AuditLog> findByEntityType(String entityType) throws SQLException {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT a.*, u.full_name AS performed_by_name " +
                     "FROM audit_log a LEFT JOIN users u ON a.performed_by = u.id " +
                     "WHERE a.entity_type = ? ORDER BY a.created_at DESC LIMIT 50";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entityType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(mapResultSetToAuditLog(rs));
            }
        }
        return logs;
    }

    public int create(AuditLog log) throws SQLException {
        String sql = "INSERT INTO audit_log (action, entity_type, entity_id, performed_by, details) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, log.getAction());
            stmt.setString(2, log.getEntityType());
            stmt.setInt(3, log.getEntityId());
            stmt.setInt(4, log.getPerformedBy());
            stmt.setString(5, log.getDetails());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        return -1;
    }

    private AuditLog mapResultSetToAuditLog(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setId(rs.getInt("id"));
        log.setAction(rs.getString("action"));
        log.setEntityType(rs.getString("entity_type"));
        log.setEntityId(rs.getInt("entity_id"));
        log.setPerformedBy(rs.getInt("performed_by"));
        log.setDetails(rs.getString("details"));
        log.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            log.setPerformedByName(rs.getString("performed_by_name"));
        } catch (SQLException e) {
            // May not be present
        }
        return log;
    }
}
