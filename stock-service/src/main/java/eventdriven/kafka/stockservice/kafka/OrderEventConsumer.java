package eventdriven.kafka.stockservice.kafka;

import eventdriven.kafka.stockservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final RestaurantService restaurantService;

    public OrderEventConsumer(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @KafkaListener(topics = "${app.kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreated(OrderEventMessage message) {
        restaurantService.processOrderCreatedEvent(message);
    }

    @KafkaListener(topics = "${app.kafka.topic.order-status-updated}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderStatusUpdated(OrderEventMessage message) {
        restaurantService.processOrderStatusUpdatedEvent(message);
    }
}
