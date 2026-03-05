# Food Delivery API - Quick Start Guide

## Overview
This is a simple **Food Delivery Microservices** application with 2 services:
- **Order Service** - Handles customer orders (Port 8081)
- **Restaurant Service** - Manages restaurants and menus (Port 8082)

---

## 🚀 Quick Start with Docker Compose

### Step 1: Start the services
```bash
cd beautiful-spring-boot-microservices
docker-compose up -d --build
```

### Step 2: Verify services are running
```bash
# Check running containers
docker-compose ps

# Check order-service logs
docker-compose logs order-service

# Check restaurant-service logs
docker-compose logs restaurant-service
```

### Step 3: Test the APIs

#### Get all restaurants
```bash
curl http://localhost:8082/api/restaurants
```

#### Create an order
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "restaurantId": 1,
    "items": [
      {"dishId": 1, "quantity": 2},
      {"dishId": 2, "quantity": 1}
    ]
  }'
```

#### Get all orders
```bash
curl http://localhost:8081/api/orders
```

---

## 🔧 Running Locally (Without Docker)

### Order Service
```bash
cd order-service
mvn clean install
mvn spring-boot:run
# Service runs on http://localhost:8081
```

### Restaurant Service (in another terminal)
```bash
cd stock-service
mvn clean install
mvn spring-boot:run
# Service runs on http://localhost:8082
```

---

## 📋 Available Endpoints

### Order Service (Port 8081)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get all orders |
| POST | `/api/orders` | Create new order |
| GET | `/api/orders/{orderId}` | Get order details |
| GET | `/api/orders/customer/{customerId}` | Get customer's orders |
| PUT | `/api/orders/{orderId}/status` | Update order status |
| DELETE | `/api/orders/{orderId}` | Delete order |

### Restaurant Service (Port 8082)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/restaurants` | Get all restaurants |
| POST | `/api/restaurants` | Create restaurant |
| GET | `/api/restaurants/{restaurantId}` | Get restaurant details |
| PUT | `/api/restaurants/{restaurantId}` | Update restaurant |
| DELETE | `/api/restaurants/{restaurantId}` | Delete restaurant |
| GET | `/api/restaurants/{restaurantId}/dishes` | Get restaurant's dishes |
| POST | `/api/restaurants/{restaurantId}/dishes` | Add dish |
| PUT | `/api/restaurants/{restaurantId}/dishes/{dishId}` | Update dish |
| DELETE | `/api/restaurants/{restaurantId}/dishes/{dishId}` | Delete dish |

---

## 📦 Sample Data

When Restaurant Service starts, it loads sample data:

### Restaurants:
1. **Pizza Palace** (ID: 1) - Italian
   - Margherita Pizza ($12.99)
   - Pepperoni Pizza ($14.99)
   - Pasta Carbonara ($13.99)

2. **Burger Barn** (ID: 2) - American
   - Classic Burger ($10.99)
   - Cheese Burger ($11.99)
   - French Fries ($4.99)

---

## 🛑 Stop Services

```bash
# Stop and remove containers
docker-compose down

# View volumes
docker-compose down -v  # Remove volumes too
```

---

## 🔍 Troubleshooting

### Port already in use
```bash
# Kill process on port 8081
lsof -i :8081
kill -9 <PID>

# Kill process on port 8082
lsof -i :8082
kill -9 <PID>
```

### Docker permission denied
On Linux, use:
```bash
sudo docker-compose up -d --build
```

### Check service health
```bash
curl http://localhost:8081/api/orders
curl http://localhost:8082/api/restaurants
```

---

## 📝 Example Usage

### 1. List all restaurants
```bash
curl http://localhost:8082/api/restaurants
```

### 2. Get restaurant details with dishes
```bash
curl http://localhost:8082/api/restaurants/1
```

### 3. Create an order
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-123",
    "restaurantId": 1,
    "items": [
      {"dishId": 1, "quantity": 1},
      {"dishId": 3, "quantity": 2}
    ]
  }'
```

### 4. Check order status
```bash
curl http://localhost:8081/api/orders/ORD-1
```

### 5. Update order status
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CONFIRMED"
```

---

## 🎯 Next Steps

Consider adding:
- Database persistence (PostgreSQL/MongoDB)
- Event-driven communication with Kafka
- Authentication & Authorization
- Payment processing
- Order notifications
- Web/Mobile frontend

---

Happy coding! 🍕🍔
