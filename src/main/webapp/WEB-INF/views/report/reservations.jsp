<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservation Report" active="reports">

    <!-- Date Filter -->
    <div class="dark-card mb-4">
        <div class="dark-card-header">
            <div class="card-subtitle">Report</div>
            <div class="card-title"><i class="bi bi-calendar-check me-2"></i>Reservation Report</div>
        </div>
        <div class="dark-card-body">
            <div class="filter-section">
                <form method="get" action="${pageContext.request.contextPath}/reports/reservations" class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label for="startDate" class="form-label">Start Date</label>
                        <input type="date" class="form-control" id="startDate" name="startDate" 
                               value="${startDate}" required>
                    </div>
                    <div class="col-md-4">
                        <label for="endDate" class="form-label">End Date</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" 
                               value="${endDate}" required>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search"></i> Generate Report
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Results -->
    <c:if test="${not empty reservations}">
        <div class="dark-card">
            <div class="dark-card-header d-flex justify-content-between align-items-center">
                <div class="card-title" style="font-size: 1rem;">Reservations from ${startDate} to ${endDate}</div>
                <span class="badge bg-primary">${reservations.size()} records</span>
            </div>
            <div class="dark-card-body">
                <div class="table-section">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Reservation #</th>
                                    <th>Guest</th>
                                    <th>Room</th>
                                    <th>Check-in</th>
                                    <th>Check-out</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="res" items="${reservations}">
                                    <tr>
                                        <td>${res.reservationNumber}</td>
                                        <td>${res.guestName}</td>
                                        <td>${res.roomNumber}</td>
                                        <td>${res.checkInDate}</td>
                                        <td>${res.checkOutDate}</td>
                                        <td>
                                            <span class="badge bg-${res.status.name() eq 'CONFIRMED' ? 'primary' : 
                                                res.status.name() eq 'CHECKED_IN' ? 'success' : 
                                                res.status.name() eq 'CHECKED_OUT' ? 'info' : 'danger'}">
                                                ${res.status}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary mt-3">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
