package com.oceanview.resort.model;

import java.sql.Timestamp;

/**
 * Represents a hotel guest at Ocean View Resort.
 */
public class Guest {
    private int id;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private String nicPassport;
    private String nationality;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Guest() {}

    public Guest(int id, String name, String address, String contactNumber, 
                 String email, String nicPassport, String nationality) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.nicPassport = nicPassport;
        this.nationality = nationality;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNicPassport() { return nicPassport; }
    public void setNicPassport(String nicPassport) { this.nicPassport = nicPassport; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Guest{id=" + id + ", name='" + name + "', contact='" + contactNumber + 
               "', nic='" + nicPassport + "'}";
    }
}
