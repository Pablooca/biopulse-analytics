# BioPulse Analytics

[![Java - Version](https://img.shields.io/badge/Java-21-orange.svg?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot - Version](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Python - Version](https://img.shields.io/badge/Python-3.11%2B-blue.svg?style=flat-square&logo=python)](https://www.python.org/)
[![Kafka - Status](https://img.shields.io/badge/Apache_Kafka-Distributed-black.svg?style=flat-square&logo=apachekafka)](https://kafka.apache.org/)
[![Camunda - BPMN](https://img.shields.io/badge/Camunda-BPMN_2.0-orange.svg?style=flat-square)](https://camunda.com/)
[![License - MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

BioPulse Analytics is an end-to-end, enterprise-grade distributed telemedicine platform designed for massive real-time biometric data ingestion, automated clinical anomaly detection via Machine Learning, and intelligent workflow orchestration. 

The system leverages **Hexagonal Architecture (Ports & Adapters)** in the Java backend to guarantee strict isolation of core medical business logic, decoupling it entirely from heavy mathematical predictive engines and streaming infrastructure.

---

## 🏗️ System Architecture & Data Flow

The platform functions as a highly decoupled event-driven ecosystem structured across four main structural tiers:

1. **Ingestion Layer (Java / Spring Boot):** A high-throughput REST API receives structured JSON health payloads from simulated medical IoT devices, validates payload contracts against core domain invariants, and immediately offloads events to an asynchronous broker.
2. **Streaming & Event Broker (Apache Kafka):** Acts as the centralized log backbone, ensuring low-latency delivery, persistent event backpressure management, and pub/sub isolation between Java services and Python analytical workers.
3. **Analytical Brain (Python / FastAPI / Scikit-Learn):** An asynchronous worker polls the ingestion streaming topics, computes advanced physiological metrics—such as the Cardiovascular Tension Index (CVI)—and executes a serialized ML pipeline binary (`.joblib`) for real-time anomaly risk grading.
4. **Orchestration & Triage (Camunda BPMN 2.0):** If a critical anomaly classification occurs, the Java core invokes an instance of a syntax-valid Camunda workflow process to orchestrate downstream clinical actions (automated alert firing, prioritization queues, and state audits).

---

## 📂 Repository Structure

This repository is structured as a unified workspace containing both the multi-module Maven Java ecosystem and the standalone Python analytical components:

```text
biopulse-analytics/
├── biopulse-backend/               # Parent Maven Multi-Module Java Project
│   ├── biopulse-core/              # Pure Domain Domain models, Business Invariants & In/Out Ports
│   ├── biopulse-infrastructure/    # Framework Adapters (REST Controllers, Spring Data JPA, Kafka Producer)
│   └── biopulse-boot/              # Application Bootstrap, Spring Main Context & Manual Wiring Config
├── biopulse-analytics-worker/      # Python Asynchronous analytical engine
│   ├── app/                        # FastAPI app, Kafka consumers, and ML inference handlers
│   └── ML/                         # Training scripts, notebooks, and serialized pipeline binaries
├── docker-compose.yml              # Unified local infrastructure blueprint (Postgres, Kafka, Camunda)
└── README.md                       # Core documentation
```

---

## 🛠️ Tech Stack & Tooling

* **Backend Core:** Java 21, Spring Boot 3.x, Spring Data JPA, Hibernate.
* **Process Orchestration:** Camunda Engine BPMN 2.0.
* **Data Stream & Streaming:** Apache Kafka (KRaft/Zookeeper architecture).
* **Machine Learning Engine:** Python 3.11+, FastAPI, Scikit-Learn, Pandas, Joblib.
* **Storage Layer:** PostgreSQL (Transactional Relational Database).
* **DevOps & Local Setup:** Docker, Docker Compose.

---

## 🚀 Quick Start & Environment Provisioning

### Prerequisites
Ensure you have the following resources installed on your development workstation:
* Docker & Docker Compose
* Java OpenJDK 21
* Python 3.11+
* Maven 3.9+

### Spin Up Infrastructure
To initialize the centralized data stores, event streams, and BPMN engines locally, clone the repository and run the unified compose layout from the root directory:

```bash
git clone [https://github.com/Pablooca/biopulse-analytics.git](https://github.com/Pablooca/biopulse-analytics.git)
cd biopulse-analytics
docker-compose up -d
```

---

## 📜 License

This project is licensed under the terms of the **MIT License**. See the [LICENSE](LICENSE) file for comprehensive legal permissions and conditions.

---

## 👥 Author

* **Pablo Oca Galeano** - *Lead Software & Data Engineer* - [GitHub Profile](https://github.com/Pablooca)
```