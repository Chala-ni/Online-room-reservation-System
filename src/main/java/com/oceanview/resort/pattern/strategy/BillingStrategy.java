package com.oceanview.resort.pattern.strategy;

/**
 * Strategy Pattern Interface - Billing calculation strategy.
 * 
 * Design Pattern: Strategy (Behavioral)
 * - Defines a family of billing algorithms (one per room type)
 * - Makes algorithms interchangeable without modifying client code
 * - New pricing strategies can be added without changing existing code
 * 
 * Benefits:
 * - Open/Closed Principle: extend pricing without modifying existing code
 * - Single Responsibility: each strategy handles its own pricing logic
 * - Easy to add seasonal pricing, promotional rates, etc.
 */
public interface BillingStrategy {

    /**
     * Calculate the subtotal (before taxes) for a stay.
     *
     * @param numNights number of nights
     * @param ratePerNight rate per night for the room
     * @return the subtotal amount
     */
    double calculateSubtotal(int numNights, double ratePerNight);

    /**
     * Get the service charge rate (percentage).
     * Default is 10% for all room types.
     */
    default double getServiceChargeRate() {
        return 0.10; // 10%
    }

    /**
     * Get the tourism development levy rate (percentage).
     * Default is 2% for all room types.
     */
    default double getTourismLevyRate() {
        return 0.02; // 2%
    }

    /**
     * Calculate total amount including all taxes.
     */
    default double calculateTotal(int numNights, double ratePerNight) {
        double subtotal = calculateSubtotal(numNights, ratePerNight);
        double serviceCharge = subtotal * getServiceChargeRate();
        double tourismLevy = subtotal * getTourismLevyRate();
        return subtotal + serviceCharge + tourismLevy;
    }
}
