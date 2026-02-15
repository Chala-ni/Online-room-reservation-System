package com.oceanview.resort.model;

import com.oceanview.resort.model.enums.RoomType;
import com.oceanview.resort.model.enums.RoomStatus;
import java.sql.Timestamp;

/**
 * Represents a hotel room at Ocean View Resort.
 */
public class Room {
    private int id;
    private String roomNumber;
    private RoomType roomType;
    private double ratePerNight;
    private int maxOccupancy;
    private String description;
    private RoomStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Room() {}

    public Room(int id, String roomNumber, RoomType roomType, double ratePerNight, 
                int maxOccupancy, String description, RoomStatus status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.ratePerNight = ratePerNight;
        this.maxOccupancy = maxOccupancy;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public double getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(double ratePerNight) { this.ratePerNight = ratePerNight; }

    public int getMaxOccupancy() { return maxOccupancy; }
    public void setMaxOccupancy(int maxOccupancy) { this.maxOccupancy = maxOccupancy; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Room{id=" + id + ", roomNumber='" + roomNumber + "', type=" + roomType + 
               ", rate=" + ratePerNight + ", status=" + status + "}";
    }
}
