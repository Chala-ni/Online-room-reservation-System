<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Rooms" active="rooms">

    <style>
        .page-header { margin-bottom: 24px; }
        .page-header h2 { font-size: 1.35rem; }
        
        /* Filter Pills */
        .filter-section { 
            background: #fff; border-radius: 14px; padding: 20px 24px; 
            margin-bottom: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.04);
        }
        .filter-group { margin-bottom: 12px; }
        .filter-group:last-child { margin-bottom: 0; }
        .filter-label { font-size: 0.72rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.06em; color: #64748b; margin-bottom: 10px; }
        .filter-pills { display: flex; flex-wrap: wrap; gap: 8px; }
        .filter-pill {
            padding: 7px 14px; border-radius: 20px; font-size: 0.78rem; font-weight: 600;
            text-decoration: none; border: 1.5px solid #e2e8f0; color: #64748b;
            transition: all 0.15s ease; background: #fff;
        }
        .filter-pill:hover { border-color: #94a3b8; color: #475569; background: #f8fafc; }
        .filter-pill.active { background: #0f172a; border-color: #0f172a; color: #fff; }
        .filter-pill.type-active { background: #3b82f6; border-color: #3b82f6; color: #fff; }
        .filter-pill.status-available { border-color: #10b981; color: #059669; }
        .filter-pill.status-available:hover, .filter-pill.status-available.active { background: #10b981; border-color: #10b981; color: #fff; }
        .filter-pill.status-occupied { border-color: #f59e0b; color: #d97706; }
        .filter-pill.status-occupied:hover, .filter-pill.status-occupied.active { background: #f59e0b; border-color: #f59e0b; color: #fff; }
        .filter-pill.status-maintenance { border-color: #ef4444; color: #dc2626; }
        .filter-pill.status-maintenance:hover, .filter-pill.status-maintenance.active { background: #ef4444; border-color: #ef4444; color: #fff; }
        
        /* Room Cards */
        .room-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 20px; }
        .room-card-new {
            background: #fff; border-radius: 16px; overflow: hidden;
            box-shadow: 0 1px 3px rgba(0,0,0,0.06);
            transition: all 0.2s ease; position: relative;
        }
        .room-card-new:hover { transform: translateY(-4px); box-shadow: 0 12px 24px -8px rgba(0,0,0,0.12); }
        .room-card-indicator {
            position: absolute; top: 0; left: 0; right: 0; height: 4px;
        }
        .room-card-indicator.available { background: linear-gradient(90deg, #10b981, #34d399); }
        .room-card-indicator.occupied { background: linear-gradient(90deg, #f59e0b, #fbbf24); }
        .room-card-indicator.maintenance { background: linear-gradient(90deg, #ef4444, #f87171); }
        .room-card-content { padding: 24px; }
        .room-card-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 16px; }
        .room-number { font-size: 1.25rem; font-weight: 700; color: #0f172a; }
        .room-status-pill {
            padding: 4px 10px; border-radius: 12px; font-size: 0.65rem; font-weight: 700;
            text-transform: uppercase; letter-spacing: 0.04em;
        }
        .room-status-pill.available { background: #d1fae5; color: #059669; }
        .room-status-pill.occupied { background: #fef3c7; color: #b45309; }
        .room-status-pill.maintenance { background: #fee2e2; color: #dc2626; }
        .room-type-badge {
            display: inline-flex; align-items: center; gap: 6px;
            background: #f1f5f9; border-radius: 8px; padding: 8px 12px;
            font-size: 0.75rem; font-weight: 700; color: #475569;
            text-transform: uppercase; letter-spacing: 0.03em; margin-bottom: 12px;
        }
        .room-type-badge i { color: #3b82f6; }
        .room-description { font-size: 0.85rem; color: #64748b; line-height: 1.5; margin-bottom: 16px; min-height: 42px; }
        .room-meta { display: flex; justify-content: space-between; align-items: center; padding-top: 16px; border-top: 1px solid #f1f5f9; }
        .room-price { font-size: 1.15rem; font-weight: 700; color: #0f172a; }
        .room-price span { font-size: 0.8rem; font-weight: 500; color: #94a3b8; }
        .room-capacity { display: flex; align-items: center; gap: 4px; font-size: 0.8rem; color: #64748b; }
        .room-capacity i { color: #3b82f6; }
        .room-card-actions {
            display: flex; gap: 6px; padding: 12px 24px 20px; background: #fafafa;
            justify-content: center;
        }
        .room-card-actions .btn { padding: 8px 12px; border-radius: 8px; font-size: 0.8rem; }
        
        /* Empty State */
        .empty-state { text-align: center; padding: 60px 20px; }
        .empty-state i { font-size: 4rem; color: #e2e8f0; margin-bottom: 16px; }
        .empty-state p { color: #94a3b8; }
    </style>

    <div class="d-flex justify-content-between align-items-center page-header">
        <h2><i class="bi bi-building"></i> Room Management</h2>
        <c:if test="${sessionScope.userRole == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/rooms/new" class="btn btn-primary">
                <i class="bi bi-plus-lg"></i> Add Room
            </a>
        </c:if>
    </div>

    <!-- Filters -->
    <div class="filter-section">
        <div class="filter-group">
            <div class="filter-label">Room Type</div>
            <div class="filter-pills">
                <a href="${pageContext.request.contextPath}/rooms" 
                   class="filter-pill ${empty typeFilter && empty statusFilter ? 'active' : ''}">All Rooms</a>
                <c:forEach var="type" items="${roomTypes}">
                    <a href="${pageContext.request.contextPath}/rooms?type=${type}" 
                       class="filter-pill ${typeFilter == type.name() ? 'type-active' : ''}">${type}</a>
                </c:forEach>
            </div>
        </div>
        <div class="filter-group">
            <div class="filter-label">Status</div>
            <div class="filter-pills">
                <c:forEach var="status" items="${roomStatuses}">
                    <a href="${pageContext.request.contextPath}/rooms?status=${status}" 
                       class="filter-pill status-${status.toString().toLowerCase()} ${statusFilter == status.name() ? 'active' : ''}">${status}</a>
                </c:forEach>
            </div>
        </div>
    </div>

    <!-- Room Cards -->
    <c:choose>
        <c:when test="${not empty rooms}">
            <div class="room-grid">
                <c:forEach var="room" items="${rooms}">
                    <div class="room-card-new">
                        <div class="room-card-indicator ${room.status.toString().toLowerCase()}"></div>
                        <div class="room-card-content">
                            <div class="room-card-header">
                                <span class="room-number">Room ${room.roomNumber}</span>
                                <span class="room-status-pill ${room.status.toString().toLowerCase()}">${room.status}</span>
                            </div>
                            <div class="room-type-badge">
                                <i class="bi bi-door-open"></i> ${room.roomType}
                            </div>
                            <p class="room-description">${room.description}</p>
                            <div class="room-meta">
                                <div class="room-price">
                                    LKR <fmt:formatNumber value="${room.ratePerNight}" maxFractionDigits="0"/>
                                    <span>/ night</span>
                                </div>
                                <div class="room-capacity">
                                    <i class="bi bi-people-fill"></i> ${room.maxOccupancy} guests
                                </div>
                            </div>
                        </div>
                        <c:if test="${sessionScope.userRole == 'ADMIN'}">
                            <div class="room-card-actions">
                                <a href="${pageContext.request.contextPath}/rooms/edit?id=${room.id}" 
                                   class="btn btn-secondary" title="Edit">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <c:if test="${room.status.name() eq 'AVAILABLE'}">
                                    <a href="${pageContext.request.contextPath}/rooms/status?id=${room.id}&newStatus=MAINTENANCE" 
                                       class="btn btn-outline-danger" title="Set Maintenance">
                                        <i class="bi bi-tools"></i>
                                    </a>
                                </c:if>
                                <c:if test="${room.status.name() eq 'MAINTENANCE'}">
                                    <a href="${pageContext.request.contextPath}/rooms/status?id=${room.id}&newStatus=AVAILABLE" 
                                       class="btn btn-outline-success" title="Set Available">
                                        <i class="bi bi-check-lg"></i>
                                    </a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/rooms/delete?id=${room.id}" 
                                   class="btn btn-outline-danger" title="Delete"
                                   onclick="return confirm('Delete this room permanently?')">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="bi bi-door-closed"></i>
                <p>No rooms found matching your criteria.</p>
            </div>
        </c:otherwise>
    </c:choose>

</t:layout>
