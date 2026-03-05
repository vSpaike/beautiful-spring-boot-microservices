package eventdriven.kafka.orderservice.service;

import eventdriven.kafka.orderservice.model.Order;
import eventdriven.kafka.orderservice.model.OrderItem;
import eventdriven.kafka.orderservice.dto.CreateOrderRequest;
import eventdriven.kafka.orderservice.dto.OrderItemDto;
<<<<<<< HEAD
import eventdriven.kafka.orderservice.kafka.OrderEventProducer;
=======
import eventdriven.kafka.orderservice.kafka.OrderProducer;
>>>>>>> 21fbead (ça marche encore plus)
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final Map<String, Order> orders = new HashMap<>();
    private final OrderEventProducer orderEventProducer;
    private long orderCounter = 1;

<<<<<<< HEAD
    public OrderService(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
=======
    private final eventdriven.kafka.orderservice.kafka.OrderProducer orderProducer;

    public OrderService(eventdriven.kafka.orderservice.kafka.OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
>>>>>>> 21fbead (ça marche encore plus)
    }

    public Order createOrder(CreateOrderRequest request) {
        String orderId = "ORD-" + orderCounter++;

        // Calculate total price
        double totalPrice = request.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * 100) // Mock price
                .sum();

        List<OrderItem> orderItems = request.getItems().stream()
                .map(item -> new OrderItem(
                        item.getDishId(),
                        "Dish-" + item.getDishId(),
                        item.getQuantity(),
                        100.0))
                .collect(Collectors.toList());

        Order order = new Order(
                orderId,
                request.getCustomerId(),
                request.getRestaurantId(),
                orderItems,
                totalPrice,
                "PENDING",
                LocalDateTime.now(),
                LocalDateTime.now());

        orders.put(orderId, order);

<<<<<<< HEAD
        // Publish to Kafka so downstream services can react asynchronously.
        orderEventProducer.publishOrderCreated(order);
=======
        eventdriven.kafka.basedomains.dto.Order eventOrder = new eventdriven.kafka.basedomains.dto.Order(
                orderId,
                "Customer-" + request.getCustomerId(),
                request.getItems().stream().mapToInt(item -> item.getQuantity()).sum(),
                totalPrice);

        eventdriven.kafka.basedomains.dto.OrderEvent event = new eventdriven.kafka.basedomains.dto.OrderEvent(
                "Order status is in pending state",
                "PENDING",
                eventOrder);

        orderProducer.sendMessage(event);

>>>>>>> 21fbead (ça marche encore plus)
        return order;
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public List<eventdriven.kafka.orderservice.model.Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public eventdriven.kafka.orderservice.model.Order updateOrderStatus(String orderId, String status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            orderEventProducer.publishOrderStatusUpdated(order);
        }
        return order;
    }

    public boolean deleteOrder(String orderId) {
        return orders.remove(orderId) != null;
    }

    public List<eventdriven.kafka.orderservice.model.Order> getOrdersByCustomerId(String customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public List<eventdriven.kafka.orderservice.model.Order> getOrdersByRestaurantId(Long restaurantId) {
        return orders.values().stream()
                .filter(order -> order.getRestaurantId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}
