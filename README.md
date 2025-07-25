# ✅ Task Management Web Application

A Spring Boot-based web application for managing tasks with user roles and MySQL database support.

---

## 🚀 Features

- User authentication & authorization
- Role-based access (e.g., User, Manager)
- Task CRUD (Create, Read, Update, Delete)
- Responsive web interface
- MySQL integration

---

## 📦 Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven

---

## 🛠️ Setup Instructions

### 1. Install MySQL Server

- **Windows:**  
  Download and install from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).

- **Linux (Debian/Ubuntu):**  
  Open terminal and run:
  ```bash
  sudo apt update
  sudo apt install mysql-server
  ```

### 2. Create Database

- Open MySQL shell and run:

  ```sql
  CREATE DATABASE taskssystem

  ```

- Or run this schema script:
  ```bash
  mysql -u root -p < schema.sql
  ```

### 3. Configure Application Properties

To keep your database credentials safe, the real config file is not included.

- Copy the sample config file (application_example.properties)

  ```bash
  cp src/main/resources/application-example.properties src/main/resources/application.properties
  ```

- Edit application.properties and update the following:

```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 4. Run the application

- Using maven wrapper

```bash
./mvnw spring-boot:run

```

- Or just run it using your IDE.

### 5. Important Note

- In this application, there is no mechanism for creating managers. To create managers, just use SQL queries or the MySQL workbench GUI to insert records into the managers table.
