<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Occupancy Report" active="reports">

    <div class="row g-4">
        <!-- Visual Occupancy -->
        <div class="col-md-6">
            <div class="dark-card">
                <div class="dark-card-header">
                    <div class="card-subtitle">Report</div>
                    <div class="card-title"><i class="bi bi-bar-chart me-2"></i>Room Occupancy Overview</div>
                </div>
                <div class="dark-card-body">
                    <div class="info-section">
                        <!-- Occupancy Bar -->
                        <div class="mb-4">
                            <div class="d-flex justify-content-between mb-1">
                                <span style="color: #64748b; font-size: 0.85rem;">Occupancy Rate</span>
                                <span style="font-weight: 600; color: #0f172a;"><fmt:formatNumber value="${stats.occupancyRate}" maxFractionDigits="1"/>%</span>
                            </div>
                            <div class="progress" style="height: 30px; border-radius: 8px;">
                                <div class="progress-bar bg-success" role="progressbar" 
                                     style="width: ${stats.occupancyRate}%">
                                    ${stats.occupiedRooms} occupied
                                </div>
                                <div class="progress-bar bg-light text-dark" role="progressbar" 
                                     style="width: ${100 - stats.occupancyRate}%">
                                    ${stats.availableRooms} available
                                </div>
                            </div>
                        </div>

                        <!-- Room Breakdown -->
                        <div class="info-row">
                            <span class="info-label"><span class="badge bg-success me-2">Available</span></span>
                            <span class="info-value">${stats.availableRooms} rooms</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label"><span class="badge bg-warning me-2">Occupied</span></span>
                            <span class="info-value">${stats.occupiedRooms} rooms</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label"><span class="badge bg-danger me-2">Maintenance</span></span>
                            <span class="info-value">${stats.maintenanceRooms} rooms</span>
                        </div>
                        <div class="info-row" style="border-top: 2px solid #e2e8f0; margin-top: 8px; padding-top: 14px;">
                            <span class="info-label" style="font-weight: 700; color: #0f172a;">Total</span>
                            <span class="info-value" style="color: #3b82f6;">${stats.totalRooms} rooms</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Activity Stats -->
        <div class="col-md-6">
            <div class="dark-card">
                <div class="dark-card-header">
                    <div class="card-subtitle">Statistics</div>
                    <div class="card-title"><i class="bi bi-activity me-2"></i>Today's Activity</div>
                </div>
                <div class="dark-card-body">
                    <div class="info-section">
                        <div class="info-row">
                            <span class="info-label"><i class="bi bi-box-arrow-in-right text-success me-2"></i>Check-ins Today</span>
                            <span class="info-value">${stats.todayCheckIns}</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label"><i class="bi bi-box-arrow-right text-warning me-2"></i>Check-outs Today</span>
                            <span class="info-value">${stats.todayCheckOuts}</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label"><i class="bi bi-calendar-check text-primary me-2"></i>Active Reservations</span>
                            <span class="info-value">${stats.totalActiveReservations}</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label"><i class="bi bi-people text-info me-2"></i>Total Guests</span>
                            <span class="info-value">${stats.totalGuests}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary mt-4">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
