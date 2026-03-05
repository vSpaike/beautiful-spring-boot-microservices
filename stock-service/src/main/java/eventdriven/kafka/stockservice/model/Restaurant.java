package eventdriven.kafka.stockservice.model;

import java.util.List;

public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String cuisine;
    private double rating;
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, String phone, String cuisine, double rating,
            List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cuisine = cuisine;
        this.rating = rating;
        this.dishes = dishes;
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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
