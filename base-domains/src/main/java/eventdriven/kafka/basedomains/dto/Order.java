package eventdriven.kafka.basedomains.dto;

import eventdriven.kafka.basedomains.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderId;
    private String name;
    private HashMap<Product, Integer> products;
    private OrderStatus status;


    public double getTotalPrice() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public int getTotalQuantity() {
        return products.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
