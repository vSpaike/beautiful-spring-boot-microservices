package eventdriven.kafka.stockservice.kafka;

import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.enums.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    // Simulate the stock quantity for the products
    private int stock = 1000;

    private ArrayList<Order> orders = new ArrayList<>();

    public OrderProducer orderProducer;


    // Écoute les événements de commande provenant de Kafka
    // Cet événement récupère la commande, vérifie la disponibilité du stock, met à jour le statut de la commande.
    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent event){
        // Log the received order event
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        Order order = event.getOrder();
        order.setStatus(OrderStatus.CREATED);

        // Check if there is enough stock for the order
        Integer orderQty = order.getTotalQuantity();

        if(orderQty > stock){
            LOGGER.info(String.format("Not enough stock for order id => %s", order.getOrderId()));
            return;
        }

        order.setStatus(OrderStatus.WAITING_PAYMENT);
        orders.add(order);
        // Créer un message au service de paiement pour indiquer que la commande est en attente de paiement

        orderProducer.sendMessage(event);

        // Update the stock quantity based on the order quantity
//        stock -= orderQty;
//        LOGGER.info(String.format("Order event received in stock service => %s", order.toString()));
//        LOGGER.info(String.format("Stock left in stock service => %d", stock));
        // save the order data into the database
    }

    // Cet évènement récupère la transaction de paiement, vérifie si le paiement est réussi, met à jour le statut de la commande et met à jour le stock en conséquence.
    @KafkaListener(
            topics = "${spring.kafka.topic.name1}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumePayment(OrderEvent event){
        // Log the received order event
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        Order order = event.getOrder();


        if(order.getStatus() == OrderStatus.PROCESSING){
            Integer orderQty = order.getTotalQuantity();
            stock -= orderQty;
            LOGGER.info(String.format("Stock left in stock service => %d", stock));
        }

        orders.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
        order.setStatus(OrderStatus.COMPLETED);
    }

}
