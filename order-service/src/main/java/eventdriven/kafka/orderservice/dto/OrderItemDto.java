package eventdriven.kafka.orderservice.dto;

public class OrderItemDto {
    private Long dishId;
    private int quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(Long dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
