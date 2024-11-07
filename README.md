# Event Management Application

Welcome to the Event Management Application, a system designed to manage events, organizers, and visitors.

## Project Structure

This project is structured as follows:

- **aspect**:
    - `LoggingAspect` - Handles cross-cutting concerns like logging.
- **config**:
    - `KafkaConfig` - Configures Kafka for message handling.
    - `SecurityConfig` - Sets up application security with JWT authentication.
- **constant**:
    - `StringConstants` - Contains constant string values used across the application.
- **domain**:
    - `Event` - Represents event entities.
    - `Organizer` - Represents organizer entities.
    - `Visitor` - Represents visitor entities.
- **repository**:
    - `EventRepository` - Data access layer for `Event`.
    - `OrganizerRepository` - Data access layer for `Organizer`.
    - `VisitorRepository` - Data access layer for `Visitor`.
- **resource**:
    - `EventController` - API endpoints for managing events.
    - `OrganizerController` - API endpoints for managing organizers.
    - `VisitorController` - API endpoints for managing visitors.
- **service**:
    - `EventService` - Business logic for events.
    - `OrganizerService` - Business logic for organizers.
    - `VisitorService` - Business logic for visitors.
- **EventManagementApplication** - Main application entry point.

## Getting Started

### Prerequisites

Ensure that you have the following installed:

- Java 17 or higher
- Apache Kafka (for message handling)
- Postman or an API testing tool

### Accessing the H2 Database Console

You can access the H2 database console at:

http://localhost:8081/h2-console

You can test the controller methods using Postman or Talend API Chrome extension.

A sample Postman collection is provided for quick testing:
- **Postman Collection**: `event-management.postman_collection.json`

### Authentication

After the application starts, a default user is created with the following credentials:

- **Username**: `parser`
- **Password**: `qwerty`

Use these credentials to authenticate and access protected API endpoints.
