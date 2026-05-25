# 🛡️ Security Incident Management System

A Spring Boot 3.x web application for tracking and managing cybersecurity
incidents, assigning them to analysts, categorizing by attack type, linking
to affected assets, and enforcing SLA-based escalation policies.

Built as a Programming 4 assessment project at the University of Pécs,
Faculty of Engineering and Information Technology.

---

## 🚀 Running the Application

### Prerequisites
- Java 21+
- IntelliJ IDEA (recommended)

### Steps
1. Clone the repository
   ```bash
   git clone https://github.com/Vasuuu641/incident-manager.git
   cd incident-manager
   ```
2. Open in IntelliJ IDEA
3. Wait for Maven to finish downloading dependencies
4. Run `IncidentManagerApplication.java`
5. Open `http://localhost:8080` in your browser

### Default Credentials
| Username | Password | Role |
|---|---|---|
| analyst | password | USER |
| admin | admin | ADMIN |

### H2 Database Console
Available at `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:incidentdb`
- Username: `sa`
- Password: (empty)

---

## 🐳 Running with Docker

### Prerequisites
- Docker installed and running

### Build and Run
```bash
# Build the Docker image
docker build -t incident-manager .

# Run the container
docker run -p 8080:8080 incident-manager
```

Open `http://localhost:8080` in your browser. Same credentials apply.

### Stop the Container
```bash
# List running containers
docker ps

# Stop by container ID
docker stop <container_id>
```

---

## ✨ Features

- **Incident tracking** — create, view, edit, and delete security incidents
- **Analyst assignment** — assign incidents to security analysts
- **Tag categorization** — label incidents by attack type (DDoS, Phishing, Ransomware)
- **Detailed reports** — attach findings and recommendations to each incident
- **Asset tracking** — link affected systems and devices to incidents
- **SLA enforcement** — assign response time policies per severity level with automatic escalation
- **Role-based access** — analysts access the web UI, admins access the REST API
- **Sample data** — pre-seeded incidents, analysts, tags, and SLA policies on startup

---

## 🗄️ Data Model

### Entities and JPA Relationships

| Relationship | Between | Description |
|---|---|---|
| OneToOne | Incident → IncidentReport | Each incident has one detailed report |
| ManyToOne | Incident → Analyst | Many incidents assigned to one analyst |
| OneToMany | Analyst → Incidents | One analyst handles many incidents |
| ManyToOne | Incident → SlaPolicy | Many incidents governed by one SLA policy |
| OneToMany | SlaPolicy → Incidents | One policy applies to many incidents |
| OneToMany | Incident → Assets | One incident has many affected assets |
| ManyToMany | Incident ↔ Tag | Incidents share tags with other incidents |

---

## ⏱️ SLA Escalation System

Each incident is assigned an SLA policy based on severity. The policy defines:
- **Resolution hours** — maximum time to resolve before SLA breach
- **Escalation hours** — time before automatic escalation

A scheduled job runs every 60 seconds checking all active incidents.
If the current time exceeds the SLA deadline, the incident is automatically
escalated — status changes to `ESCALATED` and a red badge appears on the
incident list.

| Severity | Resolution | Escalation |
|---|---|---|
| CRITICAL | 4 hrs | 2 hrs |
| HIGH | 24 hrs | 12 hrs |
| MEDIUM | 72 hrs | 48 hrs |
| LOW | 168 hrs | 120 hrs |

---

## 🌐 REST API

All entities are accessible via REST endpoints returning JSON.
REST API requires ADMIN role — use Basic Auth with `admin` / `admin`.

### Incidents
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/incidents` | Get all incidents |
| GET | `/api/incidents/{id}` | Get incident by ID |
| POST | `/api/incidents` | Create new incident |
| PUT | `/api/incidents/{id}` | Update incident |
| DELETE | `/api/incidents/{id}` | Delete incident |

### Analysts
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/analysts` | Get all analysts |
| GET | `/api/analysts/{id}` | Get analyst by ID |
| POST | `/api/analysts` | Create new analyst |
| PUT | `/api/analysts/{id}` | Update analyst |
| DELETE | `/api/analysts/{id}` | Delete analyst |

