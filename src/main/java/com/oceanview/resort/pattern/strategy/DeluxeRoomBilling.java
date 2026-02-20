package com.oceanview.resort.pattern.strategy;

/**
 * Deluxe Room Billing Strategy.
 * Applies base rate plus a 5% premium for deluxe amenities (minibar, ocean view).
 */
public class DeluxeRoomBilling implements BillingStrategy {

    private static final double DELUXE_PREMIUM = 0.05; // 5% premium

    @Override
    public double calculateSubtotal(int numNights, double ratePerNight) {
        double baseAmount = numNights * ratePerNight;
        return baseAmount + (baseAmount * DELUXE_PREMIUM);
    }
}
