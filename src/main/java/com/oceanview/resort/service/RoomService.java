package com.oceanview.resort.service;

import com.oceanview.resort.dao.RoomDAO;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.model.enums.RoomStatus;
import com.oceanview.resort.model.enums.RoomType;
import com.oceanview.resort.pattern.factory.RoomFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class for room management.
 * Uses Factory pattern for creating room objects.
 */
public class RoomService {

    private final RoomDAO roomDAO = new RoomDAO();

    /**
     * Create a new room using the Factory pattern.
     */
    public int createRoom(String roomNumber, RoomType roomType) throws SQLException {
        // Use Factory pattern to create room with default values
        Room room = RoomFactory.createRoom(roomNumber, roomType);
        return roomDAO.create(room);
    }

    /**
     * Create a new room with custom rate.
     */
    public int createRoom(String roomNumber, RoomType roomType, double customRate, 
                           int maxOccupancy, String description) throws SQLException {
        Room room = RoomFactory.createRoom(roomNumber, roomType, customRate);
        room.setMaxOccupancy(maxOccupancy);
        if (description != null && !description.trim().isEmpty()) {
            room.setDescription(description);
        }
        return roomDAO.create(room);
    }

    public Room findById(int id) throws SQLException {
        return roomDAO.findById(id);
    }

    public Room findByRoomNumber(String roomNumber) throws SQLException {
        return roomDAO.findByRoomNumber(roomNumber);
    }

    public List<Room> findAll() throws SQLException {
        return roomDAO.findAll();
    }

    public List<Room> findAvailableRooms(Date checkIn, Date checkOut) throws SQLException {
        return roomDAO.findAvailableRooms(checkIn, checkOut);
    }

    public List<Room> findByStatus(RoomStatus status) throws SQLException {
        return roomDAO.findByStatus(status);
    }

    public List<Room> findByType(RoomType type) throws SQLException {
        return roomDAO.findByType(type);
    }

    public boolean update(Room room) throws SQLException {
        return roomDAO.update(room);
    }

    public boolean updateStatus(int roomId, RoomStatus status) throws SQLException {
        return roomDAO.updateStatus(roomId, status);
    }

    public boolean delete(int roomId) throws SQLException {
        return roomDAO.delete(roomId);
    }

    public int getTotalCount() throws SQLException {
        return roomDAO.getTotalCount();
    }

    public int getCountByStatus(RoomStatus status) throws SQLException {
        return roomDAO.getCountByStatus(status);
    }
}
