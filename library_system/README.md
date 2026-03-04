# Library Management System

Spring Boot 3 based library management system with a secured REST API and a Thymeleaf UI.

## Features

- Role-based authentication and authorization with Spring Security.
- Custom login page (`/login`) and protected UI (`/ui/**`).
- Book management:
  - Add books
  - List books
  - View by ID
  - Edit/Delete from UI
- Member management:
  - Register members
  - List members
  - View member by ID
- Transaction management:
  - Issue a book to a member
  - Return a book
  - View all transactions
- Dashboard with total books, issued books, available books, and member count.
- Startup seeding:
  - Default users (`admin`, `librarian`, `member`)
  - Randomly marks books as issued (target = 5) when possible.

## Tech Stack

- Java 17
- Spring Boot 3.3
- Spring Security 6
- Spring Data JPA (Hibernate)
- Thymeleaf
- MariaDB
- Maven
- Docker + Docker Compose

## Default Users

| Username   | Password | Role      |
|------------|----------|-----------|
| admin      | qwerty   | ADMIN     |
| librarian  | qwerty   | LIBRARIAN |
| member     | qwerty   | MEMBER    |

## Role Rights

- `ADMIN`
  - UI access: `/ui/**`
  - Books API: GET/POST
  - Members API: GET/POST
  - Transactions API: issue/return/view

- `LIBRARIAN`
  - UI access: `/ui/**`
  - Books API: GET/POST
  - Members API: GET only
  - Transactions API: issue/return/view

- `MEMBER`
  - No UI access
  - Books API: GET only
  - No member creation or transaction actions

## API Summary

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `GET /api/members`
- `GET /api/members/{id}`
- `POST /api/members`
- `POST /api/transactions/issue`
- `PUT /api/transactions/return/{bookId}`
- `GET /api/transactions`

## How To Run

### Option 1: Docker (Recommended)

1. Build and start containers:
   ```bash
   docker-compose up -d --build
   ```
2. Open the application:
   - Login page: `http://localhost:8080/login`
3. Stop services:
   ```bash
   docker-compose down
   ```

### Option 2: Run Locally

1. Ensure Java 17 and Maven are installed.
2. Start a MariaDB instance and create a database/user (or use Docker for DB only).
3. Set datasource environment variables:
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3307/librarydb
   export SPRING_DATASOURCE_USERNAME=library
   export SPRING_DATASOURCE_PASSWORD=librarypass
   ```
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Open:
   - `http://localhost:8080/login`

## Snapshots

Add screenshots in `docs/snapshots/` using these names to auto-render in README:

- `login.png`
- `dashboard.png`
- `books.png`
- `members.png`

When added, use:

```md
![Login](docs/snapshots/login.png)
![Dashboard](docs/snapshots/dashboard.png)
![Books](docs/snapshots/books.png)
![Members](docs/snapshots/members.png)
```

