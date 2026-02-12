-- ============================================================
-- Ocean View Resort - Seed Data
-- ============================================================

USE ocean_view_resort;

-- ============================================================
-- Default Users
-- Passwords are SHA-256 hashed
-- admin123     -> 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- recep123     -> 5e2b4d823cc8bd8c16c4b720e94192f38ce09b66eae135f6ea3fee1e1a5b0594
-- ============================================================
INSERT INTO users (username, password, role, full_name, email) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN', 'System Administrator', 'admin@oceanview.lk'),
('receptionist1', '5e2b4d823cc8bd8c16c4b720e94192f38ce09b66eae135f6ea3fee1e1a5b0594', 'RECEPTIONIST', 'Nimal Perera', 'nimal@oceanview.lk'),
('receptionist2', '5e2b4d823cc8bd8c16c4b720e94192f38ce09b66eae135f6ea3fee1e1a5b0594', 'RECEPTIONIST', 'Kumari Silva', 'kumari@oceanview.lk');

-- ============================================================
-- Rooms
-- ============================================================
INSERT INTO rooms (room_number, room_type, rate_per_night, max_occupancy, description, status) VALUES
('101', 'STANDARD', 100.00, 2, 'Ground floor standard room with garden view, AC, WiFi', 'AVAILABLE'),
('102', 'STANDARD', 100.00, 2, 'Ground floor standard room with pool view, AC, WiFi', 'AVAILABLE'),
('103', 'STANDARD', 100.00, 2, 'Ground floor standard room with courtyard view, AC, WiFi', 'AVAILABLE'),
('201', 'DELUXE', 200.00, 3, 'First floor deluxe room with ocean view, AC, WiFi, minibar', 'AVAILABLE'),
('202', 'DELUXE', 200.00, 3, 'First floor deluxe room with balcony, AC, WiFi, minibar', 'AVAILABLE'),
('203', 'DELUXE', 200.00, 3, 'First floor deluxe room with terrace, AC, WiFi, minibar', 'AVAILABLE'),
('301', 'SUITE', 350.00, 4, 'Top floor luxury suite with ocean panorama, jacuzzi, AC, WiFi, minibar, living area', 'AVAILABLE'),
('302', 'SUITE', 350.00, 4, 'Top floor luxury suite with private terrace, jacuzzi, AC, WiFi, minibar, living area', 'AVAILABLE');

-- ============================================================
-- Sample Guests
-- ============================================================
INSERT INTO guests (name, address, contact_number, email, nic_passport, nationality) VALUES
('Kamal Fernando', '45 Galle Road, Colombo 03', '0771234567', 'kamal@email.com', '199012345678', 'Sri Lankan'),
('Sarah Johnson', '12 Oxford Street, London', '0712345678', 'sarah.j@email.com', 'P12345678', 'British'),
('Amal Perera', '78 Temple Road, Kandy', '0761234567', 'amal.p@email.com', '198765432109', 'Sri Lankan');

-- ============================================================
-- Sample Reservations
-- ============================================================
INSERT INTO reservations (reservation_number, guest_id, room_id, check_in_date, check_out_date, num_guests, status, created_by) VALUES
('RES-20260301-0001', 1, 1, '2026-03-01', '2026-03-04', 2, 'CONFIRMED', 1),
('RES-20260305-0002', 2, 5, '2026-03-05', '2026-03-10', 2, 'CONFIRMED', 2),
('RES-20260310-0003', 3, 7, '2026-03-10', '2026-03-13', 3, 'CONFIRMED', 2);
