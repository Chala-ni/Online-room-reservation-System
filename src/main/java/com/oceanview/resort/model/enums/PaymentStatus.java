package com.oceanview.resort.model.enums;

/**
 * Enum representing the payment status of a bill.
 */
public enum PaymentStatus {
    PENDING("Pending"),
    PAID("Paid"),
    REFUNDED("Refunded");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
