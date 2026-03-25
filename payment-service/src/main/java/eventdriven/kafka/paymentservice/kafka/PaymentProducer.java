package eventdriven.kafka.paymentservice.kafka;

import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.TransactionEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public PaymentProducer(NewTopic topic, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TransactionEvent event) {
        LOGGER.info(String.format("Transaction event => %s", event.toString()));

        //Create a Message
        Message<TransactionEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
