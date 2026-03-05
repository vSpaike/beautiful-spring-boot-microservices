package eventdriven.kafka.orderservice.kafka;

import eventdriven.kafka.orderservice.model.Order;
import eventdriven.kafka.orderservice.model.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEventMessage> kafkaTemplate;
    private final String orderCreatedTopic;
    private final String orderStatusUpdatedTopic;

    public OrderEventProducer(
            KafkaTemplate<String, OrderEventMessage> kafkaTemplate,
            @Value("${app.kafka.topic.order-created}") String orderCreatedTopic,
            @Value("${app.kafka.topic.order-status-updated}") String orderStatusUpdatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderCreatedTopic = orderCreatedTopic;
        this.orderStatusUpdatedTopic = orderStatusUpdatedTopic;
    }

    public void publishOrderCreated(Order order) {
        kafkaTemplate.send(orderCreatedTopic, order.getId(), toMessage("ORDER_CREATED", order));
    }

    public void publishOrderStatusUpdated(Order order) {
        kafkaTemplate.send(orderStatusUpdatedTopic, order.getId(), toMessage("ORDER_STATUS_UPDATED", order));
    }

    private OrderEventMessage toMessage(String eventType, Order order) {
        List<OrderItemEvent> items = order.getItems().stream()
                .map(this::toItemEvent)
                .collect(Collectors.toList());

        return new OrderEventMessage(
                eventType,
                order.getId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                items,
                order.getTotalPrice(),
                order.getStatus(),
                LocalDateTime.now());
    }

    private OrderItemEvent toItemEvent(OrderItem item) {
        return new OrderItemEvent(item.getDishId(), item.getDishName(), item.getQuantity(), item.getPrice());
    }
}
