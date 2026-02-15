package com.oceanview.resort.model.enums;

/**
 * Enum representing the types of rooms available at Ocean View Resort.
 */
public enum RoomType {
    STANDARD("Standard", 100.00, 2),
    DELUXE("Deluxe", 200.00, 3),
    SUITE("Suite", 350.00, 4);

    private final String displayName;
    private final double defaultRate;
    private final int defaultMaxOccupancy;

    RoomType(String displayName, double defaultRate, int defaultMaxOccupancy) {
        this.displayName = displayName;
        this.defaultRate = defaultRate;
        this.defaultMaxOccupancy = defaultMaxOccupancy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDefaultRate() {
        return defaultRate;
    }

    public int getDefaultMaxOccupancy() {
        return defaultMaxOccupancy;
    }
}
