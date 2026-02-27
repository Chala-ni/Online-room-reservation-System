<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Bills" active="bills">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-receipt"></i> Bills</h2>
    </div>

    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty bills}">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
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
                                        <td>$<fmt:formatNumber value="${bill.ratePerNight}" maxFractionDigits="2"/></td>
                                        <td><strong>$<fmt:formatNumber value="${bill.totalAmount}" maxFractionDigits="2"/></strong></td>
                                        <td>
                                            <span class="badge bg-${bill.paymentStatus == 'PAID' ? 'success' : 
                                                bill.paymentStatus == 'PENDING' ? 'warning' : 'danger'}">
                                                ${bill.paymentStatus}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/bills/view?id=${bill.id}" 
                                                   class="btn btn-outline-primary">
                                                    <i class="bi bi-eye"></i> View
                                                </a>
                                                <c:if test="${bill.paymentStatus == 'PENDING'}">
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
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="bi bi-receipt display-1 text-muted"></i>
                        <p class="text-muted mt-3">No bills generated yet.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</t:layout>
