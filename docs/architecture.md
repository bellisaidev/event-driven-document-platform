# Architecture Overview

This platform is composed of multiple Spring Boot services communicating
asynchronously through Kafka.

## Services
- Document API: secure ingestion and document access
- Document Orchestrator: state machine and workflow coordination
- Document Worker: CPU-bound document processing

## Processing Model
- Event-driven
- Asynchronous
- Idempotent
- Observable

Detailed diagrams will be added in later phases.
