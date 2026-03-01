<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Dashboard" active="dashboard">

    <!-- Page Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-speedometer2"></i> Dashboard</h2>
        <span class="text-muted">Welcome, ${sessionScope.fullName}</span>
    </div>

    <!-- Statistics Cards -->
    <div class="row g-4 mb-4">
        <!-- Total Rooms -->
        <div class="col-xl-3 col-md-6">
            <div class="card stat-card border-primary">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Total Rooms</h6>
                            <h3 class="mb-0">${stats.totalRooms}</h3>
                        </div>
                        <div class="stat-icon bg-primary">
                            <i class="bi bi-door-open"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Available Rooms -->
        <div class="col-xl-3 col-md-6">
            <div class="card stat-card border-success">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Available</h6>
                            <h3 class="mb-0 text-success">${stats.availableRooms}</h3>
                        </div>
                        <div class="stat-icon bg-success">
                            <i class="bi bi-check-circle"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Occupied Rooms -->
        <div class="col-xl-3 col-md-6">
            <div class="card stat-card border-warning">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Occupied</h6>
                            <h3 class="mb-0 text-warning">${stats.occupiedRooms}</h3>
                        </div>
                        <div class="stat-icon bg-warning">
                            <i class="bi bi-person-fill"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Occupancy Rate -->
        <div class="col-xl-3 col-md-6">
            <div class="card stat-card border-info">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Occupancy Rate</h6>
                            <h3 class="mb-0 text-info">
                                <fmt:formatNumber value="${stats.occupancyRate}" maxFractionDigits="1"/>%
                            </h3>
                        </div>
                        <div class="stat-icon bg-info">
                            <i class="bi bi-graph-up"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Revenue & Activity Row -->
    <div class="row g-4 mb-4">
        <!-- Revenue Card -->
        <div class="col-xl-4 col-md-6">
            <div class="card stat-card border-success">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Monthly Revenue</h6>
                            <h3 class="mb-0 text-success">
                                $<fmt:formatNumber value="${stats.monthlyRevenue}" maxFractionDigits="2"/>
                            </h3>
                        </div>
                        <div class="stat-icon bg-success">
                            <i class="bi bi-currency-dollar"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Total Revenue -->
        <div class="col-xl-4 col-md-6">
            <div class="card stat-card border-primary">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Total Revenue</h6>
                            <h3 class="mb-0">
                                $<fmt:formatNumber value="${stats.totalRevenue}" maxFractionDigits="2"/>
                            </h3>
                        </div>
                        <div class="stat-icon bg-primary">
                            <i class="bi bi-wallet2"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Total Guests -->
        <div class="col-xl-4 col-md-6">
            <div class="card stat-card border-secondary">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <div>
                            <h6 class="text-muted">Total Guests</h6>
                            <h3 class="mb-0">${stats.totalGuests}</h3>
                        </div>
                        <div class="stat-icon bg-secondary">
                            <i class="bi bi-people"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Today's Activity -->
    <div class="row g-4">
        <!-- Today's Check-ins -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0"><i class="bi bi-box-arrow-in-right"></i> Today's Check-ins 
                        <span class="badge bg-light text-success">${stats.todayCheckIns}</span>
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty todayCheckIns}">
                            <div class="table-responsive">
                                <table class="table table-sm table-hover">
                                    <thead>
                                        <tr>
                                            <th>Reservation #</th>
                                            <th>Guest</th>
                                            <th>Room</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="res" items="${todayCheckIns}">
                                            <tr>
                                                <td>${res.reservationNumber}</td>
                                                <td>${res.guestName}</td>
                                                <td>${res.roomNumber}</td>
                                                <td>
                                                    <span class="badge bg-${res.status == 'CONFIRMED' ? 'primary' : 
                                                        res.status == 'CHECKED_IN' ? 'success' : 'secondary'}">
                                                        ${res.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/reservations/view?id=${res.id}" 
                                                       class="btn btn-sm btn-outline-primary">View</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted text-center mb-0">No check-ins scheduled for today.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Today's Check-outs -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header bg-warning text-dark">
                    <h5 class="mb-0"><i class="bi bi-box-arrow-right"></i> Today's Check-outs
                        <span class="badge bg-light text-warning">${stats.todayCheckOuts}</span>
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty todayCheckOuts}">
                            <div class="table-responsive">
                                <table class="table table-sm table-hover">
                                    <thead>
                                        <tr>
                                            <th>Reservation #</th>
                                            <th>Guest</th>
                                            <th>Room</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="res" items="${todayCheckOuts}">
                                            <tr>
                                                <td>${res.reservationNumber}</td>
                                                <td>${res.guestName}</td>
                                                <td>${res.roomNumber}</td>
                                                <td>
                                                    <span class="badge bg-${res.status == 'CHECKED_IN' ? 'success' : 
                                                        res.status == 'CHECKED_OUT' ? 'info' : 'secondary'}">
                                                        ${res.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/reservations/view?id=${res.id}" 
                                                       class="btn btn-sm btn-outline-primary">View</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted text-center mb-0">No check-outs scheduled for today.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0"><i class="bi bi-lightning"></i> Quick Actions</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex gap-2 flex-wrap">
                        <a href="${pageContext.request.contextPath}/reservations/new" class="btn btn-primary">
                            <i class="bi bi-plus-circle"></i> New Reservation
                        </a>
                        <a href="${pageContext.request.contextPath}/guests/new" class="btn btn-success">
                            <i class="bi bi-person-plus"></i> Add Guest
                        </a>
                        <a href="${pageContext.request.contextPath}/reservations" class="btn btn-info">
                            <i class="bi bi-search"></i> Search Reservations
                        </a>
                        <a href="${pageContext.request.contextPath}/rooms" class="btn btn-secondary">
                            <i class="bi bi-door-open"></i> View Rooms
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:layout>
