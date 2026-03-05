package eventdriven.kafka.stockservice.controller;

import eventdriven.kafka.stockservice.model.Dish;
import eventdriven.kafka.stockservice.dto.DishResponse;
import eventdriven.kafka.stockservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/dishes")
@CrossOrigin(origins = "*")
public class DishController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<?> getDishesForRestaurant(@PathVariable Long restaurantId) {
        List<DishResponse> dishes = restaurantService.getDishesForRestaurant(restaurantId).stream()
                .map(DishResponse::fromDish)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDish(
            @PathVariable Long restaurantId,
            @RequestBody Dish dish) {
        Dish created = restaurantService.addDish(restaurantId, dish);
        if (created == null) {
            return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DishResponse.fromDish(created), HttpStatus.CREATED);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<?> updateDish(
            @PathVariable Long restaurantId,
            @PathVariable Long dishId,
            @RequestBody Dish dish) {
        Dish updated = restaurantService.updateDish(restaurantId, dishId, dish);
        if (updated == null) {
            return new ResponseEntity<>("Dish or Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DishResponse.fromDish(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<?> deleteDish(
            @PathVariable Long restaurantId,
            @PathVariable Long dishId) {
        boolean deleted = restaurantService.deleteDish(restaurantId, dishId);
        if (!deleted) {
            return new ResponseEntity<>("Dish or Restaurant not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Dish deleted successfully", HttpStatus.NO_CONTENT);
    }
}
