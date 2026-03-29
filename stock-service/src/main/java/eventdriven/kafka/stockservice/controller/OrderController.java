package eventdriven.kafka.stockservice.controller;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.ServiceResponse;
import eventdriven.kafka.basedomains.dto.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final Map<String, Integer> Stock = Map.of(
            "Cheese Burger", 10,
            "Veggie Burger", 15,
            "Chicken Burger", 20
    );

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/add")
    public ServiceResponse addStock(@RequestBody Order order){
        LOGGER.info("Received order for stock update: {}", order);
        if (Stock.containsKey(order.getName())) {
            Stock.put(order.getName(), Stock.get(order.getName()) + order.getQty());
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Stock added successfully for the order");
            LOGGER.info("Stock updated successfully for order: {}", order);
            return response;
        } else {
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Burger not found in the menu");
            LOGGER.warn("Burger not found in the menu for order: {}", order);
            return response;
        }
    }

    @PostMapping("/remove")
    public ServiceResponse removeStock(@RequestBody Order order){
        LOGGER.info("Received order for stock removal: {}", order);
        if (Stock.get(order.getName()) >= order.getQty()) {
            Stock.put(order.getName(), Stock.get(order.getName()) - order.getQty());
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Stock removed successfully for the order");
            LOGGER.info("Stock removed successfully for order: {}", order);
            return response;
        } else {
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Not enough stock for the order");
            LOGGER.warn("Not enough stock for order: {}", order);
            return response;
        }
    }

}
