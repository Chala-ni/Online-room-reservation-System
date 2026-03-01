<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Occupancy Report" active="reports">

    <h2 class="mb-4"><i class="bi bi-bar-chart"></i> Occupancy Report</h2>

    <div class="row g-4">
        <!-- Visual Occupancy -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Room Occupancy Overview</h5>
                </div>
                <div class="card-body">
                    <!-- Occupancy Bar -->
                    <div class="mb-4">
                        <div class="d-flex justify-content-between mb-1">
                            <span>Occupancy Rate</span>
                            <span><fmt:formatNumber value="${stats.occupancyRate}" maxFractionDigits="1"/>%</span>
                        </div>
                        <div class="progress" style="height: 30px;">
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
                    <table class="table">
                        <tr>
                            <td><span class="badge bg-success">Available</span></td>
                            <td>${stats.availableRooms} rooms</td>
                        </tr>
                        <tr>
                            <td><span class="badge bg-warning">Occupied</span></td>
                            <td>${stats.occupiedRooms} rooms</td>
                        </tr>
                        <tr>
                            <td><span class="badge bg-danger">Maintenance</span></td>
                            <td>${stats.maintenanceRooms} rooms</td>
                        </tr>
                        <tr class="table-primary">
                            <td><strong>Total</strong></td>
                            <td><strong>${stats.totalRooms} rooms</strong></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <!-- Activity Stats -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Today's Activity</h5>
                </div>
                <div class="card-body">
                    <table class="table">
                        <tr>
                            <td><i class="bi bi-box-arrow-in-right text-success"></i> Check-ins Today</td>
                            <td><strong>${stats.todayCheckIns}</strong></td>
                        </tr>
                        <tr>
                            <td><i class="bi bi-box-arrow-right text-warning"></i> Check-outs Today</td>
                            <td><strong>${stats.todayCheckOuts}</strong></td>
                        </tr>
                        <tr>
                            <td><i class="bi bi-calendar-check text-primary"></i> Active Reservations</td>
                            <td><strong>${stats.totalActiveReservations}</strong></td>
                        </tr>
                        <tr>
                            <td><i class="bi bi-people text-info"></i> Total Guests</td>
                            <td><strong>${stats.totalGuests}</strong></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary mt-4">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
