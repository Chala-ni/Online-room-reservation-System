<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${empty reservation ? 'New Reservation' : 'Edit Reservation'}" active="reservations">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0">
                        <i class="bi bi-${empty reservation ? 'plus-circle' : 'pencil'}"></i>
                        ${empty reservation ? 'New Reservation' : 'Edit Reservation'}
                    </h4>
                </div>
                <div class="card-body">
                    <form method="post" id="reservationForm"
                          action="${pageContext.request.contextPath}/reservations/${empty reservation ? 'new' : 'edit'}">
                        
                        <c:if test="${not empty reservation}">
                            <input type="hidden" name="id" value="${reservation.id}">
                        </c:if>

                        <!-- Guest Selection -->
                        <div class="mb-3">
                            <label for="guestId" class="form-label">Guest <span class="text-danger">*</span></label>
                            <select class="form-select" id="guestId" name="guestId" required>
                                <option value="">-- Select Guest --</option>
                                <c:forEach var="guest" items="${guests}">
                                    <option value="${guest.id}" 
                                        ${reservation.guestId == guest.id ? 'selected' : ''}>
                                        ${guest.name} (${guest.nicPassport})
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-text">
                                <a href="${pageContext.request.contextPath}/guests/new" target="_blank">
                                    <i class="bi bi-plus"></i> Add new guest
                                </a>
                            </div>
                        </div>

                        <!-- Room Selection -->
                        <div class="mb-3">
                            <label for="roomId" class="form-label">Room <span class="text-danger">*</span></label>
                            <select class="form-select" id="roomId" name="roomId" required>
                                <option value="">-- Select Room --</option>
                                <c:forEach var="room" items="${rooms}">
                                    <option value="${room.id}" 
                                        data-rate="${room.ratePerNight}" 
                                        data-max="${room.maxOccupancy}"
                                        ${reservation.roomId == room.id ? 'selected' : ''}>
                                        Room ${room.roomNumber} - ${room.roomType} 
                                        ($${room.ratePerNight}/night, max ${room.maxOccupancy} guests)
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Dates -->
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="checkInDate" class="form-label">
                                    Check-in Date <span class="text-danger">*</span>
                                </label>
                                <input type="date" class="form-control" id="checkInDate" name="checkInDate" 
                                       value="${reservation.checkInDate}" required>
                            </div>
                            <div class="col-md-6">
                                <label for="checkOutDate" class="form-label">
                                    Check-out Date <span class="text-danger">*</span>
                                </label>
                                <input type="date" class="form-control" id="checkOutDate" name="checkOutDate" 
                                       value="${reservation.checkOutDate}" required>
                            </div>
                        </div>

                        <!-- Number of Guests -->
                        <div class="mb-3">
                            <label for="numGuests" class="form-label">
                                Number of Guests <span class="text-danger">*</span>
                            </label>
                            <input type="number" class="form-control" id="numGuests" name="numGuests" 
                                   value="${empty reservation.numGuests ? 1 : reservation.numGuests}" 
                                   min="1" max="10" required>
                        </div>

                        <!-- Special Requests -->
                        <div class="mb-3">
                            <label for="specialRequests" class="form-label">Special Requests</label>
                            <textarea class="form-control" id="specialRequests" name="specialRequests" 
                                      rows="3" placeholder="Any special requirements...">${reservation.specialRequests}</textarea>
                        </div>

                        <!-- Estimated Cost Preview -->
                        <div class="mb-3" id="costPreview" style="display:none;">
                            <div class="alert alert-info">
                                <strong>Estimated Cost:</strong>
                                <span id="numNights">0</span> night(s) × 
                                $<span id="rateDisplay">0</span>/night = 
                                <strong>$<span id="subtotalDisplay">0</span></strong>
                                <small class="text-muted">(+ taxes & charges)</small>
                            </div>
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> 
                                ${empty reservation ? 'Create Reservation' : 'Update Reservation'}
                            </button>
                            <a href="${pageContext.request.contextPath}/reservations" class="btn btn-secondary">
                                <i class="bi bi-x-circle"></i> Cancel
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Cost preview calculation
        document.addEventListener('DOMContentLoaded', function() {
            const roomSelect = document.getElementById('roomId');
            const checkIn = document.getElementById('checkInDate');
            const checkOut = document.getElementById('checkOutDate');
            const costPreview = document.getElementById('costPreview');

            function updateCost() {
                const selectedOption = roomSelect.options[roomSelect.selectedIndex];
                const rate = parseFloat(selectedOption.getAttribute('data-rate') || 0);
                const start = new Date(checkIn.value);
                const end = new Date(checkOut.value);
                
                if (rate > 0 && checkIn.value && checkOut.value && end > start) {
                    const nights = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
                    document.getElementById('numNights').textContent = nights;
                    document.getElementById('rateDisplay').textContent = rate.toFixed(2);
                    document.getElementById('subtotalDisplay').textContent = (nights * rate).toFixed(2);
                    costPreview.style.display = 'block';
                } else {
                    costPreview.style.display = 'none';
                }
            }

            roomSelect.addEventListener('change', updateCost);
            checkIn.addEventListener('change', updateCost);
            checkOut.addEventListener('change', updateCost);
            updateCost();
        });
    </script>

</t:layout>
