# Usage Examples - Food Delivery Microservices

Complete examples showing how to use the Food Delivery system for different scenarios.

---

## Scenario 1: Customer Orders Food

### Step 1: Browse Restaurants
```bash
curl http://localhost:8082/api/restaurants
```

Response shows available restaurants:
- Pizza Palace (ID: 1) - Italian
- Burger Barn (ID: 2) - American

### Step 2: View Menu of Pizza Palace
```bash
curl http://localhost:8082/api/restaurants/1
```

Response includes dishes with prices.

### Step 3: Customer Creates Order
Customer CUST-001 wants to order:
- 2x Margherita Pizza (Dish ID: 1)
- 1x Pasta Carbonara (Dish ID: 3)

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

Response:
```json
{
  "id": "ORD-1",
  "customerId": "CUST-001",
  "restaurantId": 1,
  "totalPrice": 400.0,
  "status": "PENDING",
  "createdAt": "2024-03-05T10:30:00"
}
```

### Step 4: Customer Checks Order Status
```bash
curl http://localhost:8081/api/orders/ORD-1
```

---

## Scenario 2: Restaurant Manager Updates Menu

### Add New Dish
Restaurant manager wants to add a special dish:

```bash
curl -X POST http://localhost:8082/api/restaurants/1/dishes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Truffle Pizza",
    "description": "Premium pizza with truffle oil",
    "price": 24.99,
    "available": true
  }'
```

### Update Dish Price
```bash
curl -X PUT http://localhost:8082/api/restaurants/1/dishes/4 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Truffle Pizza",
    "description": "Premium pizza with truffle oil",
    "price": 22.99,
    "available": true
  }'
```

### Mark Dish as Unavailable
```bash
curl -X PUT http://localhost:8082/api/restaurants/1/dishes/2 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pepperoni Pizza",
    "description": "Pizza with pepperoni",
    "price": 14.99,
    "available": false
  }'
```

---

## Scenario 3: Update Order Status

### Order Confirmed by Restaurant
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CONFIRMED"
```

### Order Being Delivered
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=DELIVERED"
```

### Customer Cancels Order
```bash
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CANCELLED"
```

---

## Scenario 4: Admin Views All Orders

### Get All Orders in System
```bash
curl http://localhost:8081/api/orders
```

Response shows all orders from all customers.

### Get Specific Customer's Orders
```bash
curl http://localhost:8081/api/orders/customer/CUST-001
```

---

## Scenario 5: Manage Multiple Restaurants

### Create New Restaurant
```bash
curl -X POST http://localhost:8082/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sushi Haven",
    "address": "789 Fish Lane",
    "phone": "555-SUSHI",
    "cuisine": "Japanese",
    "rating": 4.8
  }'
```

Returns:
```json
{
  "id": 3,
  "name": "Sushi Haven",
  "address": "789 Fish Lane",
  "phone": "555-SUSHI",
  "cuisine": "Japanese",
  "rating": 4.8
}
```

### Add Menu Items to New Restaurant
```bash
curl -X POST http://localhost:8082/api/restaurants/3/dishes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Tuna Roll",
    "description": "Fresh tuna with rice",
    "price": 8.99,
    "available": true
  }'
```

### Update Restaurant Info
```bash
curl -X PUT http://localhost:8082/api/restaurants/3 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sushi Haven Premium",
    "address": "789 Fish Lane",
    "phone": "555-SUSHI",
    "cuisine": "Japanese",
    "rating": 4.9
  }'
```

---

## Scenario 6: Complex Order Workflow

### Customer 1 Orders
```bash
# Customer 1 orders from Pizza Palace
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-001",
    "restaurantId": 1,
    "items": [{"dishId": 1, "quantity": 2}]
  }'
# Returns: ORD-1
```

### Customer 2 Orders
```bash
# Customer 2 orders from Burger Barn
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-002",
    "restaurantId": 2,
    "items": [{"dishId": 5, "quantity": 1}, {"dishId": 6, "quantity": 1}]
  }'
# Returns: ORD-2
```

### Track Specific Customer's Orders
```bash
curl http://localhost:8081/api/orders/customer/CUST-001
```

### Update Each Order
```bash
# Confirm order 1
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=CONFIRMED"

# Confirm order 2
curl -X PUT "http://localhost:8081/api/orders/ORD-2/status?status=CONFIRMED"

# Deliver order 1
curl -X PUT "http://localhost:8081/api/orders/ORD-1/status?status=DELIVERED"
```

### View All Orders
```bash
curl http://localhost:8081/api/orders
```

---

## Scenario 7: Handling Deleted Resources

### Delete a Dish
```bash
curl -X DELETE http://localhost:8082/api/restaurants/1/dishes/2
```

### Delete a Restaurant
```bash
curl -X DELETE http://localhost:8082/api/restaurants/1
```

### Try Accessing Deleted
```bash
# This will now return 404
curl http://localhost:8082/api/restaurants/1
```

### Delete an Order
```bash
curl -X DELETE http://localhost:8081/api/orders/ORD-1
```

---

## Scripted Workflow Example

