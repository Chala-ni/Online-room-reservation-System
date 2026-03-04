<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reservation Details" active="reservations">

    <style>
        .res-detail-card { border: none; border-radius: 16px; overflow: hidden; }
        .res-header { 
            background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
            color: #fff; 
            padding: 28px 32px;
        }
        .res-header .res-number { font-size: 0.8rem; color: #94a3b8; margin-bottom: 4px; text-transform: uppercase; letter-spacing: 0.05em; }
        .res-header h2 { font-size: 1.5rem; font-weight: 700; margin-bottom: 12px; color: #ffffff; }
        .res-status-badge { 
            display: inline-flex; align-items: center; gap: 6px;
            padding: 8px 16px; border-radius: 20px; font-size: 0.75rem; 
            font-weight: 700; text-transform: uppercase; letter-spacing: 0.04em;
        }
        .status-confirmed { background: rgba(59,130,246,0.2); color: #60a5fa; }
        .status-checked_in { background: rgba(16,185,129,0.2); color: #34d399; }
        .status-checked_out { background: rgba(6,182,212,0.2); color: #22d3ee; }
        .status-cancelled { background: rgba(239,68,68,0.2); color: #f87171; }
        .res-body { padding: 0; }
        .info-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); }
        .info-section { padding: 24px 32px; border-bottom: 1px solid #f1f5f9; }
        .info-section:last-child { border-bottom: none; }
        .info-section-title { 
            font-size: 0.7rem; font-weight: 700; text-transform: uppercase;
            letter-spacing: 0.08em; color: #64748b; margin-bottom: 16px;
            display: flex; align-items: center; gap: 8px;
        }
        .info-section-title i { font-size: 1rem; color: #3b82f6; }
        .info-row { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #f8fafc; }
        .info-row:last-child { border-bottom: none; }
        .info-label { color: #64748b; font-size: 0.85rem; }
        .info-value { color: #0f172a; font-weight: 600; font-size: 0.9rem; }
        .info-value.highlight { color: #3b82f6; }
        .dates-row { display: flex; gap: 24px; flex-wrap: wrap; }
        .date-box { 
            flex: 1; min-width: 140px;
            background: #f8fafc; border-radius: 12px; padding: 16px; text-align: center;
        }
        .date-box .label { font-size: 0.7rem; color: #64748b; text-transform: uppercase; letter-spacing: 0.04em; margin-bottom: 4px; }
        .date-box .date { font-size: 1.1rem; font-weight: 700; color: #0f172a; }
        .date-box .day { font-size: 0.8rem; color: #94a3b8; }
        .nights-badge { 
            display: flex; align-items: center; justify-content: center;
            background: linear-gradient(135deg, #3b82f6, #8b5cf6); 
            color: #fff; border-radius: 50%; width: 48px; height: 48px;
            font-weight: 700; font-size: 1.1rem; margin: 0 auto;
        }
        .action-bar { 
            background: #f8fafc; padding: 20px 32px; 
            display: flex; gap: 12px; flex-wrap: wrap; align-items: center;
            border-top: 1px solid #e2e8f0;
        }
        .action-bar .btn { padding: 10px 20px; font-weight: 600; border-radius: 10px; }
        .action-bar .btn i { margin-right: 6px; }
        .bill-alert { 
            background: linear-gradient(135deg, #fef3c7, #fde68a); 
            border: none; border-radius: 12px; padding: 16px 20px; margin: 0 32px 24px;
            display: flex; align-items: center; gap: 12px;
        }
        .bill-alert i { font-size: 1.5rem; color: #d97706; }
        .bill-alert-text { flex: 1; }
        .bill-alert-text strong { color: #92400e; }
        .bill-alert-text p { margin: 0; font-size: 0.85rem; color: #a16207; }
    </style>

    <a href="${pageContext.request.contextPath}/reservations" class="btn btn-secondary mb-3">
        <i class="bi bi-arrow-left"></i> Back to Reservations
    </a>

    <div class="card res-detail-card shadow">
        <div class="res-header">
            <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                <div>
                    <div class="res-number">Reservation</div>
                    <h2>${reservation.reservationNumber}</h2>
                    <span class="res-status-badge status-${reservation.status.name().toLowerCase()}">
                        <i class="bi bi-${reservation.status.name() eq 'CONFIRMED' ? 'check-circle' : 
                            reservation.status.name() eq 'CHECKED_IN' ? 'door-open' : 
                            reservation.status.name() eq 'CHECKED_OUT' ? 'box-arrow-right' : 'x-circle'}"></i>
                        ${reservation.status.name().replace('_', ' ')}
                    </span>
                </div>
                <div class="text-end">
                    <div class="nights-badge">${reservation.numNights}</div>
                    <div style="font-size: 0.75rem; color: #94a3b8; margin-top: 6px;">NIGHTS</div>
                </div>
            </div>
        </div>

        <div class="res-body">
            <!-- Dates Section -->
            <div class="info-section">
                <div class="dates-row">
                    <div class="date-box">
                        <div class="label">Check-in</div>
                        <div class="date">${reservation.checkInDate}</div>
                    </div>
                    <div class="date-box">
                        <div class="label">Check-out</div>
                        <div class="date">${reservation.checkOutDate}</div>
                    </div>
                </div>
            </div>

            <div class="info-grid">
                <!-- Guest Info -->
                <div class="info-section">
                    <div class="info-section-title"><i class="bi bi-person"></i> Guest</div>
                    <div class="info-row">
                        <span class="info-label">Name</span>
                        <span class="info-value">${reservation.guestName}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Guests</span>
                        <span class="info-value">${reservation.numGuests} person(s)</span>
                    </div>
                </div>

                <!-- Room Info -->
                <div class="info-section">
                    <div class="info-section-title"><i class="bi bi-door-open"></i> Room</div>
                    <div class="info-row">
                        <span class="info-label">Room Number</span>
                        <span class="info-value highlight">${reservation.roomNumber}</span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Type</span>
                        <span class="info-value">${reservation.roomType}</span>
                    </div>
                </div>
            </div>

            <!-- Special Requests -->
            <c:if test="${not empty reservation.specialRequests}">
                <div class="info-section">
                    <div class="info-section-title"><i class="bi bi-chat-text"></i> Special Requests</div>
                    <p style="color: #475569; margin: 0; font-size: 0.9rem;">${reservation.specialRequests}</p>
                </div>
            </c:if>

            <!-- Bill Alert for Checked Out -->
            <c:if test="${reservation.status.name() eq 'CHECKED_OUT'}">
                <div class="bill-alert">
                    <i class="bi bi-exclamation-triangle"></i>
                    <div class="bill-alert-text">
                        <strong>Bill Not Generated</strong>
                        <p>Click "Generate Bill" to create the invoice and add to revenue.</p>
                    </div>
                    <a href="${pageContext.request.contextPath}/bills/generate?reservationId=${reservation.id}" 
                       class="btn btn-warning">
                        <i class="bi bi-receipt"></i> Generate Bill
                    </a>
                </div>
            </c:if>
        </div>

        <!-- Action Bar -->
        <div class="action-bar">
            <c:if test="${reservation.status.name() eq 'CONFIRMED'}">
                <a href="${pageContext.request.contextPath}/reservations/edit?id=${reservation.id}" 
                   class="btn btn-secondary">
                    <i class="bi bi-pencil"></i> Edit
                </a>
                <a href="${pageContext.request.contextPath}/reservations/checkin?id=${reservation.id}" 
                   class="btn btn-success"
                   onclick="return confirm('Check in this guest?')">
                    <i class="bi bi-box-arrow-in-right"></i> Check In
                </a>
                <a href="${pageContext.request.contextPath}/reservations/cancel?id=${reservation.id}" 
                   class="btn btn-outline-danger"
                   onclick="return confirm('Cancel this reservation?')">
                    <i class="bi bi-x-circle"></i> Cancel
                </a>
            </c:if>
            <c:if test="${reservation.status.name() eq 'CHECKED_IN'}">
                <a href="${pageContext.request.contextPath}/reservations/checkout?id=${reservation.id}" 
                   class="btn btn-info"
                   onclick="return confirm('Check out this guest?')">
                    <i class="bi bi-box-arrow-right"></i> Check Out
                </a>
            </c:if>
            <c:if test="${reservation.status.name() eq 'CHECKED_OUT'}">
                <a href="${pageContext.request.contextPath}/bills/generate?reservationId=${reservation.id}" 
                   class="btn btn-success">
                    <i class="bi bi-receipt"></i> Generate Bill
                </a>
            </c:if>
            <c:if test="${reservation.status.name() eq 'CANCELLED'}">
                <span class="text-muted">This reservation has been cancelled.</span>
            </c:if>
        </div>
    </div>

    <!-- Meta Info -->
    <div class="text-muted mt-3" style="font-size: 0.8rem;">
        Created: ${reservation.createdAt} | Last Updated: ${reservation.updatedAt}
    </div>

</t:layout>
