package com.oceanview.resort.model.enums;

/**
 * Enum representing user roles in the system.
 */
public enum UserRole {
    ADMIN("Administrator"),
    RECEPTIONIST("Receptionist");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
