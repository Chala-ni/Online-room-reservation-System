<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Guest Details" active="guests">

    <div class="row justify-content-center">
        <div class="col-lg-8">
            <a href="${pageContext.request.contextPath}/guests" class="btn btn-outline-secondary mb-3">
                <i class="bi bi-arrow-left"></i> Back to List
            </a>

            <div class="dark-card">
                <div class="dark-card-header d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <div class="card-subtitle">Guest Profile</div>
                        <div class="card-title"><i class="bi bi-person me-2"></i>${guest.name}</div>
                    </div>
                    <div class="header-actions">
                        <a href="${pageContext.request.contextPath}/guests/edit?id=${guest.id}" 
                           class="btn btn-warning btn-sm">
                            <i class="bi bi-pencil"></i> Edit
                        </a>
                    </div>
                </div>
                <div class="dark-card-body">
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                        <div class="info-section">
                            <div class="section-title"><i class="bi bi-person-vcard"></i> Personal Info</div>
                            <div class="info-row">
                                <span class="info-label">Full Name</span>
                                <span class="info-value">${guest.name}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Nationality</span>
                                <span class="info-value">${guest.nationality}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">NIC/Passport</span>
                                <span class="info-value">${guest.nicPassport}</span>
                            </div>
                        </div>
                        <div class="info-section">
                            <div class="section-title"><i class="bi bi-telephone"></i> Contact Info</div>
                            <div class="info-row">
                                <span class="info-label">Phone</span>
                                <span class="info-value">${guest.contactNumber}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Email</span>
                                <span class="info-value">${guest.email}</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Address</span>
                                <span class="info-value">${guest.address}</span>
                            </div>
                        </div>
                    </div>
                    <div class="info-section">
                        <div class="section-title"><i class="bi bi-clock-history"></i> Registration</div>
                        <div class="info-row">
                            <span class="info-label">Registered</span>
                            <span class="info-value">${guest.createdAt}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:layout>
