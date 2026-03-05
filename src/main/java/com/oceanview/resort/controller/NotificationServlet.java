package com.oceanview.resort.controller;

import com.oceanview.resort.dao.NotificationDAO;
import com.oceanview.resort.model.Notification;
import com.oceanview.resort.util.ErrorMessageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Notification Servlet - view and manage system notifications.
 */
@WebServlet("/notifications/*")
public class NotificationServlet extends HttpServlet {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listNotifications(request, response);
            } else if (pathInfo.equals("/mark-read")) {
                markAsRead(request, response);
            } else if (pathInfo.equals("/mark-all-read")) {
                markAllAsRead(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            request.setAttribute("error", ErrorMessageUtil.translateSQLException(e));
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void listNotifications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Notification> notifications = notificationDAO.findAll();
        int unreadCount = notificationDAO.countUnread();
        
        request.setAttribute("notifications", notifications);
        request.setAttribute("unreadCount", unreadCount);
        request.getRequestDispatcher("/WEB-INF/views/notification/list.jsp").forward(request, response);
    }

    private void markAsRead(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        notificationDAO.markAsRead(id);
        response.sendRedirect(request.getContextPath() + "/notifications");
    }

    private void markAllAsRead(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        notificationDAO.markAllAsRead();
        response.sendRedirect(request.getContextPath() + "/notifications?success=All+notifications+marked+as+read");
    }
}
