<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservations" active="reservations">

    <div class="dark-card">
        <div class="dark-card-header d-flex justify-content-between align-items-center flex-wrap gap-3">
            <div>
                <div class="card-subtitle">Management</div>
                <div class="card-title"><i class="bi bi-calendar-check me-2"></i>Reservations</div>
            </div>
            <a href="${pageContext.request.contextPath}/reservations/new" class="btn btn-primary btn-sm">
                <i class="bi bi-plus-circle"></i> New Reservation
            </a>
        </div>

        <div class="dark-card-body">
            <!-- Search & Filter -->
            <div class="filter-section">
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
                        <div class="d-flex gap-2 justify-content-md-end flex-wrap">
                            <a href="${pageContext.request.contextPath}/reservations" 
                               class="btn btn-outline-secondary btn-sm ${empty statusFilter ? 'active' : ''}">All</a>
                            <a href="${pageContext.request.contextPath}/reservations?status=CONFIRMED" 
                               class="btn btn-outline-primary btn-sm ${statusFilter == 'CONFIRMED' ? 'active' : ''}">Confirmed</a>
                            <a href="${pageContext.request.contextPath}/reservations?status=CHECKED_IN" 
                               class="btn btn-outline-success btn-sm ${statusFilter == 'CHECKED_IN' ? 'active' : ''}">Checked In</a>
                            <a href="${pageContext.request.contextPath}/reservations?status=CHECKED_OUT" 
                               class="btn btn-outline-info btn-sm ${statusFilter == 'CHECKED_OUT' ? 'active' : ''}">Checked Out</a>
                            <a href="${pageContext.request.contextPath}/reservations?status=CANCELLED" 
                               class="btn btn-outline-danger btn-sm ${statusFilter == 'CANCELLED' ? 'active' : ''}">Cancelled</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Reservations Table -->
            <c:choose>
                <c:when test="${not empty reservations}">
                    <div class="table-section">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead>
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
                                                <span class="badge bg-${res.status.name() eq 'CONFIRMED' ? 'primary' : 
                                                    res.status.name() eq 'CHECKED_IN' ? 'success' : 
                                                    res.status.name() eq 'CHECKED_OUT' ? 'info' : 'danger'}">
                                                    ${res.status}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/reservations/view?id=${res.id}" 
                                                       class="btn btn-outline-primary" title="View">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <c:if test="${res.status.name() eq 'CONFIRMED'}">
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
                                                    <c:if test="${res.status.name() eq 'CHECKED_IN'}">
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
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <i class="bi bi-calendar-x"></i>
                        <p>No reservations found.</p>
                        <a href="${pageContext.request.contextPath}/reservations/new" class="btn btn-primary">
                            Create New Reservation
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
