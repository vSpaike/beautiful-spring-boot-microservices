package eventdriven.kafka.orchestratorservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.ResponseStatus;
import eventdriven.kafka.basedomains.dto.ServiceResponse;

import org.springframework.web.client.RestTemplate;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private OrderProducer orderProducer;

    private final String KITCHEN_SERVICE_URL = "http://localhost:8082/api/v1/cook_order";
    private final String STOCK_SERVICE_URL = "http://localhost:8081/api/v1/remove";

    public OrderConsumer(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name1}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent event){
        // Log the received order event
        LOGGER.info(String.format("Order event received in kitchen service => %s", event.toString()));
        LOGGER.info(String.format("Processing order with ID: %s", event.getOrder().getOrderId()));

        Order order = event.getOrder();
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrder(order);

        RestTemplate restTemplate = new RestTemplate();

        ServiceResponse kitchenResponse = restTemplate.postForObject(KITCHEN_SERVICE_URL, event, ServiceResponse.class);

        if (kitchenResponse != null && kitchenResponse.getStatus() == ResponseStatus.SUCCESS) {
            LOGGER.info("Kitchen service processed the order successfully.");
        } else {
            LOGGER.error("Kitchen service failed to process the order.");
            // Handle failure case as needed
            orderEvent.setMessage("Failed to process order in kitchen.");
            orderEvent.setStatus("CANCELLED");

            this.orderProducer.sendMessage(orderEvent);
            return; // Return early if kitchen processing fails
        }

        ServiceResponse stockResponse = restTemplate.postForObject(STOCK_SERVICE_URL, event, ServiceResponse.class);

        if (stockResponse != null && stockResponse.getStatus() == ResponseStatus.SUCCESS) {
            LOGGER.info("Stock service updated successfully.");
        } else {
            LOGGER.error("Stock service failed to update.");
            // Handle failure case as needed
            orderEvent.setMessage("Failed to update stock.");
            orderEvent.setStatus("CANCELLED");

            this.orderProducer.sendMessage(orderEvent);
            return; // Return early if stock update fails
        }

        orderEvent.setMessage("Order processed successfully.");
        orderEvent.setStatus("IN_PROGRESS");

        this.orderProducer.sendMessage(orderEvent);
    }

}