<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Bills" active="bills">

    <div class="dark-card">
        <div class="dark-card-header">
            <div class="card-subtitle">Management</div>
            <div class="card-title"><i class="bi bi-receipt me-2"></i>Bills</div>
        </div>

        <div class="dark-card-body">
            <c:choose>
                <c:when test="${not empty bills}">
                    <div class="table-section">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Reservation</th>
                                        <th>Guest</th>
                                        <th>Room</th>
                                        <th>Nights</th>
                                        <th>Rate/Night</th>
                                        <th>Total</th>
                                        <th>Payment Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="bill" items="${bills}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${bill.reservationNumber}</td>
                                            <td>${bill.guestName}</td>
                                            <td>${bill.roomNumber}</td>
                                            <td>${bill.numNights}</td>
                                            <td>LKR <fmt:formatNumber value="${bill.ratePerNight}" maxFractionDigits="2"/></td>
                                            <td><strong>LKR <fmt:formatNumber value="${bill.totalAmount}" maxFractionDigits="2"/></strong></td>
                                            <td>
                                                <span class="badge bg-${bill.paymentStatus.name() eq 'PAID' ? 'success' : 
                                                    bill.paymentStatus.name() eq 'PENDING' ? 'warning' : 'danger'}">
                                                    ${bill.paymentStatus}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/bills/view?id=${bill.id}" 
                                                       class="btn btn-outline-primary">
                                                        <i class="bi bi-eye"></i> View
                                                    </a>
                                                    <c:if test="${bill.paymentStatus.name() eq 'PENDING'}">
                                                        <a href="${pageContext.request.contextPath}/bills/pay?id=${bill.id}" 
                                                           class="btn btn-outline-success"
                                                           onclick="return confirm('Mark this bill as paid?')">
                                                            <i class="bi bi-check"></i> Pay
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
                        <i class="bi bi-receipt"></i>
                        <p>No bills generated yet.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
