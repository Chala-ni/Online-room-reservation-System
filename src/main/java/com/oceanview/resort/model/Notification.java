package com.oceanview.resort.model;

import java.sql.Timestamp;

/**
 * Represents a notification entry for tracking system notifications.
 */
public class Notification {
    private int id;
    private String type;           // RESERVATION_CREATED, STATUS_CHANGE, CANCELLATION
    private String title;
    private String message;
    private int relatedEntityId;   // reservation_id
    private boolean isRead;
    private Timestamp createdAt;

    public Notification() {}

    public Notification(String type, String title, String message, int relatedEntityId) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.relatedEntityId = relatedEntityId;
        this.isRead = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(int relatedEntityId) { this.relatedEntityId = relatedEntityId; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getIconClass() {
        return switch (type) {
            case "RESERVATION_CREATED" -> "bi-calendar-plus text-success";
            case "STATUS_CHANGE" -> "bi-arrow-repeat text-warning";
            case "CANCELLATION" -> "bi-x-circle text-danger";
            default -> "bi-bell text-info";
        };
    }
}
