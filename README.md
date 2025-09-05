# 💳 Spring Boot Payment Processing Microservice with Stripe and MySQL

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green.svg)
![Stripe](https://img.shields.io/badge/Stripe-API-informational.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## 📖 Overview

This microservice is built with Spring Boot to process payment transactions using the **Stripe payment gateway** and persist transaction details in a **MySQL database**. It exposes REST APIs to initiate payments and retrieve payment history, making it ideal for e-commerce platforms requiring secure and trackable payment processing.

---

## ✨ Features

- 🔐 **Stripe Integration**: Secure payment processing via Stripe Charge API  
- 🗃️ **Database Persistence**: Stores charge ID, amount, currency, description, and status in MySQL  
- 🌐 **REST API Endpoints**:
  - `POST /api/pay` — Initiates a payment
  - `GET /api/payments` — Retrieves payment history  
- ⚠️ **Error Handling**: Graceful handling of Stripe exceptions  
- 🧱 **Scalable Design**: Microservice-ready for integration with API Gateway or Eureka Server

---

## 🧰 Technologies Used

- Java 17  
- Spring Boot 3.x  
- Stripe Java SDK  
- MySQL 8.x  
- Spring Web  
- Spring Data JPA  
- Hibernate (connection pooling)  
- Lombok  
- Maven  
- Postman or browser for testing

---

## 📁 Project Structure

```text
payment-microservice/
├── src/main/java/com/ccp/
│   ├── controller/             # REST controller
│   ├── entity/                 # JPA entity for payment records
│   ├── model/                  # Payment request model
│   ├── service/                # Stripe payment logic and DB persistence
│   ├── repository/             # JPA repository
├── src/main/resources/
│   └── application.properties  # Stripe & MySQL configuration
├── assets/                     # Images (logo, diagrams)
└── README.md
```

---

## 🛠️ Prerequisites

- Java 17+  
- Maven 3.6+  
- MySQL 8.x installed and running  
- Stripe account and API key  
- Postman or browser for testing  
- *(Optional)* API Gateway or Eureka Server

---

## ⚙️ Setup Instructions

### 📥 Clone the Repository

```bash
git clone <repository-url>
cd payment-microservice
```

### 🛢️ Configure MySQL Database

Create the database:

```sql
CREATE DATABASE payment_db;
```

Update `application.properties`:

```properties
spring.application.name=StripeApiPaymentMySQL
stripe.api.key=sk_test_your_stripe_api_key

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/payment_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password

spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=1000

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

### 📦 Add Dependencies

Ensure `pom.xml` includes:

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
  </dependency>
  <dependency>
    <groupId>com.stripe</groupId>
    <artifactId>stripe-java</artifactId>
    <version>20.128.0</version>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### 🏗️ Build the Project

```bash
mvn clean install
```

### ▶️ Run the Application

```bash
mvn spring-boot:run
```

---

## 📡 Usage

### 🔧 Initiate Payment

**Endpoint**: `POST http://localhost:8080/api/pay?token=tok_visa`  
**Request Body**:

```json
{
  "amount": 1000,
  "curreny": "usd",
  "description": "Payment for order #123"
}
```

**Example cURL**:

```bash
curl -X POST "http://localhost:8080/api/pay?token=tok_visa" \
     -H "Content-Type: application/json" \
     -d '{"amount": 1000, "curreny": "usd", "description": "Payment for order #123"}'
```

**Success Response**:

```json
"Payment Successful! chargeId: ch_3N0x1234567890"
```

**Error Response**:

```json
"Payment Failed: Invalid card details"
```

---

### 📖 Retrieve Payment History

**Endpoint**: `GET http://localhost:8080/api/payments`  
**Example Response**:

```json
[
  {
    "id": 1,
    "chargeId": "ch_3N0x1234567890",
    "amount": 1000,
    "currency": "usd",
    "description": "Payment for order #123",
    "status": "succeeded"
  }
]
```

---

## 🔗 Integration with Microservices

Example API Gateway route configuration:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: payment-service
          uri: lb://PAYMENT-SERVICE
          predicates:
            - Path=/api/pay/**
            - Path=/api/payments/**
```

---

## 🧪 Testing with Stripe

- Use test tokens like `tok_visa`  
- Verify charges in Stripe Dashboard under test mode  
- Refer to [Stripe Testing Docs](https://stripe.com/docs/testing)

---

## 🧭 Architecture Diagram

> *(Add your diagram image to `assets/` and embed it here)*

---

## 📝 Notes

- Store Stripe API key securely (e.g., AWS Secrets Manager)  
- Use Stripe.js or Stripe Elements to generate tokens client-side  
- Ensure MySQL is running and accessible  
- Deploy multiple instances with a load balancer for scalability

---

## 🛠️ Troubleshooting

| Issue                  | Solution                                                                 |
|------------------------|--------------------------------------------------------------------------|
| Stripe API Errors      | Verify API key and use valid test tokens                                 |
| Database Issues        | Ensure MySQL is running and `payment_db` exists                          |
| Service Not Starting   | Check `pom.xml` and ensure port `8080` is free                           |
| README Wrapping        | Use LF line endings and proper Markdown formatting                       |

```bash
sed -i 's/\r$//' README.md
git add README.md
git commit -m "Fix line endings"
git push origin main
```

---

## 🚀 Future Enhancements

- Add JWT-based authentication  
- Implement refund and subscription support  
- Integrate with Order Service for full workflow  
- Dockerize and deploy with Kubernetes  
- Add monitoring with Spring Boot Actuator and Prometheus

---

## 🤝 Contributing

Contributions are welcome! Submit pull requests or open issues for bugs, improvements, or new features.

---

## 📜 License

This project is licensed under the **MIT License**.
