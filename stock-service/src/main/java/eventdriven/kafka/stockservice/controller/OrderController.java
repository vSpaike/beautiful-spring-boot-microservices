package eventdriven.kafka.stockservice.controller;

import eventdriven.kafka.basedomains.dto.Order;
import eventdriven.kafka.basedomains.dto.OrderEvent;
import eventdriven.kafka.basedomains.dto.ServiceResponse;
import eventdriven.kafka.basedomains.dto.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    // ✅ CORRECTION 1 : Utilisation d'une ConcurrentHashMap (modifiable et thread-safe)
    private static final Map<String, Integer> Stock = new ConcurrentHashMap<>(Map.of(
            "Cheese Burger", 10,
            "Veggie Burger", 15,
            "Chicken Burger", 20
    ));

    @PostMapping("/add")
    public ServiceResponse addStock(@RequestBody OrderEvent orderEvent){
        LOGGER.info("Received order for stock update: {}", orderEvent.getOrder());

        // ✅ CORRECTION 2 : Protection contre les valeurs nulles
        if (orderEvent == null || orderEvent.getOrder() == null || orderEvent.getOrder().getName() == null) {
            LOGGER.error("Order or Order name is null!");
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Invalid order: name cannot be null");
            return response;
        }

        if (Stock.containsKey(orderEvent.getOrder().getName())) {
            // Modification du stock sécurisée
            Stock.put(orderEvent.getOrder().getName(), Stock.get(orderEvent.getOrder().getName()) + orderEvent.getOrder().getQty());
            
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Stock added successfully for the order");
            LOGGER.info("Stock updated successfully for order: {}", orderEvent.getOrder());
            return response;
        } else {
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Burger not found in the menu");
            LOGGER.warn("Burger not found in the menu for order: {}", orderEvent.getOrder());
            return response;
        }
    }

    @PostMapping("/remove")
    public ServiceResponse removeStock(@RequestBody OrderEvent orderEvent){
        LOGGER.info("Received order for stock removal: {}", orderEvent);

        // ✅ CORRECTION 2 : Protection contre les valeurs nulles
        if (orderEvent == null || orderEvent.getOrder().getName() == null) {
            LOGGER.error("Order or Order name is null!");
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Invalid order: name cannot be null");
            return response;
        }

        // On vérifie que le burger existe d'abord pour éviter un NullPointerException sur le .get()
        if (Stock.containsKey(orderEvent.getOrder().getName()) && Stock.get(orderEvent.getOrder().getName()) >= orderEvent.getOrder().getQty()) {
            
            Stock.put(orderEvent.getOrder().getName(), Stock.get(orderEvent.getOrder().getName()) - orderEvent.getOrder().getQty());
            
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Stock removed successfully for the order");
            LOGGER.info("Stock removed successfully for order: {}", orderEvent.getOrder());
            return response;
        } else {
            ServiceResponse response = new ServiceResponse();
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Not enough stock for the order or Burger not found");
            LOGGER.warn("Not enough stock for order: {}", orderEvent.getOrder());
            return response;
        }
    }
}