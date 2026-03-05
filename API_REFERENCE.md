# API Reference - Food Delivery Microservices

## Base URLs
- **Order Service**: `http://localhost:8081/api`
- **Restaurant Service**: `http://localhost:8082/api`

---

## Order Service API

### 1. Create Order
**Endpoint**: `POST /orders`

**Description**: Create a new order from a customer

**Request Body**:
```json
{
  "customerId": "CUST-001",
  "restaurantId": 1,
  "items": [
    {
      "dishId": 1,
      "quantity": 2
    },
    {
      "dishId": 3,
      "quantity": 1
    }
  ]
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "restaurantId": 1,
    "items": [
      {"dishId": 1, "quantity": 2},
      {"dishId": 3, "quantity": 1}
    ]
  }'
```

**Response (201 Created)**:
```json
{
  "id": "ORD-1",
  "customerId": "CUST-001",
  "restaurantId": 1,
  "totalPrice": 300.0,
  "status": "PENDING",
  "createdAt": "2024-03-05T10:30:00"
}
```

---

### 2. Get All Orders
**Endpoint**: `GET /orders`

**Description**: Retrieve all orders in the system

**cURL Example**:
```bash
curl http://localhost:8081/api/orders
```

**Response (200 OK)**:
```json
[
  {
    "id": "ORD-1",
    "customerId": "CUST-001",
    "restaurantId": 1,
    "totalPrice": 300.0,
    "status": "PENDING",
    "createdAt": "2024-03-05T10:30:00"
  },
  {
    "id": "ORD-2",
    "customerId": "CUST-002",
    "restaurantId": 2,
    "totalPrice": 150.0,
    "status": "CONFIRMED",
    "createdAt": "2024-03-05T11:00:00"
  }
]
```

---

### 3. Get Specific Order
**Endpoint**: `GET /orders/{orderId}`

**Description**: Get details of a specific order

**Path Parameters**:
- `orderId` (string, required): Order ID (e.g., "ORD-1")

**cURL Example**:
```bash
curl http://localhost:8081/api/orders/ORD-1
```

**Response (200 OK)**:
```json
{
  "id": "ORD-1",
  "customerId": "CUST-001",
  "restaurantId": 1,
  "totalPrice": 300.0,
  "status": "PENDING",
  "createdAt": "2024-03-05T10:30:00"
}
```

---

### 4. Get Customer's Orders
**Endpoint**: `GET /orders/customer/{customerId}`

**Description**: Get all orders for a specific customer

**Path Parameters**:
- `customerId` (string, required): Customer ID (e.g., "CUST-001")

**cURL Example**:
```bash
curl http://localhost:8081/api/orders/customer/CUST-001
```

**Response (200 OK)**:
```json
[
  {
    "id": "ORD-1",
    "customerId": "CUST-001",
    "restaurantId": 1,
    "totalPrice": 300.0,
    "status": "PENDING",
    "createdAt": "2024-03-05T10:30:00"
  }
]
```

---

### 5. Update Order Status
**Endpoint**: `PUT /orders/{orderId}/status`

**Description**: Update the status of an order

**Path Parameters**:
- `orderId` (string, required): Order ID (e.g., "ORD-1")

**Query Parameters**:
- `status` (string, required): New status (PENDING, CONFIRMED, DELIVERED, CANCELLED)

**cURL Example**:
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CONFIRMED"
```

**Response (200 OK)**:
```json
{
  "id": "ORD-1",
  "customerId": "CUST-001",
  "restaurantId": 1,
  "totalPrice": 300.0,
  "status": "CONFIRMED",
  "createdAt": "2024-03-05T10:30:00"
}
```

---

### 6. Delete Order
**Endpoint**: `DELETE /orders/{orderId}`

**Description**: Delete an order

**Path Parameters**:
- `orderId` (string, required): Order ID (e.g., "ORD-1")

**cURL Example**:
```bash
curl -X DELETE http://localhost:8081/api/orders/ORD-1
```

**Response (204 No Content)**:
```
(No content)
```

---

## Restaurant Service API

### 1. Get All Restaurants
**Endpoint**: `GET /restaurants`

**Description**: Retrieve all restaurants

**cURL Example**:
```bash
curl http://localhost:8082/api/restaurants
```

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "name": "Pizza Palace",
    "address": "123 Main Street",
    "phone": "123-456-7890",
    "cuisine": "Italian",
    "rating": 4.5
  },
  {
    "id": 2,
    "name": "Burger Barn",
    "address": "456 Oak Avenue",
    "phone": "098-765-4321",
    "cuisine": "American",
    "rating": 4.2
  }
]
```

---

### 2. Get Restaurant Details
**Endpoint**: `GET /restaurants/{restaurantId}`

**Description**: Get details of a specific restaurant (with dishes)

**Path Parameters**:
- `restaurantId` (integer, required): Restaurant ID

**cURL Example**:
```bash
curl http://localhost:8082/api/restaurants/1
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "name": "Pizza Palace",
  "address": "123 Main Street",
  "phone": "123-456-7890",
  "cuisine": "Italian",
  "rating": 4.5,
  "dishes": [
    {
      "id": 1,
      "restaurantId": 1,
      "name": "Margherita Pizza",
      "description": "Classic pizza with tomato and mozzarella",
      "price": 12.99,
      "available": true
    },
    {
      "id": 2,
      "restaurantId": 1,
      "name": "Pepperoni Pizza",
      "description": "Pizza with pepperoni",
      "price": 14.99,
      "available": true
    }
  ]
}
```

