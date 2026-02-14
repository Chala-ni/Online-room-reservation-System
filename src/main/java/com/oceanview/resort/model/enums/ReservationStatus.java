package com.oceanview.resort.model.enums;

/**
 * Enum representing the status/lifecycle of a reservation.
 */
public enum ReservationStatus {
    CONFIRMED("Confirmed"),
    CHECKED_IN("Checked In"),
    CHECKED_OUT("Checked Out"),
    CANCELLED("Cancelled");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
