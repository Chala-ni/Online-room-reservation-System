<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${empty room ? 'Add Room' : 'Edit Room'}" active="rooms">

    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0">
                        <i class="bi bi-${empty room ? 'plus-circle' : 'pencil'}"></i>
                        ${empty room ? 'Add New Room' : 'Edit Room'}
                    </h4>
                </div>
                <div class="card-body">
                    <form method="post" 
                          action="${pageContext.request.contextPath}/rooms/${empty room ? 'new' : 'edit'}">
                        
                        <c:if test="${not empty room}">
                            <input type="hidden" name="id" value="${room.id}">
                        </c:if>

                        <!-- Room Number -->
                        <div class="mb-3">
                            <label for="roomNumber" class="form-label">Room Number <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="roomNumber" name="roomNumber" 
                                   value="${room.roomNumber}" required maxlength="10"
                                   placeholder="e.g., 101">
                        </div>

                        <!-- Room Type -->
                        <div class="mb-3">
                            <label for="roomType" class="form-label">Room Type <span class="text-danger">*</span></label>
                            <select class="form-select" id="roomType" name="roomType" required>
                                <option value="">-- Select Type --</option>
                                <c:forEach var="type" items="${roomTypes}">
                                    <option value="${type}" ${room.roomType == type ? 'selected' : ''}>
                                        ${type} ($${type.defaultRate}/night, max ${type.defaultMaxOccupancy} guests)
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Rate Per Night -->
                        <div class="mb-3">
                            <label for="ratePerNight" class="form-label">Rate Per Night ($)</label>
                            <input type="number" class="form-control" id="ratePerNight" name="ratePerNight" 
                                   value="${room.ratePerNight}" step="0.01" min="0"
                                   placeholder="Leave empty to use default rate">
                        </div>

                        <!-- Max Occupancy -->
                        <c:if test="${not empty room}">
                            <div class="mb-3">
                                <label for="maxOccupancy" class="form-label">Max Occupancy</label>
                                <input type="number" class="form-control" id="maxOccupancy" name="maxOccupancy" 
                                       value="${room.maxOccupancy}" min="1" max="10">
                            </div>
                        </c:if>

                        <!-- Status (Edit only) -->
                        <c:if test="${not empty room}">
                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status">
                                    <c:forEach var="status" items="${roomStatuses}">
                                        <option value="${status}" ${room.status == status ? 'selected' : ''}>${status}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>

                        <!-- Description -->
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" 
                                      rows="3" placeholder="Room description...">${room.description}</textarea>
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> 
                                ${empty room ? 'Add Room' : 'Update Room'}
                            </button>
                            <a href="${pageContext.request.contextPath}/rooms" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Cancel
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</t:layout>
