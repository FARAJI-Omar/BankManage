# 🏦 Bank Management Console App (Java)

## 📌 Project Context
A Moroccan bank wants to digitalize the management of client accounts and transactions.  
This project is a **Java console application** that simulates account and transaction management while respecting business rules, applying **OOP best practices**, and using **Java 8 features** like Collections, Streams, Optional, Functional Interfaces, and Java Time API.

---

## 🚀 Features

### 👤 For Clients
- View personal information and bank accounts.
- View full transaction history (deposits, withdrawals, transfers).
- **Perform transactions directly:**
  - Deposit money into an account.
  - Withdraw money from an account.
  - Transfer money between accounts (if sufficient balance).
- Filter and sort transactions by type, amount, or date.
- Calculate total balance and totals of deposits/withdrawals.

### 🧑‍💼 For Bankers (Managers)
- Create, update, or delete clients and their accounts.
- View and filter transactions.
- Automatic calculation of balance and totals by client & transaction type.
- Identify suspicious transactions (large amounts, repeated operations).

---

## ⚖️ Business Rules
- Each client can have multiple accounts.
- Each account belongs to one client.
- An account can have multiple transactions.
- **Clients initiate deposits, withdrawals, and transfers** via the console.
- Transfers only possible if source account has sufficient balance.
- Exception handling:
  - Negative amount → `IllegalArgumentException`
  - Client or account not found → `NoSuchElementException`
  - Invalid transaction → `IllegalStateException`
  - Insufficient balance for transfer → `ArithmeticException`

---

## 🏗️ Architecture (MVC + Service Layer)

- **Model**
  - `Person` (abstract), `Client`, `Manager`
  - `Account`, `Transaction`
  - `AccountType` (enum: CHECKING, SAVINGS, TERM_DEPOSIT)
  - `TransactionType` (enum: DEPOSIT, WITHDRAWAL, TRANSFER)

- **Repositories**
  - `ClientRepository`, `AccountRepository`, `TransactionRepository` (+ implementations)

- **Services**
  - `AuthService` → login (with hardcoded manager)
  - `ClientService` → manage clients
  - `AccountService` → manage accounts
  - `TransactionService` → deposit, withdraw, transfer, filter transactions, detect suspicious activity  
    ⚡ **Note:** Clients initiate transactions, but services perform the logic and repositories store them.

- **Controller**
  - Handles user interaction, validates input, calls services.

- **View**
  - Console-based menus for client and banker dashboards.

---

## 🛠️ Technologies & Tools
- **Language:** Java 8
- **Paradigm:** Object-Oriented Programming
- **Architecture:** MVC + Service Layer
- **Collections:** List, Map
- **Java Features Used:**
  - Streams & Lambdas
  - Functional Interfaces
  - Optional
  - Java Time API

---

## 📂 Project Structure
src/
src/
src/
├── model/
│   ├── Person.java
│   ├── Client.java
│   ├── Account.java
│   ├── Transaction.java
│   ├── enums/
│   │   ├── AccountType.java
│   │   ├── TransactionType.java
│   │   └── Role.java
│
├── repository/
│   ├── ClientRepository.java
│   ├── AccountRepository.java
│   ├── TransactionRepository.java
│   └── InMemoryImpl/
│       ├── InMemoryClientRepository.java
│       ├── InMemoryAccountRepository.java
│       └── InMemoryTransactionRepository.java
│
├── service/
│   ├── AuthService.java
│   ├── ClientService.java
│   ├── AccountService.java
│   ├── TransactionService.java
│   └── impl/
│       ├── AuthServiceImpl.java
│       ├── ClientServiceImpl.java
│       ├── AccountServiceImpl.java
│       └── TransactionServiceImpl.java
│
├── view/
│   ├── MainMenu.java
│   ├── ClientView.java
│   └── BankerView.java
│
└── util/
├── DateUtil.java
├── InputUtil.java
└── ValidatorUtil.java
│
└── Main.java




## 📂 Class Diagram

<img width="827" height="1169" alt="classDiagram bank" src="https://github.com/user-attachments/assets/3c5d57e7-59db-4660-9826-ec8c0e7c12e1" />
