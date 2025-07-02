**Distributed microservices platform for air quality monitoring**

The system collects data from various IoT sensors placed in different areas of the city, analyzes air quality, detects unusual events (such as increased pollution) and provides a dashboard for authorities to monitor the situation, conduct in-depth analyses and take timely actions to mitigate risks and ensure public safety.

**Technologies**
- **Java 17, Spring Boot 3**
- **Spring Cloud Gateway, Eureka, Config Serger**
- **Kafka** (event-driven data streaming)
- **MongoDB, MySQL**
- **Redis** (rate limiting)
- **Zipkin**
- **Docker**
- **Resilience4j and DLQ** (error handling)

**Main Components**

| Service Name            | Description                                     |
|-------------------------|-------------------------------------------------|
| sensor-data-producer    | Produces raw sensor data to Kafka              |
| data-ingestion          | Consumes Kafka data and stores it in MongoDB   |
| data-processing         | Processes data for air quality analysis        |
| air-event-service       | Generates events and alerts                    |
| dashboard               | Expose REST API to display collected data      |
| api-gateway             | Gateway with rate limiting and circuit breaker |
| config-server           | Centralized configuration server               |
| service-registry        | Eureka service discovery                       |
| zipkin                  | Distributed tracing server                     |
| redis                   | Used for rate limiting                         |
| mongo + mongo-express   | NoSQL database + web UI                        |
| mysql + adminer         | Relational DB + admin UI                       |

