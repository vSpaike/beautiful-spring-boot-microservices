package eventdriven.kafka.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private double price;
    private boolean available;
}
