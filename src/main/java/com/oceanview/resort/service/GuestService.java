package com.oceanview.resort.service;

import com.oceanview.resort.dao.GuestDAO;
import com.oceanview.resort.model.Guest;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for guest management.
 */
public class GuestService {

    private final GuestDAO guestDAO = new GuestDAO();

    public int createGuest(Guest guest) throws SQLException {
        return guestDAO.create(guest);
    }

    public boolean updateGuest(Guest guest) throws SQLException {
        return guestDAO.update(guest);
    }

    public Guest findById(int id) throws SQLException {
        return guestDAO.findById(id);
    }

    public List<Guest> findAll() throws SQLException {
        return guestDAO.findAll();
    }

    public List<Guest> searchByName(String name) throws SQLException {
        return guestDAO.searchByName(name);
    }

    public Guest findByNicPassport(String nicPassport) throws SQLException {
        return guestDAO.findByNicPassport(nicPassport);
    }

    public boolean delete(int guestId) throws SQLException {
        return guestDAO.delete(guestId);
    }

    public int getTotalCount() throws SQLException {
        return guestDAO.getTotalCount();
    }
}
