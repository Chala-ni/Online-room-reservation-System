/**
 * Ocean View Resort - Main JavaScript
 * Vanilla JS (no frameworks)
 */

document.addEventListener('DOMContentLoaded', function () {

    // ---- Auto-dismiss alerts after 5 seconds ----
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            bsAlert.close();
        }, 5000);
    });

    // ---- Form validation feedback ----
    const forms = document.querySelectorAll('form');
    forms.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });

    // ---- Confirm delete actions ----
    document.querySelectorAll('[data-confirm]').forEach(function (el) {
        el.addEventListener('click', function (event) {
            if (!confirm(el.getAttribute('data-confirm'))) {
                event.preventDefault();
            }
        });
    });

    // ---- Active nav link highlighting (fallback) ----
    const currentPath = window.location.pathname;
    document.querySelectorAll('.navbar .nav-link').forEach(function (link) {
        const href = link.getAttribute('href');
        if (href && currentPath.includes(href) && href !== '/') {
            link.classList.add('active');
        }
    });

    // ---- Date inputs: set min to today for future dates ----
    const today = new Date().toISOString().split('T')[0];
    const checkInDate = document.getElementById('checkInDate');
    const checkOutDate = document.getElementById('checkOutDate');

    if (checkInDate && !checkInDate.value) {
        checkInDate.setAttribute('min', today);
    }

    if (checkInDate && checkOutDate) {
        checkInDate.addEventListener('change', function () {
            checkOutDate.setAttribute('min', checkInDate.value);
            if (checkOutDate.value && checkOutDate.value <= checkInDate.value) {
                checkOutDate.value = '';
            }
        });
    }

    // ---- Tooltips initialization ----
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipTriggerList.forEach(function (el) {
        new bootstrap.Tooltip(el);
    });

    // ---- Search functionality with debounce ----
    const searchInput = document.querySelector('input[name="search"]');
    if (searchInput) {
        searchInput.addEventListener('keyup', debounce(function (e) {
            if (e.key === 'Enter') {
                e.target.closest('form').submit();
            }
        }, 300));
    }

    console.log('Ocean View Resort - System Loaded');
});

/**
 * Debounce utility function.
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction() {
        const context = this;
        const args = arguments;
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            func.apply(context, args);
        }, wait);
    };
}

/**
 * Format currency display.
 */
function formatCurrency(amount) {
    return '$' + parseFloat(amount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

/**
 * AJAX helper for API calls.
 */
function apiCall(url, method, data) {
    const options = {
        method: method || 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    };

    if (data && method !== 'GET') {
        options.body = JSON.stringify(data);
    }

    return fetch(url, options)
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}
