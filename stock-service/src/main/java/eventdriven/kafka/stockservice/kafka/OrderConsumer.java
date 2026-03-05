package eventdriven.kafka.stockservice.kafka;

import eventdriven.kafka.basedomains.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "orders_topic", groupId = "stock-group")
    public void consume(OrderEvent event) {
        LOGGER.info(String.format("Order event received in stock service => message: %s, status: %s",
                event.getMessage(), event.getStatus()));

        // We log the incoming order correctly. Further logic to deduct stock
        // could be added here later.
    }
}
