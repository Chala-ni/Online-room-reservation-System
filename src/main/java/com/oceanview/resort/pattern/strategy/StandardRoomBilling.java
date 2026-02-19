package com.oceanview.resort.pattern.strategy;

/**
 * Standard Room Billing Strategy.
 * Applies base rate with no additional charges.
 */
public class StandardRoomBilling implements BillingStrategy {

    @Override
    public double calculateSubtotal(int numNights, double ratePerNight) {
        return numNights * ratePerNight;
    }
}
