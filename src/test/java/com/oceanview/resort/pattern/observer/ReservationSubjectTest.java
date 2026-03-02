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
        subject.registerObserver(observer1);
        Reservation r = createTestReservation();
        subject.notifyCreated(r);
        assertTrue(observer1.createdCalled);
    }

    @Test
    @DisplayName("Should notify all registered observers on creation")
    void notifyAllOnCreate() {
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);
        Reservation r = createTestReservation();

        subject.notifyCreated(r);

        assertTrue(observer1.createdCalled);
        assertTrue(observer2.createdCalled);
    }

    @Test
    @DisplayName("Should notify observers on status update")
    void notifyOnUpdate() {
        subject.registerObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyUpdated(r, ReservationStatus.CHECKED_IN);

        assertTrue(observer1.updatedCalled);
        assertEquals(ReservationStatus.CHECKED_IN, observer1.lastNewStatus);
    }

    @Test
    @DisplayName("Should notify observers on cancellation")
    void notifyOnCancel() {
        subject.registerObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyCancelled(r);

        assertTrue(observer1.cancelledCalled);
    }

    @Test
    @DisplayName("Should remove observer and stop notifications")
    void removeObserver() {
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);
        subject.removeObserver(observer1);
        Reservation r = createTestReservation();

        subject.notifyCreated(r);

        assertFalse(observer1.createdCalled);
        assertTrue(observer2.createdCalled);
    }

    @Test
    @DisplayName("Should handle empty observer list without errors")
    void emptyObserverList() {
        Reservation r = createTestReservation();
        assertDoesNotThrow(() -> subject.notifyCreated(r));
        assertDoesNotThrow(() -> subject.notifyUpdated(r, ReservationStatus.CHECKED_IN));
        assertDoesNotThrow(() -> subject.notifyCancelled(r));
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
        ReservationStatus lastNewStatus = null;

        @Override
        public void onReservationCreated(Reservation reservation) {
            createdCalled = true;
        }

        @Override
        public void onReservationUpdated(Reservation reservation, ReservationStatus newStatus) {
            updatedCalled = true;
            lastNewStatus = newStatus;
        }

        @Override
        public void onReservationCancelled(Reservation reservation) {
            cancelledCalled = true;
        }
    }
}
