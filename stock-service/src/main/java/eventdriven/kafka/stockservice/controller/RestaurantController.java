package eventdriven.kafka.stockservice.controller;

import eventdriven.kafka.stockservice.model.Restaurant;
import eventdriven.kafka.stockservice.dto.RestaurantResponse;
import eventdriven.kafka.stockservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAllRestaurants().stream()
                .map(RestaurantResponse::fromRestaurant)
                .collect(Collectors.toList());
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestaurantResponse.fromRestaurant(restaurant), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(RestaurantResponse.fromRestaurant(created), HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody Restaurant restaurant) {
        Restaurant updated = restaurantService.updateRestaurant(restaurantId, restaurant);
        if (updated == null) {
            return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestaurantResponse.fromRestaurant(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId) {
        boolean deleted = restaurantService.deleteRestaurant(restaurantId);
        if (!deleted) {
            return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Restaurant deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/orders/states")
    public ResponseEntity<Map<String, String>> getConsumedOrderStates() {
        return new ResponseEntity<>(restaurantService.getOrderStates(), HttpStatus.OK);
    }
}
