<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservation Report" active="reports">

    <h2 class="mb-4"><i class="bi bi-calendar-check"></i> Reservation Report</h2>

    <!-- Date Filter -->
    <div class="card mb-4">
        <div class="card-body">
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

    <!-- Results -->
    <c:if test="${not empty reservations}">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Reservations from ${startDate} to ${endDate}
                    <span class="badge bg-primary">${reservations.size()} records</span>
                </h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
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
                                        <span class="badge bg-${res.status == 'CONFIRMED' ? 'primary' : 
                                            res.status == 'CHECKED_IN' ? 'success' : 
                                            res.status == 'CHECKED_OUT' ? 'info' : 'danger'}">
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
    </c:if>

    <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary mt-3">
        <i class="bi bi-arrow-left"></i> Back to Reports
    </a>

</t:layout>
