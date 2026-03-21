package eventdriven.kafka.stockservice.kafka;

import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    // Simulate the stock quantity for the products
    private int stock = 1000;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent event){
        // Log the received order event
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        Order order = event.getOrder();
        // Check if there is enough stock for the order
        if(order.getQty() > stock){
            LOGGER.info(String.format("Not enough stock for order id => %s", order.getOrderId()));
            return;
        }
        // Update the stock quantity based on the order quantity
        stock -= order.getQty();
        LOGGER.info(String.format("Order event received in stock service => %s", order.toString()));
        LOGGER.info(String.format("Stock left in stock service => %d", stock));
        // save the order data into the database
    }

}
