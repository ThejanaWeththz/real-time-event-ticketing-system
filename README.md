# Real Time Event Ticketing System

A comprehensive system for managing real-time event ticketing with a producer consumer pattern, including a backend built with Spring Boot, a frontend powered by Angular, and a CLI developed in Java.

#### Prerequisites

Ensure the following tools are installed:

- [Java](https://www.java.com/en/) (v21 recommended)
- [Angular](https://angular.dev/) (latest recommended)
- [Bun](https://bun.sh/) (latest recommended)
- Maven

#### Getting Started

1. Clone the repository:

```bash
git  clone  https://github.com/ThejanaWeththz/real-time-event-ticketing-system.git
cd  real-time-event-ticketing-system
```

2. Run the backend server:

```bash
cd backend
mvn clean install
mvn  spring-boot:run
```

This will start the SpringBoot server on `http://localhost:8080`.

3. Run the frontend server:

```bash
cd frontend
bun install
bun run start
```

This will start the Angular server on `http://localhost:4200`.

4. Run the cli:

```bash
cd cli
mvn clean install
```

Run the "CliApplication.java" through your IDE.
