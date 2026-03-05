package eventdriven.kafka.stockservice.dto;

import eventdriven.kafka.stockservice.model.Restaurant;

public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String cuisine;
    private double rating;

    public RestaurantResponse() {
    }

    public RestaurantResponse(Long id, String name, String address, String phone, String cuisine, double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static RestaurantResponse fromRestaurant(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getCuisine(),
                restaurant.getRating());
    }
}
