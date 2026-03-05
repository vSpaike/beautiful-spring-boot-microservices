package eventdriven.kafka.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long dishId;
    private String dishName;
    private int quantity;
    private double price;
}
