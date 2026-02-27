<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservations" active="reservations">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-calendar-check"></i> Reservations</h2>
        <a href="${pageContext.request.contextPath}/reservations/new" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> New Reservation
        </a>
    </div>

    <!-- Search & Filter -->
    <div class="card mb-4">
        <div class="card-body">
            <div class="row g-3">
                <div class="col-md-6">
                    <form method="get" action="${pageContext.request.contextPath}/reservations" class="d-flex gap-2">
                        <input type="text" class="form-control" name="search" placeholder="Search by guest name..." 
                               value="${search}">
                        <button type="submit" class="btn btn-outline-primary">
                            <i class="bi bi-search"></i> Search
                        </button>
                    </form>
                </div>
                <div class="col-md-6">
                    <div class="d-flex gap-2 justify-content-md-end">
                        <a href="${pageContext.request.contextPath}/reservations" 
                           class="btn btn-outline-secondary ${empty statusFilter ? 'active' : ''}">All</a>
                        <a href="${pageContext.request.contextPath}/reservations?status=CONFIRMED" 
                           class="btn btn-outline-primary ${statusFilter == 'CONFIRMED' ? 'active' : ''}">Confirmed</a>
                        <a href="${pageContext.request.contextPath}/reservations?status=CHECKED_IN" 
                           class="btn btn-outline-success ${statusFilter == 'CHECKED_IN' ? 'active' : ''}">Checked In</a>
                        <a href="${pageContext.request.contextPath}/reservations?status=CHECKED_OUT" 
                           class="btn btn-outline-info ${statusFilter == 'CHECKED_OUT' ? 'active' : ''}">Checked Out</a>
                        <a href="${pageContext.request.contextPath}/reservations?status=CANCELLED" 
                           class="btn btn-outline-danger ${statusFilter == 'CANCELLED' ? 'active' : ''}">Cancelled</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Reservations Table -->
    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty reservations}">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>Reservation #</th>
                                    <th>Guest</th>
                                    <th>Room</th>
                                    <th>Check-in</th>
                                    <th>Check-out</th>
                                    <th>Guests</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="res" items="${reservations}">
                                    <tr>
                                        <td><strong>${res.reservationNumber}</strong></td>
                                        <td>${res.guestName}</td>
                                        <td>${res.roomNumber}</td>
                                        <td>${res.checkInDate}</td>
                                        <td>${res.checkOutDate}</td>
                                        <td>${res.numGuests}</td>
                                        <td>
                                            <span class="badge bg-${res.status == 'CONFIRMED' ? 'primary' : 
                                                res.status == 'CHECKED_IN' ? 'success' : 
                                                res.status == 'CHECKED_OUT' ? 'info' : 'danger'}">
                                                ${res.status}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/reservations/view?id=${res.id}" 
                                                   class="btn btn-outline-primary" title="View">
                                                    <i class="bi bi-eye"></i>
                                                </a>
                                                <c:if test="${res.status == 'CONFIRMED'}">
                                                    <a href="${pageContext.request.contextPath}/reservations/edit?id=${res.id}" 
                                                       class="btn btn-outline-warning" title="Edit">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/reservations/checkin?id=${res.id}" 
                                                       class="btn btn-outline-success" title="Check In"
                                                       onclick="return confirm('Confirm check-in?')">
                                                        <i class="bi bi-box-arrow-in-right"></i>
                                                    </a>
                                                </c:if>
                                                <c:if test="${res.status == 'CHECKED_IN'}">
                                                    <a href="${pageContext.request.contextPath}/reservations/checkout?id=${res.id}" 
                                                       class="btn btn-outline-info" title="Check Out"
                                                       onclick="return confirm('Confirm check-out?')">
                                                        <i class="bi bi-box-arrow-right"></i>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="bi bi-calendar-x display-1 text-muted"></i>
                        <p class="text-muted mt-3">No reservations found.</p>
                        <a href="${pageContext.request.contextPath}/reservations/new" class="btn btn-primary">
                            Create New Reservation
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
