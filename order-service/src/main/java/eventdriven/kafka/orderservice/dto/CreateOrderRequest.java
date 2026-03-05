package eventdriven.kafka.orderservice.dto;

import java.util.List;

public class CreateOrderRequest {
    private String customerId;
    private Long restaurantId;
    private List<OrderItemDto> items;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String customerId, Long restaurantId, List<OrderItemDto> items) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
