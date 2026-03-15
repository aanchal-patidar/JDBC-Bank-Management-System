# JDBC Bank Management System

## 📌 Project Description

This project is a simple **Bank Management System** built using **Java and JDBC**.
It allows users to create accounts and perform banking operations like deposit, withdraw, and transfer money.

---

# 🛠 Technologies Used

* Java
* JDBC
* PostgreSQL
* SQL

---

# ✨ Features

* Create Customer Account
* Deposit Money
* Withdraw Money
* Transfer Money
* View Transaction History
* Check Account Balance

---

# 🗄 Database Setup

## Create Database

```sql
CREATE DATABASE bankdb;
```

Connect to database

```sql
\c bankdb;
```

---

## Customer Table

```sql
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    address TEXT
);
```

---

## Account Table

```sql
CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id),
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type VARCHAR(20),
    balance DECIMAL(12,2) DEFAULT 0
);
```

---

## Transaction Table

```sql
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INT REFERENCES accounts(account_id),
    transaction_type VARCHAR(20),
    amount DECIMAL(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## Transfer Table

```sql
CREATE TABLE transfers (
    transfer_id SERIAL PRIMARY KEY,
    from_account INT,
    to_account INT,
    amount DECIMAL(10,2),
    transfer_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

# ▶ How to Run the Project

1. Install PostgreSQL
2. Create the database using the SQL queries above
3. Update database credentials in the Java connection file
4. Run the Java application

---

# 📂 Project Structure

src/
dao/
util/
Main.java

---

# 👩‍💻 Author

Aanchal Patidar
Associate Software Developer
