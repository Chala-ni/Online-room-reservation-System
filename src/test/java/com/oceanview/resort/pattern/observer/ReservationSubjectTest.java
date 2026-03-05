package com.oceanview.resort.pattern.observer;

import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.enums.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Observer Pattern implementation.
 */
class ReservationSubjectTest {

    private ReservationSubject subject;
    private TestObserver observer1;
    private TestObserver observer2;

    @BeforeEach
    void setUp() {
        subject = new ReservationSubject();
        observer1 = new TestObserver();
        observer2 = new TestObserver();
    }

    @Test
    @DisplayName("Should register observer successfully")
    void registerObserver() {
        subject.addObserver(observer1);
        Reservation r = createTestReservation();
        subject.notifyReservationCreated(r);
        assertTrue(observer1.createdCalled);
    }

    @Test
    @DisplayName("Should notify all registered observers on creation")
    void notifyAllOnCreate() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        Reservation r = createTestReservation();

        subject.notifyReservationCreated(r);

        assertTrue(observer1.createdCalled);
        assertTrue(observer2.createdCalled);
    }

    @Test
    @DisplayName("Should notify observers on status update")
    void notifyOnUpdate() {
        subject.addObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyReservationUpdated(r, "CONFIRMED", "CHECKED_IN");

        assertTrue(observer1.updatedCalled);
        assertEquals("CHECKED_IN", observer1.lastNewStatus);
    }

    @Test
    @DisplayName("Should notify observers on cancellation")
    void notifyOnCancel() {
        subject.addObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyReservationCancelled(r);

        assertTrue(observer1.cancelledCalled);
    }

    @Test
    @DisplayName("Should remove observer and stop notifications")
    void removeObserver() {
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.removeObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyReservationCreated(r);

        assertFalse(observer1.createdCalled);
        assertTrue(observer2.createdCalled);
    }

    @Test
    @DisplayName("Should handle empty observer list without errors")
    void emptyObserverList() {
        Reservation r = createTestReservation();
        assertDoesNotThrow(() -> subject.notifyReservationCreated(r));
        assertDoesNotThrow(() -> subject.notifyReservationUpdated(r, "CONFIRMED", "CHECKED_IN"));
        assertDoesNotThrow(() -> subject.notifyReservationCancelled(r));
    }

    // Helper methods
    private Reservation createTestReservation() {
        Reservation r = new Reservation();
        r.setId(1);
        r.setReservationNumber("RES-20250615-0001");
        r.setGuestId(1);
        r.setRoomId(1);
        r.setStatus(ReservationStatus.CONFIRMED);
        return r;
    }

    /**
     * Test observer that tracks which methods were called.
     */
    private static class TestObserver implements ReservationObserver {
        boolean createdCalled = false;
        boolean updatedCalled = false;
        boolean cancelledCalled = false;
        String lastNewStatus = null;

        @Override
        public void onReservationCreated(Reservation reservation) {
            createdCalled = true;
        }

        @Override
        public void onReservationUpdated(Reservation reservation, String oldStatus, String newStatus) {
            updatedCalled = true;
            lastNewStatus = newStatus;
        }

        @Override
        public void onReservationCancelled(Reservation reservation) {
            cancelledCalled = true;
        }
    }
}
