# ExamByte

## Overview  
**ExamByte** is a modern web-based application designed to streamline online exam management. It provides a secure, scalable, and efficient platform using cutting-edge backend and frontend technologies.  

## Technology Stack 

### **Architecture & Design Patterns**  
- **Onion Architecture** – Decoupled and maintainable software structure  
- **Domain-Driven Design (DDD)** – Focus on core business logic and domain models 

### **Backend**  
- **Java 17** – Core programming language  
- **Spring Boot** – Backend framework  
  - `spring-boot-starter-web` – RESTful API development  
  - `spring-boot-starter-security` – Authentication & OAuth2 authorization  
  - `spring-boot-starter-data-jpa` & `spring-boot-starter-jdbc` – Database interaction  
- **Flyway** – Database migrations and version control  
- **PostgreSQL** – Primary relational database  
- **H2 Database** – In-memory database for development/testing  

### **Frontend**  
- **Thymeleaf** – Server-side templating engine  
- **HTML & CSS** – UI structure and styling  

### **Testing & Development**  
- **JUnit & ArchUnit** – Unit and architectural testing  
- **Spring Security Test** – Security testing utilities  
- **Testcontainers** – Containerized database testing  
- **Spring Boot DevTools** – Development utilities  

## License  
This project is open-source and available under the appropriate license.  
