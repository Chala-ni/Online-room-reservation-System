package com.oceanview.resort.pattern.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Billing Strategy Pattern implementations.
 */
class BillingStrategyTest {

    // ============================================================
    // Standard Room Billing Tests
    // ============================================================
    @Test
    @DisplayName("StandardRoomBilling: should calculate base rate correctly")
    void standardBillingCalculation() {
        BillingStrategy strategy = new StandardRoomBilling();
        double ratePerNight = 100.0;
        int numNights = 3;

        double subtotal = strategy.calculateSubtotal(ratePerNight, numNights);

        assertEquals(300.0, subtotal, 0.01);
    }

    @Test
    @DisplayName("StandardRoomBilling: service charge should be 10%")
    void standardServiceCharge() {
        BillingStrategy strategy = new StandardRoomBilling();
        assertEquals(0.10, strategy.getServiceChargeRate(), 0.001);
    }

    @Test
    @DisplayName("StandardRoomBilling: tourism levy should be 2%")
    void standardTourismLevy() {
        BillingStrategy strategy = new StandardRoomBilling();
        assertEquals(0.02, strategy.getTourismLevyRate(), 0.001);
    }

    // ============================================================
    // Deluxe Room Billing Tests
    // ============================================================
    @Test
    @DisplayName("DeluxeRoomBilling: should add 5% premium")
    void deluxeBillingCalculation() {
        BillingStrategy strategy = new DeluxeRoomBilling();
        double ratePerNight = 200.0;
        int numNights = 3;

        double subtotal = strategy.calculateSubtotal(ratePerNight, numNights);

        // 200 * 3 * 1.05 = 630.0
        assertEquals(630.0, subtotal, 0.01);
    }

    // ============================================================
    // Suite Room Billing Tests
    // ============================================================
    @Test
    @DisplayName("SuiteRoomBilling: should add 10% premium for short stay")
    void suiteBillingShortStay() {
        BillingStrategy strategy = new SuiteRoomBilling();
        double ratePerNight = 350.0;
        int numNights = 3;

        double subtotal = strategy.calculateSubtotal(ratePerNight, numNights);

        // 350 * 3 * 1.10 = 1155.0
        assertEquals(1155.0, subtotal, 0.01);
    }

    @Test
    @DisplayName("SuiteRoomBilling: should give 5% loyalty discount for 5+ nights")
    void suiteBillingLoyaltyDiscount() {
        BillingStrategy strategy = new SuiteRoomBilling();
        double ratePerNight = 350.0;
        int numNights = 5;

        double subtotal = strategy.calculateSubtotal(ratePerNight, numNights);

        // 350 * 5 * 1.10 * 0.95 = 1828.75
        assertEquals(1828.75, subtotal, 0.01);
    }

    @Test
    @DisplayName("SuiteRoomBilling: exactly 5 nights should trigger loyalty discount")
    void suiteBillingExactlyFiveNights() {
        BillingStrategy strategy = new SuiteRoomBilling();
        double ratePerNight = 100.0;
        int numNights = 5;

        double subtotal = strategy.calculateSubtotal(ratePerNight, numNights);

        // 100 * 5 * 1.10 * 0.95 = 522.5
        assertEquals(522.5, subtotal, 0.01);
    }

    // ============================================================
    // Edge Cases
    // ============================================================
    @Test
    @DisplayName("Zero nights should return zero")
    void zeroNights() {
        BillingStrategy strategy = new StandardRoomBilling();
        assertEquals(0.0, strategy.calculateSubtotal(100.0, 0), 0.01);
    }

    @Test
    @DisplayName("Single night standard room calculation")
    void singleNight() {
        BillingStrategy strategy = new StandardRoomBilling();
        assertEquals(100.0, strategy.calculateSubtotal(100.0, 1), 0.01);
    }
}
