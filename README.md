# MediaMarkt Product Service - Case study 🚀

This project is a reactive RESTful API developed for the MediaMarkt case study. It manages **Products** and **Categories** by implementing **"Ligthy"** **Hexagonal Architecture (Ports & Adapters)** and **Reactive Programming** to ensure maximum scalability, performance, and maintainability.

## 🛠️ Technology Stack

* **Java 17**
* **Spring Boot 3.x** (WebFlux for reactive and asynchronous programming)
* **Project Reactor** (Mono / Flux)
* **MongoDB** (Spring Data Reactive MongoDB)
* **Apache POI** (For reading Excel datasets)
* **SpringDoc OpenAPI (Swagger)** (API documentation)
* ...

## 🏗️ Architecture and Design Decisions

The project has been designed following the principles of **Clean Architecture**:

1. **Separation of Responsibilities (SRP):**

* **`domain`**: Pure models and business exceptions. Zero dependencies on external frameworks.
* **`application`**: Use Cases (`ManageProductsUseCase`) and Ports (Interfaces). These act as the central orchestrator of business rules.
* **`infrastructure`**: Secondary Adapters. This is where the MongoDB implementations, file reading (DataLoader), and AOP reside.
* **`interfaces`**: Primary Adapters. REST controllers that handle HTTP traffic and DTOs to prevent domain leaks (exposure of raw models).

2. **Reactive Programming (WebFlux):**
   Intensive use of non-blocking flows. A prime example is the dynamic resolution of category trees using the recursive `expandDeep()` operator to efficiently navigate from the leaves (products) to the root (absolute parents).
3. **Aspect-Oriented Programming (AOP):**
   A `LoggingAspect` class has been implemented in the infrastructure layer to standardize traceability and measure the actual execution times of `Mono` and `Flux` without cluttering the application layer.

## 🚀 How to Run the Project (Plug & Play)

To facilitate technical testing, the original Excel datasets have been included in the code along with a `DataLoader`. When the application starts, it will detect if the database is empty and automatically insert all the data so you can test the endpoints immediately.

*To be continued --> I have to improve this area adding docker.*

**For now, initial configuration you can use the following:**

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/masterdata
```

## **Local Execution:**

1. Clone the repo
2. Ensure you have MongoDb running local `localhost:27017`
3. Execute:
   ```bash
   ./mvnw spring-boot:run
   ```

**Access the Swagger Documentation:**

Once the application displays in the console that the `DataLoader` has finished importing the Excel files, navigate to:

👉 **http://localhost:8080/swagger-ui.html**

## 🧪 Key Endpoints

From Swagger, you can test all the standard CRUD endpoints for **Categories** and **Products**.
