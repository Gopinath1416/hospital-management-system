# 🏥 Hospital Management System

A full-stack web application developed using React.js, Spring Boot, and MySQL to manage hospital operations efficiently.

The system provides modules for managing patients, doctors, appointments, medical records, and patient reports through a responsive user interface and REST APIs.

## ✨ Features

- Patient Management
- Doctor Management
- Appointment Management
- Medical Record Management
- Patient Report Generation
- Dynamic Dashboard
- Form Validation
- Custom Exception Handling
- REST API Integration
- Swagger / OpenAPI Documentation
- JUnit, Mockito, and MockMvc Testing

## 🛠️ Technologies Used

### Frontend
- React.js
- JavaScript
- HTML5
- CSS3
- Bootstrap
- Axios
- React Router
- React Icons

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs
- Jakarta Validation
- Lombok

### Database
- MySQL

### API Documentation
- Swagger / OpenAPI

### Testing
- JUnit 5
- Mockito
- MockMvc

### Development Tools
- Eclipse
- Visual Studio Code
- Postman
- Git
- GitHub

## 🏗️ Project Architecture

```text
React Frontend
      |
      | Axios / REST API
      v
Spring Boot Controller
      |
      v
Service Layer
      |
      v
Repository Layer
      |
      v
MySQL Database
```

## 📋 Application Modules

### Patient Management
- Add new patients
- View all patients
- View patient details
- Delete patients
- Generate patient reports

### Doctor Management
- Add new doctors
- View all doctors
- View doctor details
- Delete doctors

### Appointment Management
- Schedule appointments
- View all appointments
- View appointment details
- Delete appointments

### Medical Record Management
- Add medical records
- View all medical records
- View medical record details
- Delete medical records

### Patient Report
- View patient information
- View appointment history
- View medical record history

## 🔗 REST API Endpoints

### Patient APIs

```text
GET    /patients
POST   /patients
GET    /patients/{id}
DELETE /patients/{id}
GET    /patients/{id}/report
```

### Doctor APIs

```text
GET    /doctors
POST   /doctors
GET    /doctors/{id}
DELETE /doctors/{id}
```

### Appointment APIs

```text
GET    /appointments
POST   /appointments
GET    /appointments/{id}
DELETE /appointments/{id}
```

### Medical Record APIs

```text
GET    /medical-records
POST   /medical-records
GET    /medical-records/{id}
DELETE /medical-records/{id}
```

## 📖 Swagger / OpenAPI Documentation

Swagger/OpenAPI is integrated into the backend to provide interactive REST API documentation and testing.

After starting the Spring Boot backend, open:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON documentation:

```text
http://localhost:8080/v3/api-docs
```

Swagger allows developers to view available endpoints, request bodies, parameters, response codes, and test the APIs directly from the browser.

## ✅ Validation & Exception Handling

Input validation is implemented using Jakarta Validation to prevent invalid data from being stored in the system.

Validation is applied to:

- Patient details such as name, age, email, phone, and blood group
- Doctor details such as name, specialization, email, and experience
- Appointment details such as patient, doctor, date, time, and status
- Medical record details such as diagnosis, treatment, prescription, and visit date

Invalid input returns:

`400 Bad Request`

Custom exception handling is implemented using:

- `ResourceNotFoundException`
- `GlobalExceptionHandler`

When a requested resource is not found, the API returns:

`404 Not Found`

with a structured JSON error response.

## 🧪 Testing

The backend is tested using:

- JUnit 5
- Mockito
- MockMvc

The test suite covers:

- Service layer operations
- Controller endpoints
- Successful API responses
- Resource not found scenarios
- Input validation
- Delete operations
- Patient report generation

Current test result:

```text
44 / 44 Tests Passed
Errors: 0
Failures: 0
```

## 🗄️ Database

The application uses MySQL for storing and managing hospital data.

Database name:

```text
hospital_management
```

Main tables:

```text
patients
doctors
appointments
medical_records
```

Spring Data JPA and Hibernate are used to communicate between the Spring Boot backend and MySQL database.

## 🚀 How to Run the Project

### 1. Start MySQL

- Open XAMPP Control Panel
- Start MySQL
- Make sure the `hospital_management` database is available

### 2. Run the Spring Boot Backend

Open the backend project in Eclipse.

Run:

```text
HospitalManagementBackendApplication.java
```

The backend will start at:

```text
http://localhost:8080
```

### 3. Run the React Frontend

Open the frontend project in Visual Studio Code.

Install the required dependencies:

```bash
npm install
```

Start the React application:

```bash
npm start
```

The frontend will start at:

```text
http://localhost:3000
```

### 4. Open the Application

Open the following address in your browser:

```text
http://localhost:3000
```

## 🔮 Future Enhancements

- User login and authentication
- Spring Security integration
- Role-based access for Admin, Doctor, and Receptionist
- Appointment update functionality
- Email notifications
- Search and filtering
- Pagination
- Cloud deployment

## 📌 Project Status

The core Hospital Management System is successfully completed with:

- React.js frontend
- Spring Boot backend
- MySQL database integration
- REST API development
- Input validation
- Custom exception handling
- Swagger / OpenAPI documentation
- JUnit, Mockito, and MockMvc testing
- 44 automated tests successfully passing

## 👨‍💻 Author

**Gopinath**