<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservation Details" active="reservations">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <!-- Back Button -->
            <a href="${pageContext.request.contextPath}/reservations" class="btn btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Back to List
            </a>

            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0">
                        <i class="bi bi-calendar-check"></i> Reservation ${reservation.reservationNumber}
                    </h4>
                    <span class="badge bg-${reservation.status == 'CONFIRMED' ? 'primary' : 
                        reservation.status == 'CHECKED_IN' ? 'success' : 
                        reservation.status == 'CHECKED_OUT' ? 'info' : 'danger'} fs-6">
                        ${reservation.status}
                    </span>
                </div>
                <div class="card-body">
                    <div class="row">
                        <!-- Guest Info -->
                        <div class="col-md-6 mb-4">
                            <h5 class="text-muted"><i class="bi bi-person"></i> Guest Information</h5>
                            <table class="table table-sm">
                                <tr>
                                    <th>Guest Name:</th>
                                    <td>${reservation.guestName}</td>
                                </tr>
                            </table>
                        </div>

                        <!-- Room Info -->
                        <div class="col-md-6 mb-4">
                            <h5 class="text-muted"><i class="bi bi-door-open"></i> Room Information</h5>
                            <table class="table table-sm">
                                <tr>
                                    <th>Room Number:</th>
                                    <td>${reservation.roomNumber}</td>
                                </tr>
                                <tr>
                                    <th>Room Type:</th>
                                    <td>${reservation.roomType}</td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <!-- Reservation Details -->
                    <h5 class="text-muted"><i class="bi bi-info-circle"></i> Reservation Details</h5>
                    <table class="table">
                        <tr>
                            <th>Check-in Date:</th>
                            <td>${reservation.checkInDate}</td>
                            <th>Check-out Date:</th>
                            <td>${reservation.checkOutDate}</td>
                        </tr>
                        <tr>
                            <th>Number of Nights:</th>
                            <td>${reservation.numNights}</td>
                            <th>Number of Guests:</th>
                            <td>${reservation.numGuests}</td>
                        </tr>
                        <tr>
                            <th>Special Requests:</th>
                            <td colspan="3">${empty reservation.specialRequests ? 'None' : reservation.specialRequests}</td>
                        </tr>
                        <tr>
                            <th>Created:</th>
                            <td>${reservation.createdAt}</td>
                            <th>Last Updated:</th>
                            <td>${reservation.updatedAt}</td>
                        </tr>
                    </table>

                    <!-- Actions -->
                    <div class="d-flex gap-2 mt-3">
                        <c:if test="${reservation.status == 'CONFIRMED'}">
                            <a href="${pageContext.request.contextPath}/reservations/edit?id=${reservation.id}" 
                               class="btn btn-warning">
                                <i class="bi bi-pencil"></i> Edit
                            </a>
                            <a href="${pageContext.request.contextPath}/reservations/checkin?id=${reservation.id}" 
                               class="btn btn-success"
                               onclick="return confirm('Are you sure you want to check in this guest?')">
                                <i class="bi bi-box-arrow-in-right"></i> Check In
                            </a>
                            <a href="${pageContext.request.contextPath}/reservations/cancel?id=${reservation.id}" 
                               class="btn btn-danger"
                               onclick="return confirm('Are you sure you want to cancel this reservation?')">
                                <i class="bi bi-x-circle"></i> Cancel
                            </a>
                        </c:if>
                        <c:if test="${reservation.status == 'CHECKED_IN'}">
                            <a href="${pageContext.request.contextPath}/reservations/checkout?id=${reservation.id}" 
                               class="btn btn-info"
                               onclick="return confirm('Are you sure you want to check out this guest?')">
                                <i class="bi bi-box-arrow-right"></i> Check Out
                            </a>
                        </c:if>
                        <c:if test="${reservation.status == 'CHECKED_OUT'}">
                            <a href="${pageContext.request.contextPath}/bills/generate?reservationId=${reservation.id}" 
                               class="btn btn-success">
                                <i class="bi bi-receipt"></i> Generate Bill
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:layout>
