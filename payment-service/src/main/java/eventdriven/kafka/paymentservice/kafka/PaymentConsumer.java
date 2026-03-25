package eventdriven.kafka.paymentservice.kafka;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.TransactionEvent;
import eventdriven.kafka.basedomains.enums.OrderStatus;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(TransactionEvent event){
        LOGGER.info(String.format("Transaction Event received in payment-service => %s", event.toString()));

        Order order = event.getTransaction().getOrder();
        Double totalCost = order.getTotalPrice();

        // Contrôle du prix
        /*
         A IMPLÉMENTER
         */

        order.setStatus(OrderStatus.PROCESSING);



    }
}
