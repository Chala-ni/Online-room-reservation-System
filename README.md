# Ocean View Resort - Room Reservation System

An online room reservation system built with pure Java (Servlets, JSP, JDBC) for the Ocean View Resort hotel. This is a university assignment project for Cardiff Metropolitan University.

## Features

- **User Authentication** - Role-based access (Admin, Receptionist)
- **Reservation Management** - Create, edit, check-in, check-out, cancel
- **Guest Management** - CRUD operations with validation
- **Room Management** - Room types (Standard, Deluxe, Suite) with status tracking
- **Billing System** - Automated bill generation with service charges
- **Reports & Dashboard** - Occupancy, revenue, and reservation reports
- **REST API** - JSON endpoints for AJAX integration
- **Audit Trail** - Action logging via Observer pattern

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Web Server | Apache Tomcat 10.1 |
| Backend | Jakarta Servlet 6.0, JSP |
| Database | MySQL 8.0, JDBC |
| Frontend | Bootstrap 5 (CDN), Vanilla JS |
| Build | Maven |
| Testing | JUnit 5, Mockito |
| CI/CD | GitHub Actions |

## Design Patterns

1. **Singleton** - Database connection management
2. **Factory** - Room creation with type-specific defaults
3. **Builder** - Complex reservation object construction
4. **DAO** - Data access layer abstraction
5. **MVC** - Model-View-Controller architecture
6. **Strategy** - Billing calculation (Standard/Deluxe/Suite)
7. **Observer** - Event notifications (email, audit log)
8. **Front Controller** - Authentication filter
9. **DTO** - Data transfer objects

## Project Structure

```
src/
├── main/
│   ├── java/com/oceanview/resort/
│   │   ├── api/            # REST API servlets
│   │   ├── controller/     # Servlet controllers (MVC)
│   │   ├── dao/            # Data Access Objects
│   │   ├── filter/         # Servlet filters
│   │   ├── model/          # POJOs and enums
│   │   ├── pattern/        # Design pattern implementations
│   │   ├── service/        # Business logic layer
│   │   └── util/           # Utility classes
│   ├── resources/
│   │   └── db.properties   # Database config
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── tags/       # JSP tag files (layout)
│       │   ├── views/      # JSP pages
│       │   └── web.xml     # Deployment descriptor
│       ├── css/            # Stylesheets
│       └── js/             # JavaScript
├── test/                   # JUnit test classes
└── sql/                    # Database scripts
```

## Setup Instructions

### Prerequisites
- Java 17+ (JDK)
- Apache Tomcat 10.1+
- MySQL 8.0+
- Maven 3.8+

### Database Setup
```bash
# Login to MySQL
mysql -u root -p

# Run schema
source sql/schema.sql;

# Load seed data
source sql/seed-data.sql;

# Load stored procedures
source sql/stored-procedures.sql;

# Load triggers and functions
source sql/triggers-and-functions.sql;
```

### Build & Deploy
```bash
# Build the WAR file
mvn clean package

# Copy WAR to Tomcat
cp target/ocean-view-resort.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh
```

### Access
- URL: `http://localhost:8080/ocean-view-resort`
- Admin: `admin` / `admin123`
- Receptionist: `receptionist1` / `recep123`

## Running Tests
```bash
mvn test
```

## License
This project is for educational purposes only - Cardiff Metropolitan University.
