# 💰 Finance Aggregator

> A Production-Style Full Stack Financial Intelligence Platform  
> Built with Spring Boot, PostgreSQL, React, and Plaid

---

## 🚀 What This Project Does

**Finance Aggregator** is a secure financial analytics system that connects to bank accounts, syncs transactions automatically, reconciles financial data, and provides intelligent insights such as:

- 📊 Transaction-wise Net Worth
- 💸 Category-based Spending Analysis
- 💰 Income vs Expense Tracking
- 📈 30-Day Financial Trends
- 🏦 Multi-Bank Account Linking
- 🔄 Automated Transaction Reconciliation
- 🔐 Secure Token Handling & Encryption

It transforms raw banking data into structured financial intelligence.

---

## 🏗 Architecture Overview

Bank → Plaid → Spring Boot Backend → PostgreSQL → Analysis Engine → React Dashboard

### Backend Responsibilities
- JWT Authentication
- Plaid token exchange
- Encrypted token storage (AES-GCM)
- Transaction synchronization
- Idempotent reconciliation logic
- Financial analytics computation
- Secure REST APIs

### Frontend Responsibilities
- Login & session management
- Bank linking flow
- Transaction feed with filters
- Spend visualization using charts
- Clean dashboard experience

---

## 🔥 Core Features

### 🔐 Authentication & Security
- Secure registration & login
- BCrypt password hashing
- Stateless JWT authentication
- Encrypted Plaid access tokens
- Clean separation of frontend & bank credentials

### 🏦 Bank Integration (Plaid)
- Public token → Access token exchange
- Multi-account support
- Secure encrypted storage of access tokens
- Future-ready for incremental sync

### 🔄 Automated Transaction Sync
- Fetches last 30 days of transactions
- Overlapping window strategy
- Idempotent upsert using:
  UNIQUE(user_id, provider, provider_txn_id)
- Handles pending → posted transitions
- Prevents duplicate transaction ingestion

### 📊 Financial Analysis Engine
- Category-wise spending breakdown
- Total income calculation
- Net cash flow computation
- Average daily spending
- Backend-level aggregation for performance

### 📈 Dashboard & Visualization
- Pie Chart spend breakdown (Recharts)
- Summary financial stat cards
- Transaction table with filters
- Responsive Tailwind UI

---

## 🧠 Data Model

Core Entities:
- User
- BankAccount
- Transaction
- Budget

Database: PostgreSQL
- UUID primary keys
- Strong relational integrity
- Indexed financial queries
- ACID compliance for financial correctness

---

## 🛠 Tech Stack

| Layer      | Technology |
|------------|------------|
| Backend    | Spring Boot 3 |
| Language   | Java 17 |
| Database   | PostgreSQL |
| ORM        | JPA / Hibernate |
| Auth       | JWT |
| Encryption | AES-GCM |
| Frontend   | React (Vite) |
| Styling    | Tailwind CSS |
| Charts     | Recharts |
| Bank API   | Plaid |

---

## 📂 Project Structure

finance-aggregator/
├── finance-aggregator-backend/
│   ├── config/
│   ├── auth/
│   ├── domain/
│   ├── repo/
│   ├── plaid/
│   ├── sync/
│   ├── analysis/
│   ├── api/
│   └── util/
│
└── finance-aggregator-frontend/
    ├── api/
    ├── pages/
    ├── components/
    └── styles/

---

## ⚙️ Setup Instructions

### Backend Setup

Requirements:
- Java 17+
- Maven
- PostgreSQL

Create database:

CREATE DATABASE finance;
CREATE USER finance WITH PASSWORD 'finance';
GRANT ALL PRIVILEGES ON DATABASE finance TO finance;

Run backend:

cd finance-aggregator-backend
mvn spring-boot:run

Backend runs at:
http://localhost:8080

---

### Frontend Setup

Requirements:
- Node 18+
- npm

cd finance-aggregator-frontend
npm install
npm run dev

Frontend runs at:
http://localhost:5173

---

## 🧪 API Endpoints

Auth:
POST /api/auth/register
POST /api/auth/login

Bank:
POST /api/banks/link

Analysis:
GET /api/analysis/categories
GET /api/analysis/trends

---

## 🔁 Sync Strategy

Current:
- 30-day overlapping fetch
- Idempotent upsert

Planned Enhancements:
- Scheduled background sync
- Cursor-based incremental sync
- Webhook support
- Budget vs Actual comparison
- Net worth calculation engine
- Asset & Liability tracking

---

## 🔐 Security Considerations

- JWT-based stateless authentication
- AES-GCM encryption for access tokens
- Password hashing with BCrypt
- No bank credentials handled by frontend
- Token exchange performed server-side only

---

## 🚀 Why This Project Matters

This is not just a CRUD app.

Finance Aggregator demonstrates:
- Production-style architecture
- Secure financial data handling
- Real-world API integration
- Clean service-layer design
- Scalable sync logic
- Backend-driven analytics
- Full-stack financial system thinking

It reflects practical fintech engineering principles.

---

⭐ If you find this project interesting, feel free to fork, contribute, or reach out.
