package eventdriven.kafka.orderservice.dto;

import eventdriven.kafka.orderservice.model.Order;
import eventdriven.kafka.orderservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private String customerId;
    private Long restaurantId;
    private double totalPrice;
    private String status;
    private String createdAt;
    private List<OrderItem> items;

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt().toString(),
                order.getItems());
    }
}
