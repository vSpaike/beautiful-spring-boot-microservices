# Food Delivery Microservices - Architecture & Development Guide

## Project Overview

This is a **lightweight microservices architecture** for a food delivery application, designed for learning and quick prototyping. The system consists of **2 independent REST-based microservices** with no external dependencies like Kafka or databases.

---

## System Architecture

```
┌─────────────────────────────────────────────────────────┐
│         Food Delivery Microservices System              │
├──────────────────────┬──────────────────────────────────┤
│   Order Service      │     Restaurant Service           │
│   (Port 8081)        │     (Port 8082)                  │
│                      │                                  │
│ • Create Orders      │ • Manage Restaurants             │
│ • Track Orders       │ • Manage Dishes/Menu             │
│ • Update Status      │ • List Restaurants               │
│ • Query Orders       │ • Track Availability             │
│                      │                                  │
│ Stack:               │ Stack:                           │
│ - Spring Boot Web    │ - Spring Boot Web                │
│ - REST APIs          │ - REST APIs                      │
│ - In-Memory Storage  │ - In-Memory Storage              │
│ - HashMap            │ - HashMap                        │
└──────────────────────┴──────────────────────────────────┘
```

---

## Service Breakdown

### 1. Order Service (Port 8081)

**Purpose**: Manage customer orders

**Key Classes**:
- `Order.java` - Core order entity
- `OrderItem.java` - Items within an order
- `OrderService.java` - Business logic (CRUD operations)
- `OrderController.java` - REST endpoints
- `CreateOrderRequest.java`, `OrderResponse.java` - DTOs

**Storage**: `HashMap<String, Order>` - stores orders by order ID

**Data Model**:
```java
Order {
  id: String (e.g., "ORD-1")
  customerId: String
  restaurantId: Long
  items: List<OrderItem>
  totalPrice: double
  status: String (PENDING, CONFIRMED, DELIVERED, CANCELLED)
  createdAt: LocalDateTime
  updatedAt: LocalDateTime
}
```

**REST Endpoints**:
```
POST   /api/orders                    - Create order
GET    /api/orders                    - List all orders
GET    /api/orders/{orderId}          - Get order details
GET    /api/orders/customer/{id}      - Get customer's orders
PUT    /api/orders/{orderId}/status   - Update status
DELETE /api/orders/{orderId}          - Delete order
```

### 2. Restaurant Service (Port 8082)

**Purpose**: Manage restaurants and their menus

**Key Classes**:
- `Restaurant.java` - Restaurant entity
- `Dish.java` - Menu item entity
- `RestaurantService.java` - Business logic
- `RestaurantController.java` - Restaurant endpoints
- `DishController.java` - Dish endpoints
- DTOs: `RestaurantResponse.java`, `DishResponse.java`

**Storage**: `HashMap<Long, Restaurant>` - stores restaurants by ID

**Data Models**:
```java
Restaurant {
  id: Long
  name: String
  address: String
  phone: String
  cuisine: String
  rating: double
  dishes: List<Dish>
}

Dish {
  id: Long
  restaurantId: Long
  name: String
  description: String
  price: double
  available: boolean
}
```

**REST Endpoints**:
```
GET    /api/restaurants                              - List restaurants
GET    /api/restaurants/{restaurantId}               - Get restaurant
POST   /api/restaurants                              - Create restaurant
PUT    /api/restaurants/{restaurantId}               - Update restaurant
DELETE /api/restaurants/{restaurantId}               - Delete restaurant

GET    /api/restaurants/{restaurantId}/dishes        - List dishes
POST   /api/restaurants/{restaurantId}/dishes        - Add dish
PUT    /api/restaurants/{restaurantId}/dishes/{id}   - Update dish
DELETE /api/restaurants/{restaurantId}/dishes/{id}   - Delete dish
```

---

## Technology Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Framework** | Spring Boot 3.1.5 | Application framework |
| **Language** | Java 17 | Programming language |
| **Web** | Spring Web | REST API support |
| **Build** | Maven 3.9 | Build tool |
| **Storage** | HashMap (In-Memory) | Data persistence |
| **Containerization** | Docker | Deployment |
| **Orchestration** | Docker Compose | Multi-service management |
| **Testing** | JUnit 5 | Unit testing |
| **Utils** | Lombok | Reduce boilerplate |

---

## Project Structure

```
beautiful-spring-boot-microservices/
├── docker-compose.yml              # Docker Compose configuration
├── README.md                        # Main documentation
├── QUICKSTART.md                    # Quick start guide
├── ARCHITECTURE.md                  # This file
│
├── order-service/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── mvnw, mvnw.cmd
│   └── src/
│       ├── main/java/eventdriven/kafka/orderservice/
│       │   ├── OrderServiceApplication.java
│       │   ├── model/
│       │   │   ├── Order.java
│       │   │   └── OrderItem.java
│       │   ├── dto/
│       │   │   ├── CreateOrderRequest.java
│       │   │   ├── OrderItemDto.java
│       │   │   └── OrderResponse.java
│       │   ├── controller/
│       │   │   └── OrderController.java
│       │   └── service/
│       │       └── OrderService.java
│       ├── main/resources/
│       │   └── application.properties
│       └── test/
│           └── java/...OrderServiceApplicationTests.java
│
└── stock-service/
    ├── pom.xml
    ├── Dockerfile
    ├── mvnw, mvnw.cmd
    └── src/
        ├── main/java/eventdriven/kafka/stockservice/
        │   ├── StockServiceApplication.java
        │   ├── model/
        │   │   ├── Restaurant.java
        │   │   └── Dish.java
        │   ├── dto/
        │   │   ├── RestaurantResponse.java
        │   │   └── DishResponse.java
        │   ├── controller/
        │   │   ├── RestaurantController.java
        │   │   └── DishController.java
        │   └── service/
        │       └── RestaurantService.java
        ├── main/resources/
        │   └── application.properties
        └── test/
            └── java/...StockServiceApplicationTests.java
```

