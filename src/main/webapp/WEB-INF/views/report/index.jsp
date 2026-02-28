<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Reports" active="reports">

    <h2 class="mb-4"><i class="bi bi-graph-up"></i> Reports</h2>

    <div class="row g-4">
        <!-- Reservation Report -->
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="bi bi-calendar-check display-3 text-primary"></i>
                    <h5 class="mt-3">Reservation Report</h5>
                    <p class="text-muted">View reservations within a date range</p>
                    <a href="${pageContext.request.contextPath}/reports/reservations" class="btn btn-primary">
                        <i class="bi bi-arrow-right"></i> View Report
                    </a>
                </div>
            </div>
        </div>

        <!-- Revenue Report -->
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="bi bi-currency-dollar display-3 text-success"></i>
                    <h5 class="mt-3">Revenue Report</h5>
                    <p class="text-muted">Monthly and total revenue analysis</p>
                    <a href="${pageContext.request.contextPath}/reports/revenue" class="btn btn-success">
                        <i class="bi bi-arrow-right"></i> View Report
                    </a>
                </div>
            </div>
        </div>

        <!-- Occupancy Report -->
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <i class="bi bi-bar-chart display-3 text-info"></i>
                    <h5 class="mt-3">Occupancy Report</h5>
                    <p class="text-muted">Room occupancy rates and availability</p>
                    <a href="${pageContext.request.contextPath}/reports/occupancy" class="btn btn-info">
                        <i class="bi bi-arrow-right"></i> View Report
                    </a>
                </div>
            </div>
        </div>
    </div>

</t:layout>
