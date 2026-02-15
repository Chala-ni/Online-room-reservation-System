package com.oceanview.resort.model;

import java.sql.Timestamp;

/**
 * Represents an audit log entry for tracking system actions.
 */
public class AuditLog {
    private int id;
    private String action;
    private String entityType;
    private int entityId;
    private int performedBy;
    private String details;
    private Timestamp createdAt;

    // Joined fields
    private String performedByName;

    public AuditLog() {}

    public AuditLog(String action, String entityType, int entityId, int performedBy, String details) {
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.performedBy = performedBy;
        this.details = details;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public int getEntityId() { return entityId; }
    public void setEntityId(int entityId) { this.entityId = entityId; }

    public int getPerformedBy() { return performedBy; }
    public void setPerformedBy(int performedBy) { this.performedBy = performedBy; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getPerformedByName() { return performedByName; }
    public void setPerformedByName(String performedByName) { this.performedByName = performedByName; }

    @Override
    public String toString() {
        return "AuditLog{action='" + action + "', entity=" + entityType + 
               "#" + entityId + ", details='" + details + "'}";
    }
}
