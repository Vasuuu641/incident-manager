# 🛡️ Security Incident Management System

A Spring Boot 3.x web application for tracking and managing cybersecurity
incidents, assigning them to analysts, categorizing by attack type, and
linking to affected assets.

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

## ✨ Features

- **Incident tracking** — create, view, edit, and delete security incidents
- **Analyst assignment** — assign incidents to security analysts
- **Tag categorization** — label incidents by attack type (DDoS, Phishing, Ransomware)
- **Detailed reports** — attach findings and recommendations to each incident
- **Asset tracking** — link affected systems and devices to incidents
- **Role-based access** — analysts access the web UI, admins access the REST API
- **Sample data** — pre-seeded incidents, analysts, and tags on startup

---

## 🗄️ Data Model

### Entities and JPA Relationships

| Relationship | Between | Description |
|---|---|---|
| OneToOne | Incident → IncidentReport | Each incident has one detailed report |
| ManyToOne | Incident → Analyst | Many incidents assigned to one analyst |
| OneToMany | Analyst → Incidents | One analyst handles many incidents |
| ManyToMany | Incident ↔ Tag | Incidents share tags with other incidents |

---

## 🌐 REST API

All entities are accessible via REST endpoints returning JSON.

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

---

## 🔐 Security

- Spring Security with form-based login
- Role-based access control — USER and ADMIN roles
- REST API restricted to ADMIN role
- Web UI accessible to USER and ADMIN roles
- Passwords encoded with BCrypt

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

---

## 📁 Project Structure

```
src/main/java/com/security/incidentmanager/
├── domain/                  # JPA entities
│   ├── Incident.java
│   ├── IncidentReport.java
│   ├── Analyst.java
│   ├── Asset.java
│   └── Tag.java
├── repository/              # Spring Data JPA repositories
├── service/                 # Business logic layer
├── controller/
│   ├── api/                 # REST controllers
│   └── web/                 # Thymeleaf web controllers
├── config/                  # Spring Security configuration
└── DataSeeder.java          # Sample data on startup

src/main/resources/
├── templates/               # Thymeleaf HTML templates
│   ├── layout.html
│   ├── home.html
│   ├── login.html
│   ├── incidents/
│   ├── analysts/
│   └── tags/
└── application.properties
```

---

## 🚀 Project Status

- [x] Project setup and configuration
- [x] Domain entities with JPA relationships
- [x] Repository and service layer
- [x] REST API controllers
- [x] Spring Security configuration
- [x] Thymeleaf web interface
- [x] Data seeder with sample data
- [x] Full CRUD for all entities

---

## 👩‍💻 Author

**Vasundhara Ravikumar**
Computer Science Engineering — University of Pécs, Hungary
[GitHub](https://github.com/Vasuuu641) · [LinkedIn](https://www.linkedin.com/in/vasundhararavikumar/)