---

## How It Works

### Order Flow

1. **Customer creates order**
   ```
   POST /api/orders
   {
     "customerId": "CUST-123",
     "restaurantId": 1,
     "items": [{"dishId": 1, "quantity": 2}]
   }
   ```

2. **Order Service**
   - Validates request
   - Calculates total price
   - Creates Order object
   - Stores in HashMap
   - Returns OrderResponse

3. **Order Status Lifecycle**
   ```
   PENDING → CONFIRMED → DELIVERED (or CANCELLED)
   ```

### Restaurant & Menu Flow

1. **Get all restaurants**
   ```
   GET /api/restaurants
   ```

2. **Get restaurant details with dishes**
   ```
   GET /api/restaurants/1
   ```

3. **Add new dish to restaurant**
   ```
   POST /api/restaurants/1/dishes
   {
     "name": "New Dish",
     "price": 15.99
   }
   ```

---

## Key Design Patterns

### 1. Service Layer Pattern
- Separates business logic from controllers
- `OrderService` and `RestaurantService` handle all operations
- Controllers only handle HTTP requests/responses

### 2. DTO (Data Transfer Object) Pattern
- `CreateOrderRequest` - input validation
- `OrderResponse` - controlled output format
- Decouples internal models from API contracts

### 3. Repository Pattern (Simulated)
- HashMap acts as in-memory repository
- Easy to replace with database later

### 4. REST API Best Practices
- Consistent URL structure
- Proper HTTP methods (GET, POST, PUT, DELETE)
- Appropriate status codes (200, 201, 404, etc.)
- JSON request/response bodies

---

## Data Persistence Strategy

### Current (In-Memory)
- **Storage**: HashMap
- **Scope**: Per JVM instance
- **Lifetime**: Until service restart
- **Use Case**: Development, testing, prototyping

### Future (Persistent)
```java
// Could add:
// 1. PostgreSQL with Spring Data JPA
@Entity
public class Order { ... }

// 2. MongoDB with Spring Data MongoDB
@Document
public class Restaurant { ... }

// 3. Both (CQRS pattern)
```

---

## Extension Points

### 1. Add Database Persistence
```xml
<!-- Add to pom.xml -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
</dependency>
```

### 2. Add Event-Driven Communication
```xml
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>
```

### 3. Add Authentication
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 4. Add API Documentation
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.0.0</version>
</dependency>
```

---

## Environment Variables

### Order Service (application.properties)
```properties
spring.application.name=order-service
server.port=8081
```

### Restaurant Service (application.properties)
```properties
spring.application.name=restaurant-service
server.port=8082
```

---

## Testing Strategy

### Unit Tests Included
- `OrderServiceApplicationTests.java`
  - testCreateOrder()
  - testGetOrderById()
  - testGetAllOrders()
  - testUpdateOrderStatus()

- `StockServiceApplicationTests.java`
  - testGetAllRestaurants()
  - testGetRestaurantById()
  - testCreateRestaurant()
  - testGetDishesForRestaurant()
  - testAddDish()

### Running Tests
```bash
# Order Service tests
cd order-service
mvn test

# Restaurant Service tests
cd stock-service
mvn test
```

---

## Performance Considerations

### Current Limitations
- In-memory storage = single JVM instance
- No distributed caching
- All orders/restaurants lost on restart
- No persistence across deployments

### Scalability Options
1. **Database**: PostgreSQL or MongoDB for persistence
2. **Caching**: Redis for frequently accessed data
3. **API Gateway**: Kong or Spring Cloud Gateway
4. **Load Balancer**: NGINX or load balancing service
5. **Message Queue**: Kafka for eventual consistency

---

## Deployment

### Docker Compose (Recommended)
```bash
docker-compose up -d --build
```

### Kubernetes (Future)
```yaml
# Could add:
apiVersion: apps/v1
kind: Deployment
metadata:
  name: order-service
spec:
  replicas: 3
  ...
```

---

## Troubleshooting

### Service won't start
- Check port availability: `lsof -i :8081` or `lsof -i :8082`
- Check Java version: `java -version` (needs Java 17+)
- Check Maven: `mvn -v` (needs Maven 3.6+)

### Services can't communicate
- Ensure both services are running
- Check Docker network: `docker network ls`
- Verify ports in docker-compose.yml

### Data not persisting
- **This is expected!** In-memory storage doesn't persist
- Restart service to clear data
- Consider adding database to persist data

---

## Next Steps

1. **Add Persistence**: Implement with PostgreSQL + Spring Data JPA
2. **Add Async Communication**: Integrate Kafka for events
3. **Add Security**: Implement JWT authentication
4. **Add Frontend**: Create React/Vue.js UI
5. **Add Monitoring**: Enable metrics with Micrometer
6. **Add CI/CD**: Setup GitHub Actions pipeline
7. **Scale**: Use Kubernetes for orchestration

---

Happy coding! 🚀
