package com.oceanview.resort.pattern.factory;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.model.enums.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RoomFactory (Factory Pattern).
 */
class RoomFactoryTest {

    @Test
    @DisplayName("Should create Standard room with correct defaults")
    void createStandardRoom() {
        Room room = RoomFactory.createRoom("101", RoomType.STANDARD);

        assertNotNull(room);
        assertEquals("101", room.getRoomNumber());
        assertEquals(RoomType.STANDARD, room.getRoomType());
        assertEquals(100.00, room.getRatePerNight());
        assertEquals(2, room.getMaxOccupancy());
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());
        assertNotNull(room.getDescription());
    }

    @Test
    @DisplayName("Should create Deluxe room with correct defaults")
    void createDeluxeRoom() {
        Room room = RoomFactory.createRoom("201", RoomType.DELUXE);

        assertNotNull(room);
        assertEquals("201", room.getRoomNumber());
        assertEquals(RoomType.DELUXE, room.getRoomType());
        assertEquals(200.00, room.getRatePerNight());
        assertEquals(3, room.getMaxOccupancy());
    }

    @Test
    @DisplayName("Should create Suite room with correct defaults")
    void createSuiteRoom() {
        Room room = RoomFactory.createRoom("301", RoomType.SUITE);

        assertNotNull(room);
        assertEquals("301", room.getRoomNumber());
        assertEquals(RoomType.SUITE, room.getRoomType());
        assertEquals(350.00, room.getRatePerNight());
        assertEquals(4, room.getMaxOccupancy());
    }

    @Test
    @DisplayName("All created rooms should have AVAILABLE status")
    void defaultAvailableStatus() {
        for (RoomType type : RoomType.values()) {
            Room room = RoomFactory.createRoom("100", type);
            assertEquals(RoomStatus.AVAILABLE, room.getStatus());
        }
    }
}