### Bash Script to Test Full Workflow
```bash
#!/bin/bash

# Colors
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}Food Delivery API Test Script${NC}"
echo "=================================="

# Test 1: Get all restaurants
echo -e "\n${GREEN}1. Getting all restaurants...${NC}"
curl -s http://localhost:8082/api/restaurants | jq .

# Test 2: Create order
echo -e "\n${GREEN}2. Creating order...${NC}"
ORDER=$(curl -s -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST-TEST",
    "restaurantId": 1,
    "items": [{"dishId": 1, "quantity": 1}]
  }')
echo $ORDER | jq .

# Extract order ID
ORDER_ID=$(echo $ORDER | jq -r '.id')
echo "Order ID: $ORDER_ID"

# Test 3: Get order
echo -e "\n${GREEN}3. Getting order details...${NC}"
curl -s http://localhost:8081/api/orders/$ORDER_ID | jq .

# Test 4: Update status
echo -e "\n${GREEN}4. Updating order status...${NC}"
curl -s -X PUT "http://localhost:8081/api/orders/$ORDER_ID/status?status=CONFIRMED" | jq .

# Test 5: Get all orders
echo -e "\n${GREEN}5. Getting all orders...${NC}"
curl -s http://localhost:8081/api/orders | jq .

echo -e "\n${GREEN}Tests completed!${NC}"
```

Save as `test.sh` and run:
```bash
chmod +x test.sh
./test.sh
```

---

## Python Client Example

```python
import requests
import json

BASE_URL_ORDER = "http://localhost:8081/api"
BASE_URL_RESTAURANT = "http://localhost:8082/api"

# Get all restaurants
response = requests.get(f"{BASE_URL_RESTAURANT}/restaurants")
restaurants = response.json()
print("Available restaurants:")
for restaurant in restaurants:
    print(f"  - {restaurant['name']} (ID: {restaurant['id']})")

# Create order
order_data = {
    "customerId": "CUST-PYTHON",
    "restaurantId": 1,
    "items": [
        {"dishId": 1, "quantity": 2},
        {"dishId": 3, "quantity": 1}
    ]
}

response = requests.post(
    f"{BASE_URL_ORDER}/orders",
    json=order_data,
    headers={"Content-Type": "application/json"}
)

order = response.json()
order_id = order['id']
print(f"\nOrder created: {order_id}")

# Get order details
response = requests.get(f"{BASE_URL_ORDER}/orders/{order_id}")
order_details = response.json()
print(f"Order Total: ${order_details['totalPrice']}")

# Update status
response = requests.put(
    f"{BASE_URL_ORDER}/orders/{order_id}/status",
    params={"status": "CONFIRMED"}
)
print(f"Order status updated: {response.json()['status']}")
```

---

## JavaScript/Node.js Example

```javascript
const axios = require('axios');

const orderServiceUrl = 'http://localhost:8081/api';
const restaurantServiceUrl = 'http://localhost:8082/api';

async function testWorkflow() {
  try {
    // 1. Get restaurants
    console.log('1. Fetching restaurants...');
    const restaurantsRes = await axios.get(`${restaurantServiceUrl}/restaurants`);
    console.log(`Found ${restaurantsRes.data.length} restaurants`);

    // 2. Create order
    console.log('\n2. Creating order...');
    const orderRes = await axios.post(`${orderServiceUrl}/orders`, {
      customerId: 'CUST-NODE',
      restaurantId: 1,
      items: [
        { dishId: 1, quantity: 2 },
        { dishId: 2, quantity: 1 }
      ]
    });
    const orderId = orderRes.data.id;
    console.log(`Order created: ${orderId}`);

    // 3. Get order details
    console.log('\n3. Fetching order details...');
    const orderDetailsRes = await axios.get(`${orderServiceUrl}/orders/${orderId}`);
    console.log(`Order Status: ${orderDetailsRes.data.status}`);

    // 4. Update status
    console.log('\n4. Updating order status...');
    const updateRes = await axios.put(`${orderServiceUrl}/orders/${orderId}/status`, null, {
      params: { status: 'CONFIRMED' }
    });
    console.log(`New Status: ${updateRes.data.status}`);

  } catch (error) {
    console.error('Error:', error.message);
  }
}

testWorkflow();
```

---

## Integration Testing Checklist

- [ ] Services start without errors
- [ ] Can list restaurants
- [ ] Can create orders
- [ ] Can update order status
- [ ] Can list customer orders
- [ ] Can add new restaurant
- [ ] Can add dishes to restaurant
- [ ] Can update restaurant info
- [ ] Can delete orders
- [ ] Can delete restaurants
- [ ] Invalid requests return proper errors
- [ ] Concurrent orders work correctly
- [ ] Data persists during service uptime

---

## Performance Testing

### Simple Load Test
```bash
#!/bin/bash

echo "Creating 100 orders..."
for i in {1..100}; do
  curl -s -X POST http://localhost:8081/api/orders \
    -H "Content-Type: application/json" \
    -d "{
      \"customerId\": \"CUST-$i\",
      \"restaurantId\": $((RANDOM % 2 + 1)),
      \"items\": [{\"dishId\": $((RANDOM % 3 + 1)), \"quantity\": 1}]
    }" > /dev/null
  echo "Created order $i"
done

echo "Fetching all orders..."
curl -s http://localhost:8081/api/orders | jq '.| length'
```

---

## Troubleshooting Examples

### Issue: Port Already in Use
```bash
# Find process on port 8081
lsof -i :8081

# Kill process
kill -9 <PID>
```

### Issue: Connection Refused
```bash
# Check if services are running
docker-compose ps

# View logs
docker-compose logs order-service
docker-compose logs restaurant-service
```

### Issue: Empty Response
```bash
# Check service health
curl http://localhost:8081/api/orders

# If returns [], data was reset (expected - restart clears in-memory data)
```

---

Happy coding! 🚀
