package eventdriven.kafka.kitchenservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.OrderEvent;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private OrderProducer producer;

    public OrderConsumer(OrderProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name1}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent event){
        // Log the received order event
        LOGGER.info(String.format("Order event received in kitchen service => %s", event.toString()));
        LOGGER.info(String.format("Processing order with ID: %s", event.getOrder().getOrderId()));

        // Simulate order processing logic
        Order order = event.getOrder();
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);
        orderEvent.setMessage("order is being prepared in the kitchen");
        orderEvent.setStatus("IN_PREPARATION");

        // Send the updated order event back to Kafka
        producer.sendMessage(orderEvent);
    }

}