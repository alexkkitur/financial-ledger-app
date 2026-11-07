# Financial Ledger and AML Reference Application

Production-oriented full-stack reference application for a financial ledger and anti-money-laundering (AML) review workflow. It combines a Spring Boot REST/GraphQL API with a React and TypeScript operations interface.

## Highlights

- Integer cents throughout the domain to avoid floating-point money errors.
- Serializable transfers with pessimistic account locks to prevent concurrent double-spending.
- Immutable ledger records with SHA-256 transaction hashes for tamper-evident audit history.
- Rule-based compliance screening for high-value transfers and sender velocity/structuring patterns.
- JWT authentication with role-protected compliance queries.
- PostgreSQL schema with constraints and audit-focused indexes.

## Run the backend

1. Create PostgreSQL database `ledger` and user `ledger`.
2. Run `backend/src/main/resources/db/schema.sql`.
3. Set `JDBC_PASSWORD` and a cryptographically random `JWT_SECRET` (at least 32 bytes).
4. Run `mvn spring-boot:run` from `backend`.

The REST transfer endpoint is `POST /api/transfers` with `{ "senderAccount": "...", "recipientAccount": "...", "amountInCents": 1000 }`. The GraphQL query is restricted to `ROLE_ANALYST`:

```graphql
query { flaggedTransactions(limit: 25) { transactionHash occurredAt amountInCents flagReason } }
```

## Run the frontend

```powershell
cd frontend
npm install
npm run dev
```

Set `VITE_API_URL` when the API is not running on `http://localhost:8080`. Put a valid JWT in the browser's `access_token` local-storage key for authenticated requests.

## CV project description

**Financial Ledger and AML Compliance Platform** — Built a full-stack banking ledger using Java 17, Spring Boot, PostgreSQL, React, TypeScript, REST, and GraphQL. Implemented ACID-safe money transfers with serializable transactions and pessimistic locking, immutable SHA-256 audit records, JWT/RBAC security, and configurable AML rules for high-value and velocity-based transaction monitoring. Delivered a compliance dashboard for analysts to review flagged activity.
