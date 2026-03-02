package com.oceanview.resort.model;

import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.model.enums.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Room model.
 */
class RoomTest {

    @Test
    @DisplayName("Setting and getting room fields")
    void setGetFields() {
        Room room = new Room();
        room.setId(1);
        room.setRoomNumber("101");
        room.setRoomType(RoomType.STANDARD);
        room.setRatePerNight(100.0);
        room.setMaxOccupancy(2);
        room.setDescription("Standard room");
        room.setStatus(RoomStatus.AVAILABLE);

        assertEquals(1, room.getId());
        assertEquals("101", room.getRoomNumber());
        assertEquals(RoomType.STANDARD, room.getRoomType());
        assertEquals(100.0, room.getRatePerNight());
        assertEquals(2, room.getMaxOccupancy());
        assertEquals("Standard room", room.getDescription());
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());
    }

    @Test
    @DisplayName("Room type enum should have correct default rates")
    void roomTypeDefaultRates() {
        assertEquals(100.0, RoomType.STANDARD.getDefaultRate());
        assertEquals(200.0, RoomType.DELUXE.getDefaultRate());
        assertEquals(350.0, RoomType.SUITE.getDefaultRate());
    }

    @Test
    @DisplayName("Room type enum should have correct default max occupancy")
    void roomTypeDefaultMaxOccupancy() {
        assertEquals(2, RoomType.STANDARD.getDefaultMaxOccupancy());
        assertEquals(3, RoomType.DELUXE.getDefaultMaxOccupancy());
        assertEquals(4, RoomType.SUITE.getDefaultMaxOccupancy());
    }

    @Test
    @DisplayName("Room status enum values")
    void roomStatusValues() {
        RoomStatus[] statuses = RoomStatus.values();
        assertEquals(3, statuses.length);
        assertEquals(RoomStatus.AVAILABLE, RoomStatus.valueOf("AVAILABLE"));
        assertEquals(RoomStatus.OCCUPIED, RoomStatus.valueOf("OCCUPIED"));
        assertEquals(RoomStatus.MAINTENANCE, RoomStatus.valueOf("MAINTENANCE"));
    }
}
