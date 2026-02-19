package com.oceanview.resort.pattern.strategy;

/**
 * Suite Room Billing Strategy.
 * Applies base rate plus a 10% premium for suite amenities (jacuzzi, living area, panoramic view).
 * Stays of 5+ nights get a 5% loyalty discount.
 */
public class SuiteRoomBilling implements BillingStrategy {

    private static final double SUITE_PREMIUM = 0.10;     // 10% premium
    private static final double LOYALTY_DISCOUNT = 0.05;  // 5% discount for 5+ nights
    private static final int LOYALTY_THRESHOLD = 5;

    @Override
    public double calculateSubtotal(int numNights, double ratePerNight) {
        double baseAmount = numNights * ratePerNight;
        double premiumAmount = baseAmount + (baseAmount * SUITE_PREMIUM);

        // Apply loyalty discount for extended stays
        if (numNights >= LOYALTY_THRESHOLD) {
            premiumAmount -= (premiumAmount * LOYALTY_DISCOUNT);
        }

        return premiumAmount;
    }
}
