<div align="center">

# 📚 Liibrary — Library Management System

**A full-stack, containerized Library Management System built with Spring Boot, MariaDB, and Docker — deployed on AWS EC2.**

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MariaDB](https://img.shields.io/badge/MariaDB-11-003545?logo=mariadb&logoColor=white)](https://mariadb.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?logo=amazonaws&logoColor=white)](https://aws.amazon.com/ec2/)
[![License](https://img.shields.io/badge/License-TODO-lightgrey)](#)

</div>

---

## 📖 Overview

Managing a library manually — tracking books, members, and who has borrowed what — is slow and error-prone. **Liibrary** solves this by providing a centralized, database-backed web application that lets library staff manage the entire book-lending lifecycle from a single dashboard.

The project began as a simple console-based Java + JDBC application and has since been **migrated into a production-style Spring Boot service**, exposing both a server-rendered web UI (Thymeleaf + Bootstrap) and a secured REST API, backed by MariaDB and packaged for containerized deployment on AWS.

It's designed as a portfolio-grade demonstration of backend engineering, security, and cloud deployment practices — not just a CRUD toy project.

---

## ✨ Features

### 🔐 Authentication
- Form-based login for the web UI, secured with **Spring Security**
- **BCrypt**-hashed passwords, no plaintext credentials stored
- HTTP Basic authentication for REST API consumers
- Default seeded accounts (`admin`, `librarian`, `member`) for local development/demo

### 📗 Book Management
- Add, edit, view, and delete books
- Automatic availability tracking (`available` flag toggled on issue/return)
- Safeguards against deleting a book that is currently issued

### 👥 Member Management
- Register and view library members
- Member records linked to borrowing history via transactions

### 🔄 Issue / Return Books
- Issue a book to a member with automatic availability checks
- Return a book, closing out the active transaction and restoring availability
- Full transaction history (issue date, return date)

### 📊 Dashboard Analytics
- At-a-glance stats: total books, total members, issued books, available books
- Server-rendered dashboard view for quick operational insight

### 💾 Database Persistence
- **Spring Data JPA + Hibernate** ORM over **MariaDB**
- Relational schema with proper entity relationships (see [Database](#-database))

### 📱 Responsive UI
- **Thymeleaf** templates styled with **Bootstrap 5**
- Responsive navbar, forms, and tables that adapt to different screen sizes

### 🛡️ Role-Based Access
- Three roles: `ROLE_ADMIN`, `ROLE_LIBRARIAN`, `ROLE_MEMBER`
- Endpoint-level authorization (e.g., only admins can register members; librarians and admins can issue/return books)

> **Note:** GitHub OAuth client credentials are present in configuration, but OAuth login is **not yet wired into the active security filter chain** — treat this as a work in progress (see [Future Improvements](#-future-improvements)).

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security (BCrypt, form login, HTTP Basic) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MariaDB 11 |
| Templating | Thymeleaf |
| Styling | Bootstrap 5 (+ Bootstrap Icons) |
| Build Tool | Maven |
| API Docs | springdoc-openapi (Swagger UI) |
| Containerization | Docker, Docker Compose |
| Image Registry | Amazon ECR Public |
| Hosting | AWS EC2 |
| Auth (planned) | GitHub OAuth |

---

## 🏛️ Architecture

The application follows a classic layered Spring MVC architecture, containerized for consistent deployment:

```
        Browser
           │
           ▼
      Spring Boot
   (Controllers / UI + REST)
           │
           ▼
     Hibernate / JPA
     (Service + Repository)
           │
           ▼
        MariaDB
```

- **Presentation layer:** Thymeleaf-rendered pages under `/ui/**` for humans, and JSON REST endpoints under `/api/**` for programmatic access.
- **Service layer:** `LibraryService` encapsulates all business rules (availability checks, transaction lifecycle).
- **Persistence layer:** Spring Data JPA repositories backed by Hibernate, mapped to a MariaDB schema.
- **Orchestration:** `docker-compose.yml` wires together the Spring Boot app container and a MariaDB container, including a health check so the app only starts once the database is ready.

---

## 🖼️ Screenshots

> Add screenshots to `library_system/docs/snapshots/` and reference them below.

### Login
`TODO: add screenshot`

### Dashboard
`TODO: add screenshot`

### Books
`TODO: add screenshot`

### Members
`TODO: add screenshot`

### Transactions
`TODO: add screenshot`

---

## 📂 Project Structure

```
liibrary/
└── library_system/
    ├── Dockerfile
    ├── docker-compose.yml
    ├── library.sql
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    ├── docs/
    │   └── snapshots/          # screenshots for README
    └── src/
        ├── main/
        │   ├── java/com/example/demo/
        │   │   ├── config/           # Security config, data seeding, startup runners
        │   │   ├── controller/       # REST controllers (/api/**)
        │   │   ├── controller/ui/    # Server-rendered controllers (/ui/**)
        │   │   ├── dto/               # Request/response DTOs
        │   │   ├── exception/         # Custom exceptions + global handler
        │   │   ├── model/             # JPA entities (Book, Member, Transaction, UserAccount, Role)
        │   │   ├── repository/        # Spring Data JPA repositories
        │   │   ├── security/          # UserDetailsService implementation
        │   │   ├── service/           # Business logic (LibraryService)
        │   │   └── DemoApplication.java
        │   └── resources/
        │       ├── static/css/        # Custom CSS (app.css)
        │       ├── templates/         # Thymeleaf views (dashboard, books, members, login, layout)
        │       └── application.properties
        └── test/
            └── java/com/example/demo/DemoApplicationTests.java
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven (or use the included `mvnw` wrapper)
- MariaDB (or Docker, to run it in a container)
- Docker & Docker Compose (for containerized run)
- Git

### Clone Repository
```bash
git clone https://github.com/SaKsham266/liibrary.git
cd liibrary/library_system
```

### Build Project
```bash
./mvnw clean package -DskipTests
```

### Run Locally
Make sure a MariaDB instance is available (default expected on port `3307`), then run:
```bash
./mvnw spring-boot:run
```

Or run the packaged jar directly:
```bash
java -jar target/*.jar
```

The application will be available at `http://localhost:8080`.

### Run with Docker Compose
```bash
docker compose up -d
```

This spins up a MariaDB container and the Spring Boot app together, with the app waiting for the database health check to pass before starting.

> **Tip:** Default demo credentials seeded on startup are `admin / qwerty`, `librarian / qwerty`, and `member / qwerty`. Change these before using the app in any non-local environment.

---

## 🐳 Docker Deployment

### Build Docker Image
```bash
docker build -t library_system .
```

### Push to Amazon ECR Public
```bash
aws ecr-public get-login-password --region us-east-1 \
  | docker login --username AWS --password-stdin public.ecr.aws/<your-registry-alias>

docker tag library_system:latest public.ecr.aws/<your-registry-alias>/library_system:latest
docker push public.ecr.aws/<your-registry-alias>/library_system:latest
```

### Deploy on AWS EC2
```bash
# On the EC2 instance
sudo yum install -y docker
sudo service docker start

docker pull public.ecr.aws/<your-registry-alias>/library_system:latest
```

### Run Docker Compose (on EC2)
```bash
docker compose up -d
```

### Import MariaDB Database
```bash
docker exec -i library-mariadb mariadb -u library -p librarydb < library.sql
```

---

## ⚙️ Configuration

The application reads its datasource configuration from environment variables (with local-development fallbacks) in `application.properties`:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mariadb://localhost:3307/librarydb}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:library}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:librarypass}

server.port=${PORT:8080}
```

GitHub OAuth credentials are read the same way, with **no hardcoded fallback**, since these are secrets rather than local dev defaults:

```properties
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
spring.security.oauth2.client.registration.github.scope=user:email
```

When running via Docker Compose or on EC2, provide real values instead of relying on datasource defaults:

```bash
export SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/librarydb
export SPRING_DATASOURCE_USERNAME=library
export SPRING_DATASOURCE_PASSWORD=change_me
export GITHUB_CLIENT_ID=your_github_oauth_client_id
export GITHUB_CLIENT_SECRET=your_github_oauth_client_secret
export PORT=8080
```

> ⚠️ **Security note:** an earlier version of this file committed a real GitHub OAuth `client-id` and `client-secret` in plaintext. Those values are now sourced from environment variables, but the **old credentials are permanently exposed in git history** and must be rotated in GitHub's OAuth App settings — switching to env vars alone does not undo that exposure.

---

## 🗄️ Database

The schema is defined in `library.sql` and managed at runtime via Hibernate (`spring.jpa.hibernate.ddl-auto=update`). Core tables:

| Table | Purpose |
|---|---|
| **books** | Catalog of books (`title`, `author`, `publisher`, `available`) |
| **members** | Registered library members (`name`, `email`, `phone`) |
| **transactions** | Issue/return records linking a book to a member, with `issue_date` and `return_date` |
| **users** | Application accounts for login (`username`, `password`, `role`) |

**Entity relationships:**
- A `Transaction` has a **many-to-one** relationship with both `Book` and `Member` — each transaction references exactly one book and one member.
- A `Book`'s `available` flag is toggled by the service layer when a transaction is opened (issue) or closed (return).
- `UserAccount` is independent of `Member`/`Book` — it represents application login identity and role, not library membership.

---

## 🔒 Security

- **Spring Security** protects both the server-rendered UI (`/ui/**`, form login) and the REST API (`/api/**`, HTTP Basic) with separate, ordered filter chains.
- **BCrypt** is used to hash all stored passwords via `BCryptPasswordEncoder`.
- **Role-based authorization** restricts sensitive operations — for example, only `ROLE_ADMIN` can register new members, while `ROLE_ADMIN` and `ROLE_LIBRARIAN` can issue/return books.
- **GitHub OAuth Login** — client registration is present in configuration but not yet wired into the security filter chain; see [Future Improvements](#-future-improvements).

---

## 📡 API Overview

The application is a **hybrid**: a server-rendered Spring MVC UI under `/ui/**` for day-to-day use, plus a secured REST API under `/api/**` for programmatic access. API documentation is available via **springdoc-openapi** at `/swagger-ui.html` once the app is running.

| Method | Endpoint | Description | Required Role(s) |
|---|---|---|---|
| `GET` | `/api/books` | List all books | ADMIN, LIBRARIAN, MEMBER |
| `GET` | `/api/books/{id}` | Get a book by ID | ADMIN, LIBRARIAN, MEMBER |
| `POST` | `/api/books` | Add a new book | ADMIN, LIBRARIAN |
| `GET` | `/api/members` | List all members | ADMIN, LIBRARIAN |
| `GET` | `/api/members/{id}` | Get a member by ID | ADMIN, LIBRARIAN |
| `POST` | `/api/members` | Register a new member | ADMIN |
| `POST` | `/api/transactions/issue` | Issue a book to a member | ADMIN, LIBRARIAN |
| `PUT` | `/api/transactions/return/{bookId}` | Return a book | ADMIN, LIBRARIAN |
| `GET` | `/api/transactions` | List all transactions | ADMIN, LIBRARIAN |

---

## 🔭 Future Improvements

- [ ] JWT-based authentication for stateless API access
- [ ] Migrate to AWS RDS for managed database hosting
- [ ] CI/CD pipeline with GitHub Actions
- [ ] HTTPS termination via Nginx reverse proxy
- [ ] Application monitoring/observability (metrics, logs, alerts)
- [ ] Expanded unit and integration test coverage
- [ ] Redis caching for frequently accessed data
- [ ] Complete GitHub OAuth login integration

---

## ☁️ Deployment

The application is deployed to a production-style environment consisting of:

- **AWS EC2 (Ubuntu)** — host virtual machine
- **Docker Compose** — orchestrates the app and database containers
- **MariaDB** — running as a container with persistent volume storage
- **Amazon ECR Public** — hosts the built application image, pulled by the EC2 instance

The Spring Boot application runs entirely inside a Docker container on the EC2 instance, alongside a MariaDB container, communicating over the Docker Compose network.

---

## 🎓 Learning Outcomes

This project demonstrates practical, end-to-end software engineering skills, including:

- **Full-Stack Development** — building both a REST API and a server-rendered UI on the same backend
- **Spring Boot** — layered architecture (controller/service/repository) and dependency injection
- **Docker** — multi-stage builds and multi-container orchestration with Docker Compose
- **Cloud Deployment** — pushing images to Amazon ECR Public and running them on AWS EC2
- **Database Design** — relational schema modeling with JPA/Hibernate entity relationships
- **Authentication** — form login, HTTP Basic, and BCrypt password hashing with Spring Security
- **DevOps Fundamentals** — containerization, environment-based configuration, and deployment workflows

---

## 👤 Author

**TODO: Your Name**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-0A66C2?logo=linkedin&logoColor=white)](TODO-linkedin-url)
[![GitHub](https://img.shields.io/badge/GitHub-SaKsham266-181717?logo=github&logoColor=white)](https://github.com/SaKsham266)
