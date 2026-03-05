package eventdriven.kafka.stockservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEvent {
    private Long dishId;
    private String dishName;
    private int quantity;
    private double price;
}
