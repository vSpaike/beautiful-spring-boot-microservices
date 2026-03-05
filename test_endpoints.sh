#!/bin/bash

# Configuration
ORDER_API="http://localhost:8083/api/orders"
RESTAURANT_API="http://localhost:8084/api/restaurants"

echo "======================================"
echo "    TESTING RESTAURANT ENDPOINTS      "
echo "======================================"

echo -e "\n1. Create a Restaurant (POST /api/restaurants)"
RES_POST=$(curl -s -X POST "$RESTAURANT_API" -H "Content-Type: application/json" -d '{
  "name": "Big Burger",
  "address": "123 Burger Lane",
  "phone": "555-0199",
  "cuisine": "American",
  "rating": 4.5
}')
echo $RES_POST

# Assuming auto-incremented ID starts at 1
# Optionally, we can try to parse the JSON if 'jq' is installed, but hardcoded fallback is safe for freshly launched instances.
RES_ID=$(echo $RES_POST | grep -o '"id":[0-9]*' | cut -d':' -f2)
if [ -z "$RES_ID" ]; then
    RES_ID=1
fi

echo -e "\n2. Get all Restaurants (GET /api/restaurants)"
curl -s -X GET "$RESTAURANT_API" | grep -o '.*' || echo ""

echo -e "\n\n3. Get a Restaurant by ID (GET /api/restaurants/$RES_ID)"
curl -s -X GET "$RESTAURANT_API/$RES_ID" | grep -o '.*' || echo ""

echo -e "\n\n4. Update a Restaurant (PUT /api/restaurants/$RES_ID)"
curl -s -X PUT "$RESTAURANT_API/$RES_ID" -H "Content-Type: application/json" -d '{
  "name": "Big Burger Updated",
  "address": "123 Burger Lane, Suite 100",
  "phone": "555-0199",
  "cuisine": "American",
  "rating": 4.8
}' | grep -o '.*' || echo ""

echo -e "\n\n======================================"
echo "      TESTING DISH ENDPOINTS          "
echo "======================================"

echo -e "\n5. Add a Dish to Restaurant (POST /api/restaurants/$RES_ID/dishes)"
DISH_POST=$(curl -s -X POST "$RESTAURANT_API/$RES_ID/dishes" -H "Content-Type: application/json" -d '{
  "name": "Cheeseburger",
  "description": "Double cheese, double beef",
  "price": 9.99,
  "available": true
}')
echo $DISH_POST

DISH_ID=$(echo $DISH_POST | grep -o '"id":[0-9]*' | cut -d':' -f2)
if [ -z "$DISH_ID" ]; then
    DISH_ID=1
fi

echo -e "\n6. Get all Dishes for Restaurant (GET /api/restaurants/$RES_ID/dishes)"
curl -s -X GET "$RESTAURANT_API/$RES_ID/dishes" | grep -o '.*' || echo ""

echo -e "\n\n7. Update a Dish (PUT /api/restaurants/$RES_ID/dishes/$DISH_ID)"
curl -s -X PUT "$RESTAURANT_API/$RES_ID/dishes/$DISH_ID" -H "Content-Type: application/json" -d '{
  "name": "Bacon Cheeseburger",
  "description": "With crispy bacon",
  "price": 11.99,
  "available": true
}' | grep -o '.*' || echo ""

echo -e "\n\n======================================"
echo "        TESTING ORDER ENDPOINTS       "
echo "======================================"

echo -e "\n8. Create an Order (POST /api/orders)"
ORDER_POST=$(curl -s -X POST "$ORDER_API" -H "Content-Type: application/json" -d '{
  "customerId": "CUST123",
  "restaurantId": '$RES_ID',
  "items": [
    {
      "dishId": '$DISH_ID',
      "quantity": 2,
      "price": 11.99
    }
  ]
}')
echo $ORDER_POST

ORDER_ID=$(echo $ORDER_POST | grep -o '"id":"[^"]*' | cut -d'"' -f4)
if [ -z "$ORDER_ID" ]; then
    ORDER_ID="ORD-1"
fi

echo -e "\n9. Get Order by ID (GET /api/orders/$ORDER_ID)"
curl -s -X GET "$ORDER_API/$ORDER_ID" | grep -o '.*' || echo ""

echo -e "\n\n10. Get all Orders (GET /api/orders)"
curl -s -X GET "$ORDER_API" | grep -o '.*' || echo ""

echo -e "\n\n11. Get Orders by Customer (GET /api/orders/customer/CUST123)"
curl -s -X GET "$ORDER_API/customer/CUST123" | grep -o '.*' || echo ""

echo -e "\n\n12. Update Order Status (PUT /api/orders/$ORDER_ID/status?status=COMPLETED)"
curl -s -X PUT "$ORDER_API/$ORDER_ID/status?status=COMPLETED" | grep -o '.*' || echo ""

echo -e "\n\n======================================"
echo "          CLEANUP (DELETE)            "
echo "======================================"

echo -e "\n13. Delete Order (DELETE /api/orders/$ORDER_ID)"
curl -s -X DELETE "$ORDER_API/$ORDER_ID"
echo -e "\nOrder deleted."

echo -e "\n14. Delete Dish (DELETE /api/restaurants/$RES_ID/dishes/$DISH_ID)"
curl -s -X DELETE "$RESTAURANT_API/$RES_ID/dishes/$DISH_ID"
echo -e "\nDish deleted."

echo -e "\n15. Delete Restaurant (DELETE /api/restaurants/$RES_ID)"
curl -s -X DELETE "$RESTAURANT_API/$RES_ID"
echo -e "\nRestaurant deleted."

echo -e "\n\nAll endpoints tested!\n"