---

### 3. Create Restaurant
**Endpoint**: `POST /restaurants`

**Description**: Create a new restaurant

**Request Body**:
```json
{
  "name": "New Restaurant",
  "address": "999 New Street",
  "phone": "555-1234",
  "cuisine": "Asian",
  "rating": 3.5
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8082/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Restaurant",
    "address": "999 New Street",
    "phone": "555-1234",
    "cuisine": "Asian",
    "rating": 3.5
  }'
```

**Response (201 Created)**:
```json
{
  "id": 3,
  "name": "New Restaurant",
  "address": "999 New Street",
  "phone": "555-1234",
  "cuisine": "Asian",
  "rating": 3.5
}
```

---

### 4. Update Restaurant
**Endpoint**: `PUT /restaurants/{restaurantId}`

**Path Parameters**:
- `restaurantId` (integer, required): Restaurant ID

**Request Body**: Same as create

**cURL Example**:
```bash
curl -X PUT http://localhost:8082/api/restaurants/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Pizza Palace",
    "address": "123 Main Street",
    "phone": "123-456-7890",
    "cuisine": "Italian",
    "rating": 4.7
  }'
```

---

### 5. Delete Restaurant
**Endpoint**: `DELETE /restaurants/{restaurantId}`

**cURL Example**:
```bash
curl -X DELETE http://localhost:8082/api/restaurants/1
```

**Response (204 No Content)**

---

## Dishes API

### 1. Get Restaurant's Dishes
**Endpoint**: `GET /restaurants/{restaurantId}/dishes`

**Description**: Get all dishes for a restaurant

**cURL Example**:
```bash
curl http://localhost:8082/api/restaurants/1/dishes
```

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "restaurantId": 1,
    "name": "Margherita Pizza",
    "description": "Classic pizza with tomato and mozzarella",
    "price": 12.99,
    "available": true
  },
  {
    "id": 2,
    "restaurantId": 1,
    "name": "Pepperoni Pizza",
    "description": "Pizza with pepperoni",
    "price": 14.99,
    "available": true
  }
]
```

---

### 2. Add Dish to Restaurant
**Endpoint**: `POST /restaurants/{restaurantId}/dishes`

**Path Parameters**:
- `restaurantId` (integer, required): Restaurant ID

**Request Body**:
```json
{
  "name": "Vegetarian Pizza",
  "description": "Pizza with fresh vegetables",
  "price": 13.99,
  "available": true
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8082/api/restaurants/1/dishes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Vegetarian Pizza",
    "description": "Pizza with fresh vegetables",
    "price": 13.99,
    "available": true
  }'
```

**Response (201 Created)**:
```json
{
  "id": 4,
  "restaurantId": 1,
  "name": "Vegetarian Pizza",
  "description": "Pizza with fresh vegetables",
  "price": 13.99,
  "available": true
}
```

---

### 3. Update Dish
**Endpoint**: `PUT /restaurants/{restaurantId}/dishes/{dishId}`

**Path Parameters**:
- `restaurantId` (integer, required): Restaurant ID
- `dishId` (integer, required): Dish ID

**cURL Example**:
```bash
curl -X PUT http://localhost:8082/api/restaurants/1/dishes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Classic Margherita Pizza",
    "description": "Updated description",
    "price": 13.49,
    "available": true
  }'
```

---

### 4. Delete Dish
**Endpoint**: `DELETE /restaurants/{restaurantId}/dishes/{dishId}`

**cURL Example**:
```bash
curl -X DELETE http://localhost:8082/api/restaurants/1/dishes/1
```

**Response (204 No Content)**

---

## Sample Workflow

### 1. List all restaurants
```bash
curl http://localhost:8082/api/restaurants
```

### 2. Get restaurant 1 with its menu
```bash
curl http://localhost:8082/api/restaurants/1
```

### 3. Create an order from restaurant 1
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-123",
    "restaurantId": 1,
    "items": [
      {"dishId": 1, "quantity": 2},
      {"dishId": 2, "quantity": 1}
    ]
  }'
```

### 4. Get the order status
```bash
curl http://localhost:8081/api/orders/ORD-1
```

### 5. Update order status
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CONFIRMED"
```

### 6. Get all customer orders
```bash
curl http://localhost:8081/api/orders/customer/CUST-123
```

---

## Error Responses

### 404 Not Found
```json
{
  "status": 404,
  "message": "Order not found"
}
```

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Invalid request body"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "Internal server error"
}
```

---

## Status Codes Reference

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Request successful, no content to return |
| 400 | Bad Request - Invalid request |
| 404 | Not Found - Resource not found |
| 500 | Server Error - Internal server error |

---

## Tips for Testing

1. **Use Postman**: Import these endpoints into Postman for easier testing
2. **Use HTTPie**: `http POST localhost:8081/api/orders ...`
3. **Use VS Code REST Client**: Save requests in `.http` files
4. **Keep IDs handy**: Remember order/restaurant IDs from previous requests

Happy testing! 🚀
