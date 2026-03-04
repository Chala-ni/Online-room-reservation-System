<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reports" active="reports">

    <div class="dark-card mb-4">
        <div class="dark-card-header">
            <div class="card-subtitle">Administration</div>
            <div class="card-title"><i class="bi bi-graph-up me-2"></i>Reports</div>
        </div>
        <div class="dark-card-body">
            <div class="info-section">
                <div class="row g-4">
                    <!-- Reservation Report -->
                    <div class="col-md-4">
                        <div class="text-center py-3">
                            <i class="bi bi-calendar-check" style="font-size: 2.5rem; color: #3b82f6;"></i>
                            <h5 class="mt-3" style="color: #0f172a; font-weight: 600;">Reservation Report</h5>
                            <p style="color: #64748b; font-size: 0.85rem;">View reservations within a date range</p>
                            <a href="${pageContext.request.contextPath}/reports/reservations" class="btn btn-primary btn-sm">
                                <i class="bi bi-arrow-right"></i> View Report
                            </a>
                        </div>
                    </div>

                    <!-- Revenue Report -->
                    <div class="col-md-4">
                        <div class="text-center py-3">
                            <i class="bi bi-currency-dollar" style="font-size: 2.5rem; color: #10b981;"></i>
                            <h5 class="mt-3" style="color: #0f172a; font-weight: 600;">Revenue Report</h5>
                            <p style="color: #64748b; font-size: 0.85rem;">Monthly and total revenue analysis</p>
                            <a href="${pageContext.request.contextPath}/reports/revenue" class="btn btn-success btn-sm">
                                <i class="bi bi-arrow-right"></i> View Report
                            </a>
                        </div>
                    </div>

                    <!-- Occupancy Report -->
                    <div class="col-md-4">
                        <div class="text-center py-3">
                            <i class="bi bi-bar-chart" style="font-size: 2.5rem; color: #06b6d4;"></i>
                            <h5 class="mt-3" style="color: #0f172a; font-weight: 600;">Occupancy Report</h5>
                            <p style="color: #64748b; font-size: 0.85rem;">Room occupancy rates and availability</p>
                            <a href="${pageContext.request.contextPath}/reports/occupancy" class="btn btn-info btn-sm">
                                <i class="bi bi-arrow-right"></i> View Report
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:layout>
