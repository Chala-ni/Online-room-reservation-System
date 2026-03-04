<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Bill Details" active="bills">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <a href="${pageContext.request.contextPath}/bills" class="btn btn-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Back to Bills
            </a>

            <div class="dark-card" id="billPrint">
                <div class="dark-card-header">
                    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                        <div>
                            <div class="card-subtitle">Invoice</div>
                            <div class="card-title"><i class="bi bi-water me-2"></i>Ocean View Resort</div>
                            <div class="header-badge bg-${bill.paymentStatus.name() eq 'PAID' ? 'success' : 
                                bill.paymentStatus.name() eq 'PENDING' ? 'warning' : 'danger'}">
                                <i class="bi bi-${bill.paymentStatus.name() eq 'PAID' ? 'check-circle' : 'clock'}"></i>
                                ${bill.paymentStatus}
                            </div>
                        </div>
                        <div class="text-end">
                            <div style="font-size: 2rem; font-weight: 700; color: #60a5fa;">LKR <fmt:formatNumber value="${bill.totalAmount}" maxFractionDigits="2"/></div>
                            <div style="font-size: 0.75rem; color: #94a3b8; margin-top: 2px;">TOTAL AMOUNT</div>
                        </div>
                    </div>
                </div>

                <div class="dark-card-body">
                    <div class="info-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                        <!-- Guest Info -->
                        <div class="info-section">
                            <div class="section-title"><i class="bi bi-person"></i> Guest Details</div>
                            <div class="info-row">
                                <span class="info-label">Name</span>
                                <span class="info-value">${bill.guestName}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Room</span>
                                <span class="info-value highlight">${bill.roomNumber}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Reservation</span>
                                <span class="info-value">${bill.reservationNumber}</span>
                            </div>
                        </div>

                        <!-- Bill Info -->
                        <div class="info-section">
                            <div class="section-title"><i class="bi bi-receipt"></i> Bill Info</div>
                            <div class="info-row">
                                <span class="info-label">Bill #</span>
                                <span class="info-value">${bill.id}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Generated</span>
                                <span class="info-value">${bill.generatedAt}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Nights</span>
                                <span class="info-value">${bill.numNights}</span>
                            </div>
                        </div>
                    </div>

                    <!-- Cost Breakdown -->
                    <div class="info-section">
                        <div class="section-title"><i class="bi bi-calculator"></i> Cost Breakdown</div>
                        <div class="info-row">
                            <span class="info-label">Room Charge (${bill.numNights} night(s) × LKR <fmt:formatNumber value="${bill.ratePerNight}" maxFractionDigits="2"/>)</span>
                            <span class="info-value">LKR <fmt:formatNumber value="${bill.subtotal}" maxFractionDigits="2"/></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Service Charge (10%)</span>
                            <span class="info-value">LKR <fmt:formatNumber value="${bill.serviceCharge}" maxFractionDigits="2"/></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Tourism Levy (2%)</span>
                            <span class="info-value">LKR <fmt:formatNumber value="${bill.tourismLevy}" maxFractionDigits="2"/></span>
                        </div>
                        <div class="info-row" style="padding: 14px 0; margin-top: 8px; border-top: 2px solid #e2e8f0;">
                            <span class="info-label" style="font-weight: 700; color: #0f172a; font-size: 1rem;">Total Amount</span>
                            <span class="info-value" style="font-size: 1.2rem; color: #3b82f6;">LKR <fmt:formatNumber value="${bill.totalAmount}" maxFractionDigits="2"/></span>
                        </div>
                    </div>
                </div>

                <!-- Actions -->
                <div class="dark-card-footer no-print">
                    <c:if test="${bill.paymentStatus.name() eq 'PENDING'}">
                        <a href="${pageContext.request.contextPath}/bills/pay?id=${bill.id}" 
                           class="btn btn-success"
                           onclick="return confirm('Mark this bill as paid?')">
                            <i class="bi bi-check-circle"></i> Mark as Paid
                        </a>
                    </c:if>
                    <button onclick="window.print()" class="btn btn-outline-primary">
                        <i class="bi bi-printer"></i> Print Invoice
                    </button>
                </div>
            </div>

            <div class="text-center mt-3">
                <small class="text-muted">Thank you for choosing Ocean View Resort!</small>
            </div>
        </div>
    </div>

</t:layout>
