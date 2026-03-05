package eventdriven.kafka.stockservice.service;

import eventdriven.kafka.stockservice.model.Restaurant;
import eventdriven.kafka.stockservice.model.Dish;
import eventdriven.kafka.stockservice.kafka.OrderEventMessage;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RestaurantService {
    private final Map<Long, Restaurant> restaurants = new HashMap<>();
    private final Map<String, String> orderStates = new LinkedHashMap<>();
    private long restaurantCounter = 1;
    private long dishCounter = 1;

    public RestaurantService() {
        // Initialize with sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        Restaurant r1 = new Restaurant(
                restaurantCounter++,
                "Pizza Palace",
                "123 Main Street",
                "123-456-7890",
                "Italian",
                4.5,
                new ArrayList<>());

        Restaurant r2 = new Restaurant(
                restaurantCounter++,
                "Burger Barn",
                "456 Oak Avenue",
                "098-765-4321",
                "American",
                4.2,
                new ArrayList<>());

        // Add dishes for restaurant 1
        r1.getDishes().add(new Dish(dishCounter++, r1.getId(), "Margherita Pizza",
                "Classic pizza with tomato and mozzarella", 12.99, true));
        r1.getDishes().add(new Dish(dishCounter++, r1.getId(), "Pepperoni Pizza", "Pizza with pepperoni", 14.99, true));
        r1.getDishes()
                .add(new Dish(dishCounter++, r1.getId(), "Pasta Carbonara", "Creamy pasta with bacon", 13.99, true));

        // Add dishes for restaurant 2
        r2.getDishes().add(new Dish(dishCounter++, r2.getId(), "Classic Burger", "Beef burger with lettuce and tomato",
                10.99, true));
        r2.getDishes()
                .add(new Dish(dishCounter++, r2.getId(), "Cheese Burger", "Burger with cheddar cheese", 11.99, true));
        r2.getDishes().add(new Dish(dishCounter++, r2.getId(), "French Fries", "Golden crispy fries", 4.99, true));

        restaurants.put(r1.getId(), r1);
        restaurants.put(r2.getId(), r2);
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurants.get(restaurantId);
    }

    public List<Restaurant> getAllRestaurants() {
        return new ArrayList<>(restaurants.values());
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setId(restaurantCounter++);
        restaurant.setDishes(new ArrayList<>());
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            restaurant.setName(updatedRestaurant.getName());
            restaurant.setAddress(updatedRestaurant.getAddress());
            restaurant.setPhone(updatedRestaurant.getPhone());
            restaurant.setCuisine(updatedRestaurant.getCuisine());
            restaurant.setRating(updatedRestaurant.getRating());
        }
        return restaurant;
    }

    public boolean deleteRestaurant(Long restaurantId) {
        return restaurants.remove(restaurantId) != null;
    }

    public Dish addDish(Long restaurantId, Dish dish) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            dish.setId(dishCounter++);
            dish.setRestaurantId(restaurantId);
            restaurant.getDishes().add(dish);
            return dish;
        }
        return null;
    }

    public List<Dish> getDishesForRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            return restaurant.getDishes();
        }
        return Collections.emptyList();
    }

    public Dish updateDish(Long restaurantId, Long dishId, Dish updatedDish) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            for (Dish dish : restaurant.getDishes()) {
                if (dish.getId().equals(dishId)) {
                    dish.setName(updatedDish.getName());
                    dish.setDescription(updatedDish.getDescription());
                    dish.setPrice(updatedDish.getPrice());
                    dish.setAvailable(updatedDish.isAvailable());
                    return dish;
                }
            }
        }
        return null;
    }

    public boolean deleteDish(Long restaurantId, Long dishId) {
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant != null) {
            return restaurant.getDishes().removeIf(dish -> dish.getId().equals(dishId));
        }
        return false;
    }

    public void processOrderCreatedEvent(OrderEventMessage message) {
        orderStates.put(message.getOrderId(), "PENDING");
    }

    public void processOrderStatusUpdatedEvent(OrderEventMessage message) {
        orderStates.put(message.getOrderId(), message.getStatus());
    }

    public Map<String, String> getOrderStates() {
        return Collections.unmodifiableMap(orderStates);
    }
}
