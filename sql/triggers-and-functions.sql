-- ============================================================
-- Ocean View Resort - Triggers and Functions
-- ============================================================

USE ocean_view_resort;

-- ============================================================
-- Trigger: Auto-update room status on reservation status change
-- ============================================================
DELIMITER //
CREATE TRIGGER trg_update_room_status_on_checkin
AFTER UPDATE ON reservations
FOR EACH ROW
BEGIN
    IF NEW.status = 'CHECKED_IN' AND OLD.status != 'CHECKED_IN' THEN
        UPDATE rooms SET status = 'OCCUPIED' WHERE id = NEW.room_id;
    ELSEIF (NEW.status = 'CHECKED_OUT' OR NEW.status = 'CANCELLED') 
           AND OLD.status IN ('CONFIRMED', 'CHECKED_IN') THEN
        UPDATE rooms SET status = 'AVAILABLE' WHERE id = NEW.room_id;
    END IF;
END //
DELIMITER ;

-- ============================================================
-- Trigger: Auto-log audit trail on reservation changes
-- ============================================================
DELIMITER //
CREATE TRIGGER trg_audit_reservation_insert
AFTER INSERT ON reservations
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (action, entity_type, entity_id, performed_by, details)
    VALUES ('CREATE', 'RESERVATION', NEW.id, NEW.created_by, 
            CONCAT('Reservation ', NEW.reservation_number, ' created for room ', 
                   (SELECT room_number FROM rooms WHERE id = NEW.room_id)));
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER trg_audit_reservation_update
AFTER UPDATE ON reservations
FOR EACH ROW
BEGIN
    IF NEW.status != OLD.status THEN
        INSERT INTO audit_log (action, entity_type, entity_id, performed_by, details)
        VALUES ('STATUS_CHANGE', 'RESERVATION', NEW.id, NEW.created_by,
                CONCAT('Reservation ', NEW.reservation_number, ' status changed from ', 
                       OLD.status, ' to ', NEW.status));
    END IF;
END //
DELIMITER ;

-- ============================================================
-- Function: Get Occupancy Rate for a given date
-- ============================================================
DELIMITER //
CREATE FUNCTION fn_get_occupancy_rate(p_date DATE)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    DECLARE v_total_rooms INT;
    DECLARE v_occupied_rooms INT;

    SELECT COUNT(*) INTO v_total_rooms 
    FROM rooms 
    WHERE status != 'MAINTENANCE';

    SELECT COUNT(DISTINCT room_id) INTO v_occupied_rooms
    FROM reservations
    WHERE p_date >= check_in_date 
    AND p_date < check_out_date
    AND status IN ('CONFIRMED', 'CHECKED_IN');

    IF v_total_rooms = 0 THEN
        RETURN 0.00;
    END IF;

    RETURN ROUND((v_occupied_rooms / v_total_rooms) * 100, 2);
END //
DELIMITER ;

-- ============================================================
-- Function: Get total revenue for a given month
-- ============================================================
DELIMITER //
CREATE FUNCTION fn_get_monthly_revenue(p_year INT, p_month INT)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
    DECLARE v_revenue DECIMAL(12,2);

    SELECT IFNULL(SUM(b.total_amount), 0.00) INTO v_revenue
    FROM bills b
    JOIN reservations r ON b.reservation_id = r.id
    WHERE YEAR(r.check_in_date) = p_year 
    AND MONTH(r.check_in_date) = p_month
    AND r.status IN ('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT');

    RETURN v_revenue;
END //
DELIMITER ;
