<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Help" active="help">

    <h2 class="mb-4"><i class="bi bi-question-circle"></i> Help & User Guide</h2>

    <div class="row">
        <div class="col-lg-8">
            <div class="accordion" id="helpAccordion">
                
                <!-- Getting Started -->
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#help1">
                            <i class="bi bi-play-circle me-2"></i> Getting Started
                        </button>
                    </h2>
                    <div id="help1" class="accordion-collapse collapse show" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>Welcome to the Ocean View Resort Room Reservation System. This system helps you manage:</p>
                            <ul>
                                <li><strong>Reservations</strong> - Create, edit, check-in/out, and cancel reservations</li>
                                <li><strong>Guests</strong> - Manage guest information</li>
                                <li><strong>Rooms</strong> - View room availability and manage room details</li>
                                <li><strong>Billing</strong> - Generate and manage bills</li>
                                <li><strong>Reports</strong> - View occupancy and revenue reports (Admin only)</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Making a Reservation -->
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help2">
                            <i class="bi bi-calendar-plus me-2"></i> Making a Reservation
                        </button>
                    </h2>
                    <div id="help2" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <ol>
                                <li>Go to <strong>Reservations</strong> &rarr; <strong>New Reservation</strong></li>
                                <li>Select the guest (or add a new guest first)</li>
                                <li>Choose an available room</li>
                                <li>Enter check-in and check-out dates</li>
                                <li>Specify number of guests and any special requests</li>
                                <li>Click <strong>Create Reservation</strong></li>
                            </ol>
                            <p class="text-muted">The system will automatically check room availability and validate dates.</p>
                        </div>
                    </div>
                </div>

                <!-- Check-in / Check-out -->
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help3">
                            <i class="bi bi-box-arrow-in-right me-2"></i> Check-in & Check-out Process
                        </button>
                    </h2>
                    <div id="help3" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <h6>Check-in:</h6>
                            <ol>
                                <li>Find the reservation (Dashboard or Reservations list)</li>
                                <li>Click the <strong>Check In</strong> button</li>
                                <li>The room status will automatically update to "Occupied"</li>
                            </ol>
                            <h6>Check-out:</h6>
                            <ol>
                                <li>Find the checked-in reservation</li>
                                <li>Click the <strong>Check Out</strong> button</li>
                                <li>The room will be set back to "Available"</li>
                                <li>Generate the bill from the reservation details page</li>
                            </ol>
                        </div>
                    </div>
                </div>

                <!-- Billing -->
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help4">
                            <i class="bi bi-receipt me-2"></i> Billing
                        </button>
                    </h2>
                    <div id="help4" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <p>Bills are generated after check-out and include:</p>
                            <ul>
                                <li><strong>Room charge</strong>: Rate per night &times; number of nights</li>
                                <li><strong>Service charge</strong>: 10% of subtotal</li>
                                <li><strong>Tourism levy</strong>: 2% of subtotal</li>
                            </ul>
                            <p>Premium room types (Deluxe, Suite) may have additional charges 
                               based on the billing strategy.</p>
                        </div>
                    </div>
                </div>

                <!-- Room Types -->
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help5">
                            <i class="bi bi-door-open me-2"></i> Room Types
                        </button>
                    </h2>
                    <div id="help5" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                        <div class="accordion-body">
                            <table class="table">
                                <thead>
                                    <tr><th>Type</th><th>Rate/Night</th><th>Max Guests</th><th>Description</th></tr>
                                </thead>
                                <tbody>
                                    <tr><td>Standard</td><td>$100.00</td><td>2</td><td>Comfortable room with essential amenities</td></tr>
                                    <tr><td>Deluxe</td><td>$200.00</td><td>3</td><td>Spacious room with premium amenities and ocean view</td></tr>
                                    <tr><td>Suite</td><td>$350.00</td><td>4</td><td>Luxury suite with separate living area and panoramic ocean view</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <!-- Quick Reference -->
        <div class="col-lg-4">
            <div class="card">
                <div class="card-header bg-info text-white">
                    <h5 class="mb-0"><i class="bi bi-info-circle"></i> Quick Reference</h5>
                </div>
                <div class="card-body">
                    <h6>Reservation Statuses:</h6>
                    <ul class="list-unstyled">
                        <li><span class="badge bg-primary">CONFIRMED</span> - New reservation</li>
                        <li><span class="badge bg-success">CHECKED_IN</span> - Guest is in room</li>
                        <li><span class="badge bg-info">CHECKED_OUT</span> - Guest has left</li>
                        <li><span class="badge bg-danger">CANCELLED</span> - Reservation cancelled</li>
                    </ul>

                    <h6>User Roles:</h6>
                    <ul class="list-unstyled">
                        <li><span class="badge bg-danger">ADMIN</span> - Full access to all features</li>
                        <li><span class="badge bg-primary">RECEPTIONIST</span> - Reservation & guest management</li>
                    </ul>

                    <h6>Keyboard Shortcuts:</h6>
                    <ul class="list-unstyled">
                        <li><kbd>Ctrl</kbd>+<kbd>K</kbd> - Quick search</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</t:layout>
