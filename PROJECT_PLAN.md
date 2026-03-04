# Ocean View Resort — Online Room Reservation System

## Project Plan & Technical Documentation

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Technology Stack](#2-technology-stack)
3. [Server & Local Hosting Setup](#3-server--local-hosting-setup)
4. [Architecture](#4-architecture---3-tier)
5. [Features — Core & Additional](#5-features--core--additional)
6. [Design Patterns](#6-design-patterns)
7. [Database Design](#7-database-design)
8. [Project Structure](#8-project-structure)
9. [Assumptions](#9-assumptions)
10. [Validation Rules](#10-validation-rules)
11. [Reports](#11-reports)
12. [API Endpoints (Web Services)](#12-api-endpoints-web-services)
13. [Testing Strategy](#13-testing-strategy-tdd)
14. [Git & Version Control Strategy](#14-git--version-control-strategy)
15. [Timeline](#15-timeline)

---

## 1. Project Overview

**Ocean View Resort** is a popular beachside hotel in Galle, Sri Lanka, serving hundreds of guests each month. The management requires a computerized system to replace manual room reservation and guest record management, which currently leads to booking conflicts and delays.

This system will:
- Assign each guest a **unique reservation number**
- Register new guests with full details
- Handle room reservations efficiently
- Calculate and print bills
- Provide reports and analytics
- Expose REST API web services for distributed access

---

## 2. Technology Stack

| Layer               | Technology                          | Reason                                                                 |
|---------------------|-------------------------------------|------------------------------------------------------------------------|
| **Language**        | Java 17+                           | Assignment requirement                                                  |
| **Web Server**      | Apache Tomcat 10.1                  | Servlet container — NOT a framework, it's a runtime server             |
| **Backend**         | Java Servlets (Jakarta Servlet 6.0) | Standard Java EE/Jakarta EE API — part of the Java specification       |
| **Views**           | JSP (JavaServer Pages)              | Standard Java view technology — compiled to Servlets by Tomcat         |
| **Database**        | MySQL 8.0                           | Relational DB with stored procedures, triggers, functions              |
| **Data Access**     | JDBC (`java.sql.*`)                 | Standard Java library — raw SQL, no ORM                                |
| **Frontend**        | HTML5 + CSS3 + JavaScript (vanilla) | No frontend frameworks                                                  |
| **Styling**         | Bootstrap 5 (CDN)                   | CSS library (stylesheet only, not a framework)                         |
| **Security**        | Servlet Filters + HttpSession       | Manual authentication and session management                           |
| **Web Services**    | REST via Servlets (JSON responses)  | Distributed application requirement                                     |
| **JSON Processing** | Gson library (Google)               | Lightweight JSON serialization — a library, not a framework             |
| **Email**           | Jakarta Mail API                    | Email notifications — a library, not a framework                        |
| **Password Hash**   | `java.security.MessageDigest`       | SHA-256 hashing — built into Java                                       |
| **Testing**         | JUnit 5 + Mockito                   | Standard testing libraries                                              |
| **Build Tool**      | Apache Maven                        | Dependency management and build automation                              |
| **Version Control** | Git + GitHub                        | Source control and collaboration                                        |
| **CI/CD**           | GitHub Actions                      | Automated build and test pipeline                                       |

### What We Are NOT Using (No Frameworks):
- ~~Spring / Spring Boot~~
- ~~Hibernate / JPA~~
- ~~Thymeleaf~~
- ~~Any MVC framework (Struts, JSF, etc.)~~
- ~~Any frontend framework (React, Angular, Vue)~~

---

## 3. Server & Local Hosting Setup

### 3.1 What is Apache Tomcat?

Apache Tomcat is an **open-source Servlet container** (web server) developed by the Apache Software Foundation. It provides the runtime environment for Java Servlets and JSP pages. It is **NOT a framework** — it is a server that runs your Java web application.

- **Version**: Apache Tomcat 10.1 (supports Jakarta Servlet 6.0)
- **Download**: https://tomcat.apache.org/download-10.cgi
- **Default Port**: 8080

### 3.2 Prerequisites for Local Development

| Prerequisite       | Version   | Download Link                                      |
|--------------------|-----------|----------------------------------------------------|
| Java JDK           | 17 or 21  | https://adoptium.net/                               |
| Apache Tomcat      | 10.1.x    | https://tomcat.apache.org/download-10.cgi           |
| MySQL Server       | 8.0.x     | https://dev.mysql.com/downloads/mysql/              |
| MySQL Workbench    | Latest    | https://dev.mysql.com/downloads/workbench/          |
| Apache Maven       | 3.9.x     | https://maven.apache.org/download.cgi               |
| IDE                | IntelliJ IDEA / Eclipse / VS Code | Any Java IDE with Servlet support |
| Git                | Latest    | https://git-scm.com/downloads                       |

### 3.3 Environment Setup Steps

#### Step 1: Install Java JDK 17+
```bash
# Verify installation
java -version
javac -version

# Set JAVA_HOME environment variable
# Windows: System Properties → Environment Variables
# JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-17.x.x
```

#### Step 2: Install and Configure Apache Tomcat 10.1
```bash
# Download and extract Tomcat to a directory (e.g., C:\apache-tomcat-10.1.x)
# Set CATALINA_HOME environment variable
# CATALINA_HOME = C:\apache-tomcat-10.1.x

# Start Tomcat
cd %CATALINA_HOME%\bin
startup.bat          # Windows
./startup.sh         # Linux/Mac

# Verify: Open browser → http://localhost:8080
# You should see the Tomcat welcome page

# Stop Tomcat
shutdown.bat         # Windows
./shutdown.sh        # Linux/Mac
```

#### Step 3: Install MySQL 8.0
```bash
# After installation, create the database
mysql -u root -p

CREATE DATABASE ocean_view_resort;
CREATE USER 'oceanview'@'localhost' IDENTIFIED BY 'OceanView@2026';
GRANT ALL PRIVILEGES ON ocean_view_resort.* TO 'oceanview'@'localhost';
FLUSH PRIVILEGES;
```

#### Step 4: Install Maven
```bash
# Verify installation
mvn -version

# Set M2_HOME environment variable
# M2_HOME = C:\apache-maven-3.9.x
# Add %M2_HOME%\bin to PATH
```

### 3.4 How to Build and Deploy Locally

```bash
# Clone the project
git clone https://github.com/<your-username>/ocean-view-resort.git
cd ocean-view-resort

# Build the WAR file
mvn clean package

# The WAR file is generated at: target/ocean-view-resort.war

# Deploy to Tomcat — Option A: Copy WAR file
copy target\ocean-view-resort.war %CATALINA_HOME%\webapps\

# Start Tomcat
%CATALINA_HOME%\bin\startup.bat

# Access the application
# http://localhost:8080/ocean-view-resort/
```

### 3.5 IDE Integration (IntelliJ IDEA)

1. Open the project as a Maven project
2. Go to **Run → Edit Configurations → + → Tomcat Server → Local**
3. Set **Tomcat Home** to your Tomcat installation directory
4. Under **Deployment** tab → Add Artifact → `ocean-view-resort:war exploded`
5. Set **Application context** to `/ocean-view-resort`
6. Click **Run** — Tomcat starts and the app opens in the browser

### 3.6 IDE Integration (Eclipse)

1. Install Eclipse IDE for Enterprise Java Developers
2. Go to **Window → Preferences → Server → Runtime Environments → Add**
3. Select Apache Tomcat v10.1 → Set installation directory
4. Import project as Maven project
5. Right-click project → **Run As → Run on Server** → Select Tomcat

---

## 4. Architecture — 3-Tier

```
┌──────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                         │
│                                                              │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌──────────────┐  │
│  │  JSP    │  │  HTML   │  │  CSS    │  │  JavaScript  │  │
│  │  Pages  │  │  Forms  │  │  Styles │  │  Validation  │  │
│  └─────────┘  └─────────┘  └─────────┘  └──────────────┘  │
├──────────────────────────────────────────────────────────────┤
│                    BUSINESS LOGIC LAYER                       │
│                                                              │
│  ┌───────────┐  ┌───────────┐  ┌────────────────────────┐  │
│  │ Servlets  │  │ Services  │  │ Design Patterns        │  │
│  │ (Control) │  │ (Logic)   │  │ (Strategy, Factory...) │  │
│  └───────────┘  └───────────┘  └────────────────────────┘  │
│                                                              │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────────┐    │
│  │ Filters    │  │ REST APIs    │  │ Observers        │    │
│  │ (Auth)     │  │ (Web Svc)    │  │ (Notifications)  │    │
│  └────────────┘  └──────────────┘  └─────────────────┘    │
├──────────────────────────────────────────────────────────────┤
│                    DATA ACCESS LAYER                          │
│                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │ DAO Classes  │  │ JDBC Driver  │  │ Connection Pool  │  │
│  │              │  │ (java.sql)   │  │ (Singleton)      │  │
│  └──────┬───────┘  └──────┬───────┘  └────────┬─────────┘  │
│         │                 │                     │            │
│         └─────────────────┼─────────────────────┘            │
│                           │                                  │
│                    ┌──────▼──────┐                           │
│                    │   MySQL 8   │                           │
│                    │  Database   │                           │
│                    └─────────────┘                           │
└──────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities:

| Layer | Components | Responsibility |
|-------|-----------|----------------|
| **Presentation** | JSP, HTML, CSS, JS | User interface, form rendering, client-side validation |
| **Business Logic** | Servlets, Services, Filters | Request handling, business rules, authentication, API |
| **Data Access** | DAOs, JDBC, Connection Pool | Database operations, SQL queries, data persistence |

---

## 5. Features — Core & Additional

### 5.1 Core Features (From Assignment Brief)

| # | Feature | Description | Servlet | JSP View |
|---|---------|-------------|---------|----------|
| 1 | **User Authentication (Login)** | Username/password login with session management | `LoginServlet` | `login.jsp` |
| 2 | **Add New Reservation** | Register guest details + booking info | `ReservationServlet` | `reservation-form.jsp` |
| 3 | **Display Reservation Details** | Search and view reservation by number | `ReservationServlet` | `reservation-details.jsp` |
| 4 | **Calculate & Print Bill** | Compute cost (nights × rate) + taxes, printable view | `BillServlet` | `bill.jsp` |
| 5 | **Help Section** | Usage guidelines for new staff | `HelpServlet` | `help.jsp` |
| 6 | **Exit / Logout** | Invalidate session and redirect to login | `LogoutServlet` | redirect to `login.jsp` |

### 5.2 Additional Features (For Higher Marks)

| # | Feature | Description | Benefit |
|---|---------|-------------|---------|
| 7 | **Room Management** | CRUD for rooms — add, edit, delete, view all rooms with status | Demonstrates full data management |
| 8 | **Guest Management** | Separate guest profiles; view all guests, edit details | Better data normalization |
| 9 | **Dashboard** | Overview page with stats: occupancy rate, today's check-ins/outs, total revenue, recent reservations | Sophisticated UI (excellent marks) |
| 10 | **Search & Filter Reservations** | Search by guest name, date range, room type, status | User-friendly interface |
| 11 | **Email Confirmation** | Send booking confirmation email to guest using Jakarta Mail | Complex functionality (excellent marks) |
| 12 | **Reports Module** | Occupancy report, revenue report, guest history, check-in/out report | Decision-making reports (excellent marks) |
| 13 | **Reservation Status Tracking** | Lifecycle: Confirmed → Checked-In → Checked-Out / Cancelled | Real-world workflow |
| 14 | **Audit Trail / Logging** | Log all reservation actions (created, modified, cancelled) with timestamps | Data integrity |
| 15 | **User Management (Admin)** | Admin can create/edit/delete staff accounts, assign roles | Role-based access control |
| 16 | **Room Availability Calendar** | Visual calendar showing which rooms are available on which dates | Prevents double-booking |
| 17 | **Export Bills to PDF** | Generate printable PDF bill using iText library | Professional billing |
| 18 | **Input Validation** | Both server-side (Java) and client-side (JavaScript) validation | Data integrity |
| 19 | **Pagination** | Paginated tables for reservation/guest lists | Better UI for large data |
| 20 | **Session Timeout** | Auto-logout after inactivity period | Security best practice |

### 5.3 Feature-Servlet Mapping (Complete)

| Servlet | URL Pattern | Methods | Purpose |
|---------|------------|---------|---------|
| `LoginServlet` | `/login` | GET, POST | Display login form, authenticate user |
| `LogoutServlet` | `/logout` | GET | Invalidate session, redirect to login |
| `DashboardServlet` | `/dashboard` | GET | Display dashboard with statistics |
| `ReservationServlet` | `/reservations/*` | GET, POST | List, create, view, update reservations |
| `GuestServlet` | `/guests/*` | GET, POST | List, create, view, update guests |
| `RoomServlet` | `/rooms/*` | GET, POST | List, create, view, update rooms |
| `BillServlet` | `/bills/*` | GET, POST | Generate, view, print bills |
| `ReportServlet` | `/reports/*` | GET | Generate and display reports |
| `UserServlet` | `/admin/users/*` | GET, POST | Admin: manage user accounts |
| `HelpServlet` | `/help` | GET | Display help/guidelines page |
| `ReservationApiServlet` | `/api/reservations/*` | GET, POST, PUT, DELETE | REST API for reservations |
| `RoomApiServlet` | `/api/rooms/*` | GET, POST, PUT | REST API for rooms |
| `GuestApiServlet` | `/api/guests/*` | GET, POST, PUT | REST API for guests |
| `BillApiServlet` | `/api/bills/*` | GET, POST | REST API for bills |

---

## 6. Design Patterns

### 6.1 Singleton Pattern — `DBConnection`

**Purpose**: Ensure only one database connection pool exists throughout the application lifetime.

```
┌──────────────────────────────────┐
│         DBConnection             │
├──────────────────────────────────┤
│ - instance: DBConnection         │
│ - connection: Connection         │
│ - url: String                    │
│ - username: String               │
│ - password: String               │
├──────────────────────────────────┤
│ - DBConnection()                 │
│ + getInstance(): DBConnection    │
│ + getConnection(): Connection    │
│ + closeConnection(): void        │
└──────────────────────────────────┘
```

**Where Used**: `DBConnection.getInstance().getConnection()` — called by all DAO classes to obtain a database connection.

**Why**: Prevents creating multiple database connections, which would exhaust MySQL's connection limit and degrade performance.

---

### 6.2 Factory Method Pattern — `RoomFactory`

**Purpose**: Create different Room objects based on room type without exposing creation logic.

```
         ┌────────────────┐
         │  RoomFactory   │
         │ +createRoom()  │
         └───────┬────────┘
                 │ creates
    ┌────────────┼────────────┐
    ▼            ▼            ▼
┌────────┐ ┌──────────┐ ┌─────────┐
│Standard│ │  Deluxe  │ │  Suite  │
│  Room  │ │  Room    │ │  Room   │
└────────┘ └──────────┘ └─────────┘
```

**Where Used**: When creating new room entries — the factory determines rate_per_night, default amenities, and max occupancy based on the type.

**Why**: Encapsulates complex object creation logic. Adding a new room type (e.g., "Presidential Suite") requires changes only in the factory.

---

### 6.3 Builder Pattern — `ReservationBuilder`

**Purpose**: Construct complex Reservation objects step-by-step with optional fields.

```java
Reservation reservation = new ReservationBuilder()
    .setGuestId(guestId)
    .setRoomId(roomId)
    .setCheckInDate(checkIn)
    .setCheckOutDate(checkOut)
    .setStatus(ReservationStatus.CONFIRMED)
    .build();
```

**Where Used**: `ReservationServlet` and `ReservationApiServlet` when creating/updating reservations.

**Why**: Reservation has many fields — Builder avoids telescoping constructors and makes code readable.

---

### 6.4 DAO (Data Access Object) Pattern

**Purpose**: Separate database access logic from business logic.

```
┌──────────────┐        ┌──────────────┐        ┌────────────┐
│   Servlet    │───────▶│   Service    │───────▶│    DAO     │───────▶ MySQL
│ (Controller) │        │   (Logic)    │        │  (JDBC)    │
└──────────────┘        └──────────────┘        └────────────┘
```

**DAO Classes**:
- `UserDAO` — CRUD for users table
- `RoomDAO` — CRUD for rooms table
- `GuestDAO` — CRUD for guests table
- `ReservationDAO` — CRUD for reservations table
- `BillDAO` — CRUD for bills table

**Why**: If we ever switch from MySQL to PostgreSQL, only the DAO layer changes — Servlets and Services remain untouched.

---

### 6.5 MVC (Model-View-Controller) Pattern

**Purpose**: Separate concerns into three components.

```
User Request
     │
     ▼
┌──────────┐     ┌──────────┐     ┌──────────┐
│  View    │◀────│Controller│────▶│  Model   │
│  (JSP)   │     │(Servlet) │     │(Service  │
│          │     │          │     │ + DAO)   │
└──────────┘     └──────────┘     └──────────┘
```

- **Model**: Service classes + DAO classes + POJO entities
- **View**: JSP pages
- **Controller**: Servlets

**Where Used**: The entire application follows this pattern. Every user action flows through a Servlet (controller), which calls a Service (model), and forwards to a JSP (view).

---

### 6.6 Strategy Pattern — `BillingStrategy`

**Purpose**: Different billing calculation algorithms for different room types and seasonal pricing.

```
        ┌──────────────────────┐
        │  BillingStrategy     │ (Interface)
        │  +calculateCost()    │
        └──────────┬───────────┘
                   │ implements
     ┌─────────────┼──────────────┐
     ▼             ▼              ▼
┌──────────┐ ┌──────────┐ ┌──────────┐
│ Standard │ │  Deluxe  │ │  Suite   │
│ Billing  │ │  Billing │ │  Billing │
│ $100/n   │ │  $200/n  │ │  $350/n  │
└──────────┘ └──────────┘ └──────────┘
```

**Where Used**: `BillingService.calculateBill()` — selects the strategy based on room type.

**Why**: Adding peak season pricing or promotional rates requires only a new strategy class, not modifying existing code (Open/Closed Principle).

---

### 6.7 Observer Pattern — `ReservationObserver`

**Purpose**: Notify interested parties (email service, audit logger) when a reservation event occurs.

```
┌───────────────────┐      notifies      ┌──────────────────┐
│ ReservationSubject │────────────────────▶│ EmailNotifier    │
│ (Observable)       │                    └──────────────────┘
│                    │      notifies      ┌──────────────────┐
│                    │────────────────────▶│ AuditLogger      │
└───────────────────┘                     └──────────────────┘
```

**Where Used**: When a reservation is created, updated, or cancelled — the `ReservationSubject` notifies all registered observers.

**Why**: Adding new notification channels (e.g., SMS) requires only implementing a new observer — no changes to existing code.

---

### 6.8 Front Controller Pattern

**Purpose**: Single entry point for request filtering and routing.

```
All Requests ──▶ AuthFilter ──▶ Appropriate Servlet
                     │
                     ▼ (if not logged in)
                  login.jsp
```

**Where Used**: `AuthFilter` — a Servlet Filter that intercepts all requests, checks for valid session, and redirects to login if needed.

---

### 6.9 DTO (Data Transfer Object) Pattern

**Purpose**: Transfer data between layers without exposing internal entity structure.

**Where Used**: API responses — `ReservationDTO`, `BillDTO` contain only the fields needed for the response, not internal IDs or sensitive data.

---

## 7. Database Design

### 7.1 Entity-Relationship Overview

```
users ────────── (no FK relationships, standalone)

rooms ◀──────────── reservations ──────────▶ guests
  (1)         (many)              (many)         (1)

reservations ◀──────────── bills
     (1)              (1)
```

### 7.2 Table Definitions

#### `users` Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,           -- SHA-256 hashed
    role ENUM('ADMIN', 'RECEPTIONIST') NOT NULL DEFAULT 'RECEPTIONIST',
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### `rooms` Table
```sql
CREATE TABLE rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    room_type ENUM('STANDARD', 'DELUXE', 'SUITE') NOT NULL,
    rate_per_night DECIMAL(10, 2) NOT NULL,
    max_occupancy INT NOT NULL DEFAULT 2,
    description TEXT,
    status ENUM('AVAILABLE', 'OCCUPIED', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### `guests` Table
```sql
CREATE TABLE guests (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    nic_passport VARCHAR(20) NOT NULL,
    nationality VARCHAR(50) DEFAULT 'Sri Lankan',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### `reservations` Table
```sql
CREATE TABLE reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_number VARCHAR(20) NOT NULL UNIQUE,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    num_guests INT NOT NULL DEFAULT 1,
    special_requests TEXT,
    status ENUM('CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (guest_id) REFERENCES guests(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);
```

#### `bills` Table
```sql
CREATE TABLE bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT NOT NULL UNIQUE,
    num_nights INT NOT NULL,
    rate_per_night DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    service_charge DECIMAL(10, 2) NOT NULL,       -- 10%
    tourism_levy DECIMAL(10, 2) NOT NULL,          -- 2%
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);
```

#### `audit_log` Table
```sql
CREATE TABLE audit_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id INT NOT NULL,
    performed_by INT,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (performed_by) REFERENCES users(id)
);
```

### 7.3 Stored Procedures

```sql
-- Calculate bill for a reservation
DELIMITER //
CREATE PROCEDURE sp_calculate_bill(IN p_reservation_id INT)
BEGIN
    DECLARE v_nights INT;
    DECLARE v_rate DECIMAL(10,2);
    DECLARE v_subtotal DECIMAL(10,2);
    DECLARE v_service_charge DECIMAL(10,2);
    DECLARE v_tourism_levy DECIMAL(10,2);
    DECLARE v_total DECIMAL(10,2);

    SELECT DATEDIFF(r.check_out_date, r.check_in_date), rm.rate_per_night
    INTO v_nights, v_rate
    FROM reservations r
    JOIN rooms rm ON r.room_id = rm.id
    WHERE r.id = p_reservation_id;

    SET v_subtotal = v_nights * v_rate;
    SET v_service_charge = v_subtotal * 0.10;
    SET v_tourism_levy = v_subtotal * 0.02;
    SET v_total = v_subtotal + v_service_charge + v_tourism_levy;

    INSERT INTO bills (reservation_id, num_nights, rate_per_night, subtotal, service_charge, tourism_levy, total_amount)
    VALUES (p_reservation_id, v_nights, v_rate, v_subtotal, v_service_charge, v_tourism_levy, v_total)
    ON DUPLICATE KEY UPDATE
        num_nights = v_nights,
        rate_per_night = v_rate,
        subtotal = v_subtotal,
        service_charge = v_service_charge,
        tourism_levy = v_tourism_levy,
        total_amount = v_total;
END //
DELIMITER ;
```

### 7.4 Triggers

```sql
-- Auto-update room status when reservation status changes
DELIMITER //
CREATE TRIGGER trg_update_room_status
AFTER UPDATE ON reservations
FOR EACH ROW
BEGIN
    IF NEW.status = 'CHECKED_IN' THEN
        UPDATE rooms SET status = 'OCCUPIED' WHERE id = NEW.room_id;
    ELSEIF NEW.status = 'CHECKED_OUT' OR NEW.status = 'CANCELLED' THEN
        UPDATE rooms SET status = 'AVAILABLE' WHERE id = NEW.room_id;
    END IF;
END //
DELIMITER ;
```

### 7.5 Functions

```sql
-- Get occupancy rate for a specific date
DELIMITER //
CREATE FUNCTION fn_get_occupancy_rate(p_date DATE)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    DECLARE v_total_rooms INT;
    DECLARE v_occupied_rooms INT;

    SELECT COUNT(*) INTO v_total_rooms FROM rooms WHERE status != 'MAINTENANCE';

    SELECT COUNT(DISTINCT room_id) INTO v_occupied_rooms
    FROM reservations
    WHERE p_date BETWEEN check_in_date AND DATE_SUB(check_out_date, INTERVAL 1 DAY)
    AND status IN ('CONFIRMED', 'CHECKED_IN');

    IF v_total_rooms = 0 THEN
        RETURN 0.00;
    END IF;

    RETURN (v_occupied_rooms / v_total_rooms) * 100;
END //
DELIMITER ;
```

### 7.6 Seed Data

```sql
-- Default admin user (password: admin123, SHA-256 hashed)
INSERT INTO users (username, password, role, full_name) VALUES
('admin', '<sha256_hash_of_admin123>', 'ADMIN', 'System Administrator'),
('receptionist1', '<sha256_hash_of_recep123>', 'RECEPTIONIST', 'Nimal Perera');

-- Sample rooms
INSERT INTO rooms (room_number, room_type, rate_per_night, max_occupancy, description) VALUES
('101', 'STANDARD', 100.00, 2, 'Ground floor standard room with garden view'),
('102', 'STANDARD', 100.00, 2, 'Ground floor standard room with pool view'),
('201', 'DELUXE', 200.00, 3, 'First floor deluxe room with ocean view'),
('202', 'DELUXE', 200.00, 3, 'First floor deluxe room with balcony'),
('301', 'SUITE', 350.00, 4, 'Top floor luxury suite with ocean panorama'),
('302', 'SUITE', 350.00, 4, 'Top floor luxury suite with private terrace');
```

---

## 8. Project Structure

```
ocean-view-resort/
│
├── pom.xml                                     # Maven build configuration
├── README.md                                   # Project description
├── PROJECT_PLAN.md                             # This document
│
├── .github/
│   └── workflows/
│       └── ci.yml                              # GitHub Actions CI/CD pipeline
│
├── sql/
│   ├── schema.sql                              # CREATE TABLE statements
│   ├── seed-data.sql                           # Initial data (rooms, admin user)
│   ├── stored-procedures.sql                   # Stored procedures
│   ├── triggers.sql                            # Triggers
│   └── functions.sql                           # Functions
│
├── src/
│   ├── main/
│   │   ├── java/com/oceanview/resort/
│   │   │   │
│   │   │   ├── controller/                     # === SERVLETS (Controllers) ===
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── LogoutServlet.java
│   │   │   │   ├── DashboardServlet.java
│   │   │   │   ├── ReservationServlet.java
│   │   │   │   ├── GuestServlet.java
│   │   │   │   ├── RoomServlet.java
│   │   │   │   ├── BillServlet.java
│   │   │   │   ├── ReportServlet.java
│   │   │   │   ├── UserServlet.java
│   │   │   │   └── HelpServlet.java
│   │   │   │
│   │   │   ├── api/                            # === REST API SERVLETS ===
│   │   │   │   ├── ReservationApiServlet.java
│   │   │   │   ├── RoomApiServlet.java
│   │   │   │   ├── GuestApiServlet.java
│   │   │   │   └── BillApiServlet.java
│   │   │   │
│   │   │   ├── model/                          # === POJO ENTITIES ===
│   │   │   │   ├── User.java
│   │   │   │   ├── Room.java
│   │   │   │   ├── Guest.java
│   │   │   │   ├── Reservation.java
│   │   │   │   ├── Bill.java
│   │   │   │   └── AuditLog.java
│   │   │   │
│   │   │   ├── model/enums/                    # === ENUMS ===
│   │   │   │   ├── RoomType.java
│   │   │   │   ├── RoomStatus.java
│   │   │   │   ├── ReservationStatus.java
│   │   │   │   ├── UserRole.java
│   │   │   │   └── PaymentStatus.java
│   │   │   │
│   │   │   ├── model/dto/                      # === DTOs ===
│   │   │   │   ├── ReservationDTO.java
│   │   │   │   ├── DashboardDTO.java
│   │   │   │   └── BillDTO.java
│   │   │   │
│   │   │   ├── dao/                            # === DATA ACCESS OBJECTS ===
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── RoomDAO.java
│   │   │   │   ├── GuestDAO.java
│   │   │   │   ├── ReservationDAO.java
│   │   │   │   ├── BillDAO.java
│   │   │   │   └── AuditLogDAO.java
│   │   │   │
│   │   │   ├── service/                        # === BUSINESS LOGIC ===
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── ReservationService.java
│   │   │   │   ├── RoomService.java
│   │   │   │   ├── GuestService.java
│   │   │   │   ├── BillingService.java
│   │   │   │   ├── ReportService.java
│   │   │   │   └── EmailService.java
│   │   │   │
│   │   │   ├── pattern/                        # === DESIGN PATTERN IMPLEMENTATIONS ===
│   │   │   │   ├── singleton/
│   │   │   │   │   └── DBConnection.java
│   │   │   │   ├── factory/
│   │   │   │   │   └── RoomFactory.java
│   │   │   │   ├── builder/
│   │   │   │   │   └── ReservationBuilder.java
│   │   │   │   ├── strategy/
│   │   │   │   │   ├── BillingStrategy.java          # Interface
│   │   │   │   │   ├── StandardRoomBilling.java
│   │   │   │   │   ├── DeluxeRoomBilling.java
│   │   │   │   │   └── SuiteRoomBilling.java
│   │   │   │   └── observer/
│   │   │   │       ├── ReservationObserver.java       # Interface
│   │   │   │       ├── ReservationSubject.java
│   │   │   │       ├── EmailNotifier.java
│   │   │   │       └── AuditLogger.java
│   │   │   │
│   │   │   ├── filter/                         # === SERVLET FILTERS ===
│   │   │   │   ├── AuthFilter.java
│   │   │   │   └── EncodingFilter.java
│   │   │   │
│   │   │   └── util/                           # === UTILITIES ===
│   │   │       ├── PasswordHasher.java
│   │   │       ├── ValidationUtil.java
│   │   │       ├── ReservationNumberGenerator.java
│   │   │       └── DateUtil.java
│   │   │
│   │   ├── resources/
│   │   │   └── db.properties                   # Database connection config
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml                     # Servlet & filter mappings
│   │       │   └── views/                      # JSP pages (protected)
│   │       │       ├── login.jsp
│   │       │       ├── dashboard.jsp
│   │       │       ├── reservations/
│   │       │       │   ├── list.jsp
│   │       │       │   ├── form.jsp
│   │       │       │   └── details.jsp
│   │       │       ├── guests/
│   │       │       │   ├── list.jsp
│   │       │       │   └── form.jsp
│   │       │       ├── rooms/
│   │       │       │   ├── list.jsp
│   │       │       │   └── form.jsp
│   │       │       ├── bills/
│   │       │       │   ├── generate.jsp
│   │       │       │   └── view.jsp
│   │       │       ├── reports/
│   │       │       │   ├── occupancy.jsp
│   │       │       │   ├── revenue.jsp
│   │       │       │   └── guest-history.jsp
│   │       │       ├── admin/
│   │       │       │   └── users.jsp
│   │       │       ├── help.jsp
│   │       │       └── error.jsp
│   │       │
│   │       ├── css/
│   │       │   └── style.css                   # Custom styles
│   │       ├── js/
│   │       │   ├── validation.js               # Client-side validation
│   │       │   └── dashboard.js                # Dashboard charts
│   │       └── images/
│   │           └── logo.png
│   │
│   └── test/java/com/oceanview/resort/
│       ├── dao/
│       │   ├── UserDAOTest.java
│       │   ├── RoomDAOTest.java
│       │   ├── GuestDAOTest.java
│       │   ├── ReservationDAOTest.java
│       │   └── BillDAOTest.java
│       ├── service/
│       │   ├── AuthServiceTest.java
│       │   ├── ReservationServiceTest.java
│       │   ├── BillingServiceTest.java
│       │   └── RoomServiceTest.java
│       ├── util/
│       │   ├── PasswordHasherTest.java
│       │   ├── ValidationUtilTest.java
│       │   └── ReservationNumberGeneratorTest.java
│       └── pattern/
│           ├── RoomFactoryTest.java
│           ├── ReservationBuilderTest.java
│           └── BillingStrategyTest.java
```

---

## 9. Assumptions

| # | Assumption | Justification |
|---|------------|---------------|
| 1 | **Two user roles**: Admin (full access) and Receptionist (manage reservations, guests, bills only) | Hotels typically have hierarchical access — admin manages system settings, staff handles daily operations |
| 2 | **Room types**: Standard ($100/night), Deluxe ($200/night), Suite ($350/night) | Common hotel room categorization; rates are configurable in the database |
| 3 | **Reservation number format**: `RES-YYYYMMDD-XXXX` (auto-generated, unique) | Easily identifiable, date-encoded, prevents duplicates |
| 4 | **Tax structure**: 10% service charge + 2% tourism development levy | Based on Sri Lankan hotel taxation norms |
| 5 | **One room per reservation** | Simplifies the system; guests needing multiple rooms create multiple reservations |
| 6 | **Check-in time**: 2:00 PM, **Check-out time**: 11:00 AM | Industry standard hotel times |
| 7 | **Staff-only system** — not a guest self-service portal | The system is for hotel reception and management use |
| 8 | **Contact number**: Valid Sri Lankan format (10 digits starting with 0) | Hotels verify guest contact info on registration |
| 9 | **Minimum stay**: 1 night; **Maximum stay**: 30 nights | Prevents accidental zero-night bookings; 30-night cap for practical limits |
| 10 | **Cancellation**: Allowed before check-in date; no refund calculation in scope | Simplification — refund policy would need more complex business rules |
| 11 | **Passwords hashed with SHA-256** | Minimum security standard; passwords never stored in plain text |
| 12 | **Single property system** — designed for Ocean View Resort only | Not a multi-hotel chain system |
| 13 | **6 rooms initially** — 2 Standard, 2 Deluxe, 2 Suite | Small hotel; rooms can be added by admin via Room Management |

---

## 10. Validation Rules

### Server-Side Validation (Java — `ValidationUtil.java`)

| Field | Rules |
|-------|-------|
| **Guest Name** | Required; 2-100 characters; letters and spaces only |
| **Address** | Required; 5-500 characters |
| **Contact Number** | Required; exactly 10 digits; starts with "0" |
| **Email** | Optional; valid email format (regex) |
| **NIC/Passport** | Required; valid Sri Lankan NIC (12 digits or 9 digits + V/X) or passport format |
| **Check-in Date** | Required; must be today or future date |
| **Check-out Date** | Required; must be after check-in date |
| **Stay Duration** | 1 to 30 nights maximum |
| **Room Number** | Required; unique; 1-10 characters |
| **Rate Per Night** | Required; positive decimal; max 99999.99 |
| **Username** | Required; 3-50 characters; alphanumeric + underscore |
| **Password** | Required; minimum 8 characters; at least 1 uppercase, 1 lowercase, 1 digit |
| **Room Type** | Must be one of: STANDARD, DELUXE, SUITE |
| **Reservation Number** | Auto-generated; format `RES-YYYYMMDD-XXXX` |

### Client-Side Validation (JavaScript — `validation.js`)
- Real-time form field validation
- Date picker constraints (min/max dates)
- Phone number formatting
- Required field checks before form submission
- Visual feedback (red borders, error messages)

---

## 11. Reports

| # | Report | Description | Data Source |
|---|--------|-------------|------------|
| 1 | **Daily Occupancy Report** | Rooms occupied vs available for a given date; occupancy percentage | `fn_get_occupancy_rate()` + rooms query |
| 2 | **Revenue Report** | Total revenue by date range, broken down by room type | bills + reservations JOIN |
| 3 | **Guest History Report** | All reservations for a specific guest | reservations WHERE guest_id = ? |
| 4 | **Check-In/Check-Out Report** | Today's expected arrivals and departures | reservations WHERE check_in_date/check_out_date = TODAY |
| 5 | **Room Status Report** | Current status of all rooms (available/occupied/maintenance) | rooms table |
| 6 | **Monthly Summary Report** | Total bookings, revenue, top room types for a month | Aggregated queries |

---

## 12. API Endpoints (Web Services)

### REST API — JSON Responses (Distributed Application Requirement)

#### Reservations API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/reservations` | List all reservations |
| `GET` | `/api/reservations/{id}` | Get reservation by ID |
| `GET` | `/api/reservations?number=RES-...` | Search by reservation number |
| `POST` | `/api/reservations` | Create new reservation |
| `PUT` | `/api/reservations/{id}` | Update reservation |
| `DELETE` | `/api/reservations/{id}` | Cancel reservation |

#### Rooms API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/rooms` | List all rooms |
| `GET` | `/api/rooms/available?checkIn=...&checkOut=...` | Get available rooms for dates |
| `GET` | `/api/rooms/{id}` | Get room by ID |
| `POST` | `/api/rooms` | Add new room |
| `PUT` | `/api/rooms/{id}` | Update room details |

#### Guests API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/guests` | List all guests |
| `GET` | `/api/guests/{id}` | Get guest by ID |
| `POST` | `/api/guests` | Register new guest |
| `PUT` | `/api/guests/{id}` | Update guest info |

#### Bills API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/bills/{reservationId}` | Get bill for reservation |
| `POST` | `/api/bills/generate/{reservationId}` | Generate bill |

### Response Format

```json
{
    "status": "success",
    "data": { ... },
    "message": "Reservation created successfully"
}
```

```json
{
    "status": "error",
    "data": null,
    "message": "Reservation not found"
}
```

---

## 13. Testing Strategy (TDD)

### 13.1 Approach: Test-Driven Development

1. **Write test first** — define expected behavior
2. **Run test** — it fails (RED)
3. **Write minimal code** — to make the test pass
4. **Run test** — it passes (GREEN)
5. **Refactor** — clean up the code
6. **Repeat**

### 13.2 Test Categories

| Category | Tool | Scope | Examples |
|----------|------|-------|---------|
| **Unit Tests** | JUnit 5 + Mockito | Individual methods | Service logic, utility methods, pattern implementations |
| **DAO Tests** | JUnit 5 | Database operations | CRUD operations for each entity |
| **Validation Tests** | JUnit 5 | Input validation | Valid/invalid inputs for all fields |
| **Integration Tests** | JUnit 5 | End-to-end flows | Login → Add Reservation → Generate Bill |

### 13.3 Sample Test Cases

| Test ID | Feature | Test Case | Input | Expected Result |
|---------|---------|-----------|-------|----------------|
| TC-001 | Login | Valid credentials | admin/admin123 | Login success, redirect to dashboard |
| TC-002 | Login | Invalid password | admin/wrong | Error message "Invalid credentials" |
| TC-003 | Login | Empty username | /admin123 | Error message "Username is required" |
| TC-004 | Reservation | Valid reservation | All valid fields | Reservation created with RES-XXXXXXXX-XXXX |
| TC-005 | Reservation | Check-out before check-in | In: Mar 5, Out: Mar 3 | Error "Check-out must be after check-in" |
| TC-006 | Reservation | Past check-in date | In: Jan 1, 2025 | Error "Check-in date must be today or future" |
| TC-007 | Reservation | Room already booked | Overlapping dates | Error "Room not available for selected dates" |
| TC-008 | Bill | Calculate standard room 3 nights | Standard, 3 nights | Subtotal: $300, Tax: $36, Total: $336 |
| TC-009 | Bill | Calculate suite room 1 night | Suite, 1 night | Subtotal: $350, Tax: $42, Total: $392 |
| TC-010 | Guest | Invalid phone number | "12345" | Error "Contact number must be 10 digits" |
| TC-011 | Guest | Valid NIC | "200012345678" | Guest registered successfully |
| TC-012 | Room | Duplicate room number | "101" (exists) | Error "Room number already exists" |
| TC-013 | Strategy | Standard billing | 5 nights | $500 + $60 tax = $560 |
| TC-014 | Factory | Create deluxe room | Type: DELUXE | Room with rate $200, maxOccupancy 3 |
| TC-015 | Builder | Build reservation | All fields set | Valid Reservation object |

### 13.4 Test Data

```
Test Users:
- admin / admin123 (ADMIN)
- testuser / Test@1234 (RECEPTIONIST)

Test Rooms:
- Room 101, STANDARD, $100/night
- Room 201, DELUXE, $200/night
- Room 301, SUITE, $350/night

Test Guests:
- John Silva, 0771234567, 200012345678
- Jane Perera, 0712345678, 199987654321

Test Reservations:
- RES-20260301-0001: John → Room 101, Mar 1-3
- RES-20260305-0002: Jane → Room 301, Mar 5-8
```

---

## 14. Git & Version Control Strategy

### 14.1 Repository Setup
- **Platform**: GitHub (public repository)
- **Repository Name**: `ocean-view-resort`
- **URL**: `https://github.com/<username>/ocean-view-resort`

### 14.2 Branching Strategy

```
main ──────────────────────────────────────────────▶
  │                                                 ▲
  └──▶ develop ────────────────────────────────────▶│
         │       │       │       │       │          │
         │   feature/  feature/  feature/  feature/ │
         │   auth      rooms    billing   reports   │
         │       │       │       │       │          │
         │       └───────┴───────┴───────┘          │
         │              merged back                  │
         └──────────────────────────────────────────┘
                        release
```

- **`main`**: Production-ready code only
- **`develop`**: Integration branch for features
- **`feature/*`**: Individual feature branches

### 14.3 Commit Convention

```
feat: add reservation CRUD functionality
fix: resolve date validation bug in reservation form
test: add unit tests for BillingService
docs: update README with setup instructions
style: format JSP pages and CSS
refactor: extract billing logic into Strategy pattern
```

### 14.4 Version Tags

| Tag | Description |
|-----|-------------|
| `v1.0` | Initial project setup, database schema, basic login |
| `v1.1` | Reservation CRUD, guest management |
| `v1.2` | Room management, billing |
| `v1.3` | REST APIs, reports |
| `v1.4` | Email notifications, dashboard |
| `v1.5` | Testing, bug fixes |
| `v2.0` | Final release with all features |

### 14.5 GitHub Actions CI/CD Pipeline

```yaml
# .github/workflows/ci.yml
name: Ocean View Resort CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package -DskipTests
      - name: Run Tests
        run: mvn test
```

---

## 15. Timeline

| Day | Tasks | Git Activity |
|-----|-------|-------------|
| **Day 1** | Project setup (Maven, folder structure), MySQL schema + seed data, `DBConnection` singleton, all DAO classes | `v1.0` — Initial commit with project setup and DB |
| **Day 2** | Login/Logout servlets, `AuthFilter`, `AuthService`, session management, User management (Admin) | Commit: `feat: user authentication and session management` |
| **Day 3** | Guest CRUD (Servlet + JSP + Service + DAO), Room CRUD, Room Factory pattern | `v1.1` — Guest and room management |
| **Day 4** | Reservation CRUD, `ReservationBuilder`, `ReservationNumberGenerator`, availability check | Commit: `feat: reservation management with builder pattern` |
| **Day 5** | Billing module, Strategy pattern (Standard/Deluxe/Suite), bill generation + print view | `v1.2` — Billing with strategy pattern |
| **Day 6** | REST API servlets (all 4), Dashboard with stats, Swagger/docs for API | Commit: `feat: REST APIs and dashboard` |
| **Day 7** | Reports module (occupancy, revenue, guest history, check-in/out), Observer pattern (email + audit) | `v1.3` — Reports and notifications |
| **Day 8** | Help page, UI polish, client-side validation, error handling, pagination | Commit: `feat: UI improvements and validation` |
| **Day 9** | JUnit tests (TDD), all test classes, test documentation | `v1.5` — Testing complete |
| **Day 10** | UML diagrams, final documentation, CI/CD pipeline, final review | `v2.0` — Final release |

---

## 16. Maven Dependencies (`pom.xml`)

```xml
<dependencies>
    <!-- Jakarta Servlet API -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Jakarta Servlet JSP API -->
    <dependency>
        <groupId>jakarta.servlet.jsp</groupId>
        <artifactId>jakarta.servlet.jsp-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSTL (JSP Standard Tag Library) -->
    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
        <version>3.0.1</version>
    </dependency>

    <!-- MySQL JDBC Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

    <!-- Gson (JSON processing) -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>

    <!-- Jakarta Mail (Email) -->
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>jakarta.mail</artifactId>
        <version>2.0.1</version>
    </dependency>

    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>

    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.8.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 17. Key URLs (After Deployment)

| URL | Page |
|-----|------|
| `http://localhost:8080/ocean-view-resort/login` | Login Page |
| `http://localhost:8080/ocean-view-resort/dashboard` | Dashboard |
| `http://localhost:8080/ocean-view-resort/reservations` | Reservation List |
| `http://localhost:8080/ocean-view-resort/reservations/new` | New Reservation Form |
| `http://localhost:8080/ocean-view-resort/guests` | Guest List |
| `http://localhost:8080/ocean-view-resort/rooms` | Room List |
| `http://localhost:8080/ocean-view-resort/bills` | Bills |
| `http://localhost:8080/ocean-view-resort/reports` | Reports |
| `http://localhost:8080/ocean-view-resort/admin/users` | User Management (Admin) |
| `http://localhost:8080/ocean-view-resort/help` | Help Section |
| `http://localhost:8080/ocean-view-resort/api/reservations` | REST API |

---

*Document prepared for Ocean View Resort Online Room Reservation System — Cardiff Met University Assignment*
