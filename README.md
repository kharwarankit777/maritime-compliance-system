# 🚢 Maritime Compliance System

A full-stack maritime operations and compliance management platform built with **React** and **Spring Boot**. It enables ship admins to manage maintenance tasks and safety drills, crew members to log activities, and automatically tracks compliance across ships.

🔗 **Live Demo:** [maritime-compliance-system.vercel.app](https://maritime-compliance-system.vercel.app)  
📁 **Repository:** [github.com/kharwarankit777/maritime-compliance-system](https://github.com/kharwarankit777/maritime-compliance-system)

---

## 📸 Screenshots

### Login Page
<img width="1920" alt="Login" src="https://github.com/user-attachments/assets/33b21982-5301-4133-a396-1caa5c59e660" />

### Compliance Dashboard
<img width="1920" alt="Dashboard" src="https://github.com/user-attachments/assets/ec6d30bd-fbb8-4e2a-814b-0f182028afad" />
<img width="1920" alt="Dashboard 2" src="https://github.com/user-attachments/assets/5095bb68-b19b-4084-9c4d-924351322677" />

### Maintenance Module
<img width="1920" alt="Maintenance" src="https://github.com/user-attachments/assets/31e2f3ed-38b6-4cc2-acde-17b780939b5c" />

### Drill Module
<img width="1920" alt="Drills" src="https://github.com/user-attachments/assets/7791e1f5-b66a-431f-b1c9-8ac845d9b906" />

---

## ✨ Features

- 🔐 **JWT Authentication** with BCrypt password hashing
- 👥 **Role-Based Access Control** — ADMIN and CREW roles
- 🔧 **Maintenance Task Management** — create, assign, update tasks with due dates
- 🚨 **Safety Drill Management** — schedule drills, mark attendance
- 📊 **Compliance Dashboard** — real-time compliance % with Pie charts
- ⚠️ **Overdue & Missed Detection** — automatically flags non-compliant tasks/drills
- 👷 **Crew Dashboard** — crew views their own tasks and upcoming drills

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│                   React Frontend                     │
│  AuthContext → ProtectedRoute → Pages → Axios API   │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP + JWT Bearer Token
┌─────────────────────▼───────────────────────────────┐
│              Spring Boot Backend (Port 8080)         │
│   Controller → Service → Repository → MySQL DB      │
│                                                      │
│  JwtFilter → SecurityConfig → BCryptPasswordEncoder │
└─────────────────────────────────────────────────────┘
```

**Separation of concerns:**
- **Controller layer** — handles HTTP requests, role checks, returns DTOs
- **Service layer** — all business logic (compliance calculation, overdue detection)
- **Repository layer** — Spring Data JPA interfaces, no SQL written manually
- **Security layer** — stateless JWT filter, no sessions

**Compliance Calculation Logic:**
```
Maintenance Compliance % = (Completed Tasks / Total Tasks) × 100
Drill Compliance %       = (Attended Drills / Total Drill Records) × 100
Overall Compliance %     = Average of both
Overdue                  = dueDate < today AND status != COMPLETED
Missed Drill             = scheduledDate < today AND attended = false
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React.js, Axios, Context API, Recharts |
| Backend | Spring Boot 3.2, Spring Security, Hibernate/JPA |
| Auth | JWT (jjwt 0.11.5), BCrypt |
| Database | MySQL |
| Deployment | Vercel (frontend) |

---

## 📁 Project Structure

```
maritime-compliance-system/
├── src/
│   ├── main/java/com/maritime/
│   │   ├── controller/        # REST API endpoints
│   │   │   ├── AuthController.java
│   │   │   ├── MaintenanceController.java
│   │   │   ├── DrillController.java
│   │   │   ├── ComplianceController.java
│   │   │   └── ShipController.java
│   │   ├── service/           # Business logic
│   │   ├── model/             # JPA entities
│   │   ├── repository/        # Spring Data JPA
│   │   ├── dto/               # Request/Response objects
│   │   ├── security/          # JWT filter & config
│   │   └── enums/             # Role, TaskStatus
│   └── (React frontend files also in src/)
│       ├── pages/             # Dashboard, Maintenance, Drills, Crew
│       ├── components/        # Navbar
│       ├── context/           # AuthContext
│       └── api/               # Axios instance with interceptors
└── pom.xml
```

---

## 🗄️ Database Schema

```
users            → id, name, email, password, role (ADMIN/CREW), ship_id
ships            → id, name, registration_number
maintenance_tasks→ id, title, description, ship_id, assigned_to_id, created_by_id, status, due_date, completed_at, notes
safety_drills    → id, title, drill_type, ship_id, scheduled_date, created_by_id
drill_attendance → id, drill_id, user_id, attended, submitted_at
```

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register user |
| POST | `/api/auth/login` | Login, returns JWT |
| GET | `/api/auth/me` | Get current user info |

### Maintenance
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/maintenance/tasks` | ADMIN | Create task |
| GET | `/api/maintenance/tasks` | ADMIN | Get all tasks |
| GET | `/api/maintenance/tasks/my` | CREW | Get my assigned tasks |
| GET | `/api/maintenance/tasks/ship/{id}` | ANY | Tasks by ship |
| PUT | `/api/maintenance/tasks/{id}` | ANY | Update task status/notes |
| GET | `/api/maintenance/tasks/overdue` | ANY | Get overdue tasks |

### Safety Drills
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/drills` | ADMIN | Schedule a drill |
| GET | `/api/drills` | ANY | Get all drills |
| GET | `/api/drills/upcoming` | ANY | Get upcoming drills |
| GET | `/api/drills/ship/{id}` | ANY | Drills by ship |
| POST | `/api/drills/{id}/attend` | CREW | Mark attendance |

### Compliance
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/compliance/dashboard` | Full compliance metrics |

---

## ⚙️ Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- Node.js 18+

### Backend Setup

1. Create MySQL database:
```sql
CREATE DATABASE maritime_db;
```

2. Configure `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/maritime_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
jwt.secret=your_secret_key_min_32_chars
jwt.expiration=86400000
```

3. Run the backend:
```bash
mvn spring-boot:run
```
Backend starts at `http://localhost:8080`

### Frontend Setup

1. Install dependencies:
```bash
npm install
```

2. Update API base URL in `src/api/axios.js` if needed:
```js
baseURL: 'http://localhost:8080'
```

3. Start the React app:
```bash
npm start
```
Frontend runs at `http://localhost:3000`

---

## 🧪 Test Credentials (Demo)

You can register via `/api/auth/register` with role `ADMIN` or `CREW`.

Example payload:
```json
{
  "name": "Admin User",
  "email": "admin@maritime.com",
  "password": "password123",
  "role": "ADMIN",
  "shipId": 1
}
```

---

## 🏛️ Architecture Decisions

**Why Spring Boot?**
Mature ecosystem for building secure REST APIs. Spring Security + JWT gives out-of-the-box stateless auth, and JPA/Hibernate removes boilerplate DB code.

**Why JWT over sessions?**
Stateless authentication scales horizontally — no session store needed. Token carries role info so each request is self-contained.

**Why React Context API over Redux?**
Auth state is simple (user + token). Redux would be over-engineering for this scope. Context + localStorage gives clean global state without added complexity.

**Why MySQL?**
Relational data with clear foreign key relationships (Ship → Tasks, Drill → Attendance). Joins and compliance aggregation queries are natural in SQL.

**Compliance as a service:**
`ComplianceService` is fully decoupled — it reads from `MaintenanceTaskRepository` and `DrillAttendanceRepository` independently. This means compliance logic can be extended (per-ship, per-crew, date-ranged) without touching controllers.

---

## 👤 Author

**Ankit Kharwar**  
[GitHub](https://github.com/kharwarankit777)
