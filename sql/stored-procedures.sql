-- ============================================================
-- Ocean View Resort - Stored Procedures
-- ============================================================

USE ocean_view_resort;

-- ============================================================
-- SP: Calculate Bill for a Reservation
-- ============================================================
DELIMITER //
CREATE PROCEDURE sp_calculate_bill(IN p_reservation_id INT)
BEGIN
    DECLARE v_nights INT;
    DECLARE v_rate DECIMAL(10,2);
    DECLARE v_subtotal DECIMAL(10,2);
    DECLARE v_service_charge DECIMAL(10,2);
    DECLARE v_tourism_levy DECIMAL(10,2);
    DECLARE v_total DECIMAL(10,2);

    -- Calculate nights and get room rate
    SELECT DATEDIFF(r.check_out_date, r.check_in_date), rm.rate_per_night
    INTO v_nights, v_rate
    FROM reservations r
    JOIN rooms rm ON r.room_id = rm.id
    WHERE r.id = p_reservation_id;

    -- Calculate amounts
    SET v_subtotal = v_nights * v_rate;
    SET v_service_charge = v_subtotal * 0.10;   -- 10% service charge
    SET v_tourism_levy = v_subtotal * 0.02;      -- 2% tourism levy
    SET v_total = v_subtotal + v_service_charge + v_tourism_levy;

    -- Insert or update bill
    INSERT INTO bills (reservation_id, num_nights, rate_per_night, subtotal, service_charge, tourism_levy, total_amount)
    VALUES (p_reservation_id, v_nights, v_rate, v_subtotal, v_service_charge, v_tourism_levy, v_total)
    ON DUPLICATE KEY UPDATE
        num_nights = v_nights,
        rate_per_night = v_rate,
        subtotal = v_subtotal,
        service_charge = v_service_charge,
        tourism_levy = v_tourism_levy,
        total_amount = v_total;
END //
DELIMITER ;

-- ============================================================
-- SP: Get Reservation Summary (with guest and room details)
-- ============================================================
DELIMITER //
CREATE PROCEDURE sp_get_reservation_summary(IN p_reservation_number VARCHAR(20))
BEGIN
    SELECT 
        r.reservation_number,
        r.status AS reservation_status,
        r.check_in_date,
        r.check_out_date,
        DATEDIFF(r.check_out_date, r.check_in_date) AS num_nights,
        r.num_guests,
        r.special_requests,
        g.name AS guest_name,
        g.contact_number,
        g.email AS guest_email,
        g.nic_passport,
        rm.room_number,
        rm.room_type,
        rm.rate_per_night,
        b.total_amount,
        b.payment_status,
        u.full_name AS created_by_name,
        r.created_at
    FROM reservations r
    JOIN guests g ON r.guest_id = g.id
    JOIN rooms rm ON r.room_id = rm.id
    LEFT JOIN bills b ON r.id = b.reservation_id
    LEFT JOIN users u ON r.created_by = u.id
    WHERE r.reservation_number = p_reservation_number;
END //
DELIMITER ;

-- ============================================================
-- SP: Get Revenue Report by Date Range
-- ============================================================
DELIMITER //
CREATE PROCEDURE sp_revenue_report(IN p_start_date DATE, IN p_end_date DATE)
BEGIN
    SELECT 
        rm.room_type,
        COUNT(b.id) AS total_bookings,
        SUM(b.num_nights) AS total_nights,
        SUM(b.subtotal) AS total_room_revenue,
        SUM(b.service_charge) AS total_service_charges,
        SUM(b.tourism_levy) AS total_tourism_levy,
        SUM(b.total_amount) AS total_revenue
    FROM bills b
    JOIN reservations r ON b.reservation_id = r.id
    JOIN rooms rm ON r.room_id = rm.id
    WHERE r.check_in_date BETWEEN p_start_date AND p_end_date
    AND r.status IN ('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT')
    GROUP BY rm.room_type
    ORDER BY total_revenue DESC;
END //
DELIMITER ;

-- ============================================================
-- SP: Check Room Availability
-- ============================================================
DELIMITER //
CREATE PROCEDURE sp_check_room_availability(IN p_check_in DATE, IN p_check_out DATE)
BEGIN
    SELECT rm.*
    FROM rooms rm
    WHERE rm.status != 'MAINTENANCE'
    AND rm.id NOT IN (
        SELECT r.room_id
        FROM reservations r
        WHERE r.status IN ('CONFIRMED', 'CHECKED_IN')
        AND r.check_in_date < p_check_out
        AND r.check_out_date > p_check_in
    )
    ORDER BY rm.room_type, rm.room_number;
END //
DELIMITER ;