### Tags
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tags` | Get all tags |
| GET | `/api/tags/{id}` | Get tag by ID |
| POST | `/api/tags` | Create new tag |
| PUT | `/api/tags/{id}` | Update tag |
| DELETE | `/api/tags/{id}` | Delete tag |

### Assets
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/assets` | Get all assets |
| GET | `/api/assets/{id}` | Get asset by ID |
| POST | `/api/assets` | Create new asset |
| PUT | `/api/assets/{id}` | Update asset |
| DELETE | `/api/assets/{id}` | Delete asset |

### SLA Policies
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/sla-policies` | Get all SLA policies |
| GET | `/api/sla-policies/{id}` | Get policy by ID |
| POST | `/api/sla-policies` | Create new policy |
| PUT | `/api/sla-policies/{id}` | Update policy |
| DELETE | `/api/sla-policies/{id}` | Delete policy (fails if incidents reference it) |

---

## 🔐 Security

- Spring Security with form-based login and HTTP Basic Auth
- Role-based access control — USER and ADMIN roles
- REST API restricted to ADMIN role
- Web UI accessible to USER and ADMIN roles
- Passwords encoded with BCrypt
- CSRF protection enabled for web UI, disabled for REST API

---

## 🧪 Tests

Unit tests written with JUnit 5, Mockito, and AssertJ covering:
- `IncidentServiceTest` — 7 tests including analyst resolution and null handling
- `AnalystServiceTest` — 6 tests including specialization filtering
- `SlaPolicyServiceTest` — 8 tests including defensive delete logic
- `IncidentManagerApplicationTests` — integration test verifying context loads

```bash
# Run all tests
./mvnw test
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.5.14 |
| Language | Java 21 |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA / Hibernate |
| Templates | Thymeleaf + Bootstrap 5 |
| Security | Spring Security |
| Build | Maven |
| Containerization | Docker |
| Testing | JUnit 5, Mockito, AssertJ |

---

## 📁 Project Structure

```
src/main/java/com/security/incidentmanager/
├── domain/                  # JPA entities
│   ├── Incident.java        # Central entity — all four relationships
│   ├── IncidentReport.java  # OneToOne with Incident
│   ├── Analyst.java         # ManyToOne from Incident
│   ├── Asset.java           # OneToMany from Incident
│   ├── Tag.java             # ManyToMany with Incident
│   └── SlaPolicy.java       # ManyToOne from Incident
├── repository/              # Spring Data JPA repositories
├── service/                 # Business logic layer
├── controller/
│   ├── api/                 # REST controllers (JSON)
│   └── web/                 # Thymeleaf web controllers
├── config/                  # Spring Security configuration
├── DataSeeder.java          # Sample data on startup
└── SlaScheduler.java        # SLA breach detection — runs every 60s

src/main/resources/
├── templates/               # Thymeleaf HTML templates
│   ├── layout.html          # Shared navbar and head fragments
│   ├── home.html            # Dashboard with incident counts
│   ├── login.html           # Custom login form
│   ├── incidents/           # List, form, view
│   ├── analysts/            # List, form
│   ├── tags/                # List, form
│   ├── reports/             # List, form
│   ├── sla-policies/        # List, form
│   └── assets/              # Add asset form
└── application.properties

src/test/java/com/security/incidentmanager/
├── service/                 # Unit tests with Mockito
│   ├── IncidentServiceTest.java
│   ├── AnalystServiceTest.java
│   └── SlaPolicyServiceTest.java
└── IncidentManagerApplicationTests.java  # Integration test

Dockerfile                   # Multi-stage build
```

---

## 🚀 Project Status

- [x] Project setup and configuration
- [x] Domain entities with all four JPA relationships
- [x] Repository and service layer
- [x] REST API controllers for all entities
- [x] Spring Security with role-based access control
- [x] Thymeleaf web interface with full CRUD
- [x] Data seeder with sample data
- [x] SLA policies with automatic escalation scheduler
- [x] Unit tests with Mockito
- [x] Docker containerization

---

## 👩‍💻 Author

**Vasundhara Ravikumar**
Computer Science Engineering — University of Pécs, Hungary
[GitHub](https://github.com/Vasuuu641) · [LinkedIn](https://www.linkedin.com/in/vasundhararavikumar/)