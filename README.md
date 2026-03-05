# Food Delivery Microservices Architecture

A simple microservices architecture for a food delivery application built with Spring Boot and REST APIs.

## Architecture

This application consists of **2 independent microservices**:

- **Order Service** (Port 8081): Manages customer orders
- **Restaurant Service** (Port 8082): Manages restaurants and their menus

### Services Description

#### Order Service
- **Port**: 8081
- **Responsibilities**:
  - Create new orders
  - Track order status
  - Retrieve customer orders
  - Manage order lifecycle (PENDING, CONFIRMED, DELIVERED, CANCELLED)

#### Restaurant Service
- **Port**: 8082  
- **Responsibilities**:
  - Manage restaurant information
  - Manage restaurant menus (dishes)
  - Track dish availability
  - Provide restaurant listings

## API Endpoints

### Order Service (`http://localhost:8081/api`)

#### Orders Endpoints
```
POST   /orders                          - Create a new order
GET    /orders                          - Get all orders
GET    /orders/{orderId}                - Get specific order
GET    /orders/customer/{customerId}    - Get customer's orders
PUT    /orders/{orderId}/status         - Update order status
DELETE /orders/{orderId}                - Delete an order
```

#### Request Example: Create Order
```json
POST /api/orders
{
  "customerId": "CUST-001",
  "restaurantId": 1,
  "items": [
    {
      "dishId": 1,
      "quantity": 2
    },
    {
      "dishId": 2,
      "quantity": 1
    }
  ]
}
```

### Restaurant Service (`http://localhost:8082/api`)

#### Restaurants Endpoints
```
GET    /restaurants                     - Get all restaurants
GET    /restaurants/{restaurantId}      - Get restaurant details
POST   /restaurants                     - Create a restaurant
PUT    /restaurants/{restaurantId}      - Update restaurant
DELETE /restaurants/{restaurantId}      - Delete restaurant
```

#### Dishes Endpoints
```
GET    /restaurants/{restaurantId}/dishes              - Get all restaurant dishes
POST   /restaurants/{restaurantId}/dishes              - Add a new dish
PUT    /restaurants/{restaurantId}/dishes/{dishId}     - Update a dish
DELETE /restaurants/{restaurantId}/dishes/{dishId}     - Delete a dish
```

#### Request Example: Add Dish
```json
POST /api/restaurants/1/dishes
{
  "name": "Spaghetti Carbonara",
  "description": "Classic Italian pasta",
  "price": 15.99,
  "available": true
}
```

## Getting Started

### Prerequisites
- Docker and Docker Compose
- Java 17+
- Maven 3.6+

### Running with Docker Compose

1. Navigate to the project root directory
2. Build and run the services:
   ```bash
   docker-compose up -d --build
   ```

3. Verify services are running:
   - Order Service: `http://localhost:8081/api/orders`
   - Restaurant Service: `http://localhost:8082/api/restaurants`

4. Check service logs:
   ```bash
   docker-compose logs -f order-service
   docker-compose logs -f restaurant-service
   ```

5. Stop services:
   ```bash
   docker-compose down
   ```

### Running Locally (Without Docker)

1. Build Order Service:
   ```bash
   cd order-service
   mvn clean install
   mvn spring-boot:run
   ```

2. In another terminal, build Restaurant Service:
   ```bash
   cd stock-service
   mvn clean install
   mvn spring-boot:run
   ```

## Sample Data

The Restaurant Service automatically loads sample restaurants and dishes:

### Restaurants
- **Pizza Palace** (ID: 1) - Italian cuisine
  - Margherita Pizza - $12.99
  - Pepperoni Pizza - $14.99
  - Pasta Carbonara - $13.99

- **Burger Barn** (ID: 2) - American cuisine
  - Classic Burger - $10.99
  - Cheese Burger - $11.99
  - French Fries - $4.99

## In-Memory Storage

Both services use in-memory storage (HashMap) for simplicity:
- Data persists only while the service is running
- Restart the service to reset data
- Perfect for development and learning purposes

## Future Enhancements

- Add database persistence (PostgreSQL/MongoDB)
- Implement event-driven communication with Kafka
- Add user authentication and authorization
- Implement payment processing
- Add order notifications
- Create a mobile/web frontend

## Project Structure

```
.
├── docker-compose.yml          - Docker Compose configuration
├── order-service/              - Order management service
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
└── stock-service/              - Restaurant management service
    ├── src/
    ├── pom.xml
    └── Dockerfile
```

## Technologies Used

- **Spring Boot 3.1.5**: Framework
- **Spring Web**: REST APIs
- **Java 17**: Language
- **Maven**: Build tool
- **Docker**: Containerization
- **Lombok**: Reduce boilerplate code

---

Feel free to customize and extend this architecture for your food delivery application!
