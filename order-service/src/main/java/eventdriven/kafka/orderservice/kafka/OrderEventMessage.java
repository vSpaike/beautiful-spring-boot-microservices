package eventdriven.kafka.orderservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventMessage {
    private String eventType;
    private String orderId;
    private String customerId;
    private Long restaurantId;
    private List<OrderItemEvent> items;
    private double totalPrice;
    private String status;
    private LocalDateTime timestamp;
}
