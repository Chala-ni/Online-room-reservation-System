package com.oceanview.resort.pattern.factory;

import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.model.enums.RoomType;

/**
 * Factory Method Pattern - Creates Room objects based on room type.
 * 
 * Design Pattern: Factory Method (Creational)
 * - Encapsulates the complex logic of creating Room objects
 * - Sets default rate, max occupancy, and description based on type
 * - Adding a new room type only requires updating this factory
 * 
 * Benefits:
 * - Centralizes room creation logic
 * - Ensures consistent default values for each room type
 * - Follows Open/Closed Principle — extend by adding new types
 */
public class RoomFactory {

    /**
     * Creates a Room object with default values based on room type.
     *
     * @param roomNumber the room number (e.g., "101")
     * @param roomType the type of the room
     * @return a fully configured Room object
     */
    public static Room createRoom(String roomNumber, RoomType roomType) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setStatus(RoomStatus.AVAILABLE);

        switch (roomType) {
            case STANDARD:
                room.setRatePerNight(100.00);
                room.setMaxOccupancy(2);
                room.setDescription("Standard room with AC, WiFi, TV, and garden view");
                break;
            case DELUXE:
                room.setRatePerNight(200.00);
                room.setMaxOccupancy(3);
                room.setDescription("Deluxe room with ocean view, AC, WiFi, TV, minibar, and balcony");
                break;
            case SUITE:
                room.setRatePerNight(350.00);
                room.setMaxOccupancy(4);
                room.setDescription("Luxury suite with ocean panorama, jacuzzi, AC, WiFi, TV, minibar, and living area");
                break;
        }

        return room;
    }

    /**
     * Creates a Room object with a custom rate.
     *
     * @param roomNumber the room number
     * @param roomType the type of the room
     * @param customRate custom rate per night
     * @return a fully configured Room object
     */
    public static Room createRoom(String roomNumber, RoomType roomType, double customRate) {
        Room room = createRoom(roomNumber, roomType);
        room.setRatePerNight(customRate);
        return room;
    }
}
