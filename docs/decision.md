# Architecture Decisions

## ADR-001: Initial Use Case Selection

**Decision:**  
The initial use case for the platform is processing **bank transaction CSV files**.

**Rationale:**  
CSV transaction files are:
- easy to understand for demo users
- deterministic and testable
- representative of real financial data pipelines
- commonly used export format across banking and financial systems

This allows the platform to demonstrate ingestion, validation,
normalization, and aggregation without introducing unnecessary complexity
at the initial stage.

**Status:** Accepted

## ADR-002: Event-Driven Processing Model

**Decision:**  
The system is designed around an **event-driven, asynchronous architecture**
rather than synchronous request/response processing.

**Rationale:**
- Document processing is potentially slow and CPU-bound
- Asynchronous workflows improve scalability and resilience
- Synchronous APIs are retained only for control-plane operations (upload, status, retrieval)
- Events allow clear separation between ingestion, orchestration, and processing
- This mirrors real enterprise document and data platforms

**Consequences:**
- Kafka is introduced as a core dependency
- Services must be idempotent and observable

**Status:** Accepted


## ADR-003: Mono-Repository with Multiple Services

**Decision:**  
All services are maintained in a single repository with clear folder separation.

**Rationale:**
- Easier local development and onboarding
- Simplifies CI and dependency management
- Still preserves service boundaries and deployment independence

This decision can be revisited if the system grows or is split across teams.

**Status:** Accepted


## ADR-004: Explicitly Deferred Decisions

The following decisions are intentionally deferred:
- Exact Kafka topic structure
- Database schema details
- Choice between Spring Boot worker vs AWS Lambda
- Kubernetes vs managed container services

These will be addressed incrementally in later phases to avoid premature optimization.

**Status:** Accepted
