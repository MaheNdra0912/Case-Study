# Medication Compliance Project - Setup Instructions

## **📌 Prerequisites**
Ensure you have the following installed:
- **Java 17+**
- **Maven 3.6+**
- **PostgreSQL 14+**
- **Spring Boot 3+**
- **STS (Spring Tool Suite) or IntelliJ IDEA (Recommended IDEs)**

---

## **📌 1. Clone the Repository**
```sh
git clone https://github.com/MaheNdra0912/Case-Study.git
cd Case-Study
```

---

## **📌 2. Database Setup**

### **Start PostgreSQL and Create Database**
```sh
psql -U admin -W
CREATE DATABASE medication_compliance;
```

### **Database Configuration**
Modify `src/main/resources/application.properties` if needed:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/medication_compliance
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## **📌 3. Build and Run the Project**
### **Using Maven**
```sh
mvn clean install
mvn spring-boot:run
```

### **Using STS or IntelliJ**
- Open the project
- Navigate to `MedicationComplianceApplication.java`
- Click **Run ▶**

---

## **📌 4. API Documentation (Swagger UI)**
Once the application is running, open:
```
http://localhost:8080/swagger-ui/index.html
```
Here, you can **test APIs interactively**.

Alternatively, use **Postman** to send API requests.

---

## **📌 5. Sample Data**
Sample data is **preloaded in the application**, so you can directly test API responses using Swagger UI or Postman.

---

## **📌 6. Running Tests**
Run all tests using:
```sh
mvn test
```
For **test coverage**, use JaCoCo:
```sh
mvn test jacoco:report
```
Coverage report will be available in:
```
target/site/jacoco/index.html
```

---

## **🚀 You're Ready to Use the Medication Compliance System!**
Start testing using **Swagger UI** or **Postman** and check responses!

