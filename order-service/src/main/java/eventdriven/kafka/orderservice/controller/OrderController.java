package eventdriven.kafka.orderservice.controller;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;
    private static final List<String> Burgers = List.of("Cheese Burger", "Veggie Burger", "Chicken Burger");

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){

        // Validate the order
        if (this.Burgers.stream().noneMatch(b -> b.equals(order.getName()))) {
            return "Burger not found in the menu";
        }

        // Generate a unique order ID
        order.setOrderId(UUID.randomUUID().toString());

        // Create an order event and send it to Kafka
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully ...";
    }
}
