
# Event-Driven Document Platform

Event-driven backend platform for ingesting, validating, and normalizing
**bank transaction CSV files** into structured, reliable data using Java and Spring Boot.

---

## Overview

This project demonstrates how to design and implement a **production-style,
cloud-native document processing platform** using modern backend engineering practices.

The platform focuses on:
- secure document ingestion
- asynchronous, event-driven processing
- deterministic data normalization
- full auditability and observability

The initial use case processes **bank transaction CSV files**, converting raw exports
into canonical structured data, financial summaries, and data-quality reports.

---

## Core Concepts

- **Event-driven architecture** for scalable and resilient workflows
- **Separation of concerns** between ingestion, orchestration, and processing
- **Asynchronous processing** with explicit state transitions
- **Immutable artifacts** stored in object storage
- **Observability-first design** (metrics, logs, tracing)

---

## High-Level Architecture

The platform is composed of multiple Spring Boot services:

### Services
- **Document API Service**
  - Secure ingestion endpoints
  - Issues pre-signed upload/download URLs
  - Exposes document metadata and processing status

- **Document Orchestrator Service**
  - Owns the document state machine
  - Coordinates processing via events
  - Handles retries and idempotency

- **Document Processing Worker**
  - Validates and processes CSV files
  - Produces normalized datasets, summaries, and quality reports

Services communicate asynchronously via **Apache Kafka**.

More details are available in `docs/architecture.md`.

---

## Processing Flow (Bank Transaction CSV)

1. Client uploads a CSV file using a pre-signed URL
2. Metadata is persisted and a `DocumentUploaded` event is published
3. The orchestrator transitions the document to `PROCESSING`
4. A worker validates and processes the CSV
5. Processing artifacts are generated:
   - normalized dataset
   - financial summary
   - data-quality report
6. Results are stored and the document is marked as `PROCESSED` or `FAILED`

---

## Technology Stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security + OAuth2**
- **PostgreSQL**
- **Redis**
- **Apache Kafka**
- **Amazon S3**
- **Docker**
- **Kubernetes**
- **Terraform**
- **AWS**
- **Prometheus & Grafana**
- **Loki / ELK**

---

## Project Structure

```text
event-driven-document-platform/
│
├─ services/
│  ├─ document-api/
│  ├─ document-orchestrator/
│  └─ document-worker/
│
├─ local/
│  └─ docker-compose.yml
│
├─ infra/
│  └─ terraform/
│
├─ docs/
│  ├─ architecture.md
│  └─ decisions.md
│
└─ README.md
