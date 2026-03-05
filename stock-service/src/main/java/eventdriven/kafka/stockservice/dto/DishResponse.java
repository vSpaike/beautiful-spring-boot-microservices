package eventdriven.kafka.stockservice.dto;

import eventdriven.kafka.stockservice.model.Dish;

public class DishResponse {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public DishResponse() {
    }

    public DishResponse(Long id, Long restaurantId, String name, String description, double price, boolean available) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public static DishResponse fromDish(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.isAvailable());
    }
}
