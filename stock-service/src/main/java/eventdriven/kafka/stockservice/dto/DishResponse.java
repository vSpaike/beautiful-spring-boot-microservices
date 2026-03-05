package eventdriven.kafka.stockservice.dto;

import eventdriven.kafka.stockservice.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResponse {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public static DishResponse fromDish(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.isAvailable());
    }
}
