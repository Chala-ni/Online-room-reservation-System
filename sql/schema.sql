-- ============================================================
-- Ocean View Resort - Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS ocean_view_resort;
USE ocean_view_resort;

-- ============================================================
-- Users Table (Staff authentication)
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'RECEPTIONIST') NOT NULL DEFAULT 'RECEPTIONIST',
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- Rooms Table
-- ============================================================
CREATE TABLE IF NOT EXISTS rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    room_type ENUM('STANDARD', 'DELUXE', 'SUITE') NOT NULL,
    rate_per_night DECIMAL(10, 2) NOT NULL,
    max_occupancy INT NOT NULL DEFAULT 2,
    description TEXT,
    status ENUM('AVAILABLE', 'OCCUPIED', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- Guests Table
-- ============================================================
CREATE TABLE IF NOT EXISTS guests (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    nic_passport VARCHAR(20) NOT NULL,
    nationality VARCHAR(50) DEFAULT 'Sri Lankan',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- Reservations Table
-- ============================================================
CREATE TABLE IF NOT EXISTS reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_number VARCHAR(20) NOT NULL UNIQUE,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    num_guests INT NOT NULL DEFAULT 1,
    special_requests TEXT,
    status ENUM('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (guest_id) REFERENCES guests(id) ON DELETE RESTRICT,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE RESTRICT,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

-- ============================================================
-- Bills Table
-- ============================================================
CREATE TABLE IF NOT EXISTS bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT NOT NULL UNIQUE,
    num_nights INT NOT NULL,
    rate_per_night DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    service_charge DECIMAL(10, 2) NOT NULL,
    tourism_levy DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE RESTRICT
);

-- ============================================================
-- Audit Log Table
-- ============================================================
CREATE TABLE IF NOT EXISTS audit_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id INT NOT NULL,
    performed_by INT,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (performed_by) REFERENCES users(id) ON DELETE SET NULL
);

-- ============================================================
-- Indexes for Performance
-- ============================================================
CREATE INDEX idx_reservations_guest ON reservations(guest_id);
CREATE INDEX idx_reservations_room ON reservations(room_id);
CREATE INDEX idx_reservations_dates ON reservations(check_in_date, check_out_date);
CREATE INDEX idx_reservations_status ON reservations(status);
CREATE INDEX idx_bills_reservation ON bills(reservation_id);
CREATE INDEX idx_audit_entity ON audit_log(entity_type, entity_id);
