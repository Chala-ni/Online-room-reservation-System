<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Bill Details" active="bills">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <a href="${pageContext.request.contextPath}/bills" class="btn btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Back to Bills
            </a>

            <div class="card" id="billPrint">
                <div class="card-header text-center bg-primary text-white">
                    <h3 class="mb-0"><i class="bi bi-water"></i> Ocean View Resort</h3>
                    <p class="mb-0">Room Reservation Invoice</p>
                </div>
                <div class="card-body">
                    <!-- Bill Header -->
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h5>Guest Details</h5>
                            <p class="mb-1"><strong>${bill.guestName}</strong></p>
                            <p class="mb-1">Room: ${bill.roomNumber}</p>
                            <p class="mb-0">Reservation: ${bill.reservationNumber}</p>
                        </div>
                        <div class="col-md-6 text-md-end">
                            <h5>Bill #${bill.id}</h5>
                            <p class="mb-1">Date: ${bill.createdAt}</p>
                            <p class="mb-0">
                                Status: 
                                <span class="badge bg-${bill.paymentStatus == 'PAID' ? 'success' : 
                                    bill.paymentStatus == 'PENDING' ? 'warning' : 'danger'} fs-6">
                                    ${bill.paymentStatus}
                                </span>
                            </p>
                        </div>
                    </div>

                    <hr>

                    <!-- Bill Breakdown -->
                    <table class="table">
                        <thead class="table-light">
                            <tr>
                                <th>Description</th>
                                <th class="text-end">Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Room Charge (${bill.numNights} night(s) × $<fmt:formatNumber value="${bill.ratePerNight}" maxFractionDigits="2"/>)</td>
                                <td class="text-end">$<fmt:formatNumber value="${bill.subtotal}" maxFractionDigits="2"/></td>
                            </tr>
                            <tr>
                                <td>Service Charge (10%)</td>
                                <td class="text-end">$<fmt:formatNumber value="${bill.serviceCharge}" maxFractionDigits="2"/></td>
                            </tr>
                            <tr>
                                <td>Tourism Levy (2%)</td>
                                <td class="text-end">$<fmt:formatNumber value="${bill.tourismLevy}" maxFractionDigits="2"/></td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr class="table-primary">
                                <th class="fs-5">Total Amount</th>
                                <th class="text-end fs-5">$<fmt:formatNumber value="${bill.totalAmount}" maxFractionDigits="2"/></th>
                            </tr>
                        </tfoot>
                    </table>

                    <!-- Actions -->
                    <div class="d-flex gap-2 mt-4 no-print">
                        <c:if test="${bill.paymentStatus == 'PENDING'}">
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
                <div class="card-footer text-center text-muted">
                    <small>Thank you for choosing Ocean View Resort!</small>
                </div>
            </div>
        </div>
    </div>

</t:layout>
