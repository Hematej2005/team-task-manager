# Team Task Manager

## Overview

Team Task Manager is a full-stack role-based task management web application developed to simplify project and task coordination inside teams. The application allows administrators to create projects, assign tasks to members, monitor task progress, and manage workflows efficiently.

Members can view assigned tasks, accept tasks, update task progress, and mark tasks as completed. The application uses secure JWT-based authentication with HTTP-only cookies and role-based authorization using Spring Security.

This project was developed as a complete backend and frontend integrated application using Java Spring Boot, MySQL, HTML, CSS, and JavaScript.

---

# Features

## Authentication & Authorization
- User Registration
- User Login
- JWT Token Authentication
- HTTP Only Cookie-Based Authentication
- Secure Logout
- Role-Based Access Control
- Protected APIs using Spring Security

---

# Admin Functionalities

- Create Projects
- View All Projects
- Open Project Details
- Assign Tasks to Members
- Set Task Deadlines
- Track Task Status
- Monitor Task Progress
- Expandable Task View Interface

---

# Member Functionalities

- View Assigned Tasks
- Accept Tasks
- Update Task Status
- Mark Tasks as Completed
- View Project Information
- View Assigned Admin Details
- Track Deadlines

---

# Task Status Workflow

```text
PENDING → ACCEPTED → IN_PROGRESS → COMPLETED
