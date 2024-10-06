Library Management System
---
This Library Management System is a microservices-based web application built with Spring Boot (for backend services) and Angular (for frontend). The system allows for managing books, authors, categories, and classifications while providing efficient communication between services. The services are deployed independently and communicate over REST APIs, using JWT for authentication.

Microservices Architecture
---
The system consists of the following microservices:

Book Microservice:
- Manages CRUD operations for books.
- Handles CRUD operations for authors.
- Manages book categories and classifications.

Circulation Microservices:
- Manages the borrowings of users and returnings.

Payment Microservice:
- Handles fines and payments (Stripe integration).

Email Service:
- Consumes messages from the Payment and Circulation microservices using Kafka to send email notifications.

Each service communicates through REST APIs, secured with JWT authentication. The system uses Spring Cloud Gateway for routing and load balancing.
