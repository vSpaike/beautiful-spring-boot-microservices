package eventdriven.kafka.kitchenservice.controller;

import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.ServiceResponse;
import eventdriven.kafka.basedomains.dto.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final List<Boolean> Workers = new ArrayList<>(Arrays.asList(false, false, false, false, false));

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/cook_order")
    public ServiceResponse addStock(@RequestBody OrderEvent orderEvent){
        LOGGER.info("Received order for cooking: {}", orderEvent.getOrder());

        if (Workers.contains(false)) {
            Workers.set(Workers.indexOf(false), true);
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Order is being cooked by the worker");
            // Start a new thread to simulate the cooking process
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Simulate cooking time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Workers.set(Workers.indexOf(true), false); // Mark the worker as available again
                }
            }).start();
            LOGGER.info("Order is being cooked by the worker for order: {}", orderEvent.getOrder());
            return response;
        } else {
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("No available workers to cook the order");
            LOGGER.warn("No available workers to cook the order for order: {}", orderEvent.getOrder());
            return response;
        }
    }

}
