package eventdriven.kafka.stockservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import eventdriven.kafka.stockservice.service.RestaurantService;
import eventdriven.kafka.stockservice.model.Restaurant;
import eventdriven.kafka.stockservice.model.Dish;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
		"spring.kafka.consumer.bootstrap-servers=localhost:9092",
		"spring.kafka.listener.auto-startup=false"
})
class StockServiceApplicationTests {

	@Autowired
	private RestaurantService restaurantService;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetAllRestaurants() {
		var restaurants = restaurantService.getAllRestaurants();
		assertNotNull(restaurants);
		assertTrue(restaurants.size() > 0);
	}

	@Test
	void testGetRestaurantById() {
		Restaurant restaurant = restaurantService.getRestaurantById(1L);
		assertNotNull(restaurant);
		assertEquals(1L, restaurant.getId());
	}

	@Test
	void testCreateRestaurant() {
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setName("Test Restaurant");
		newRestaurant.setAddress("123 Test St");
		newRestaurant.setPhone("555-1234");
		newRestaurant.setCuisine("Test Cuisine");
		newRestaurant.setRating(4.0);
		newRestaurant.setDishes(new ArrayList<>());

		Restaurant created = restaurantService.createRestaurant(newRestaurant);
		assertNotNull(created);
		assertNotNull(created.getId());
		assertEquals("Test Restaurant", created.getName());
	}

	@Test
	void testGetDishesForRestaurant() {
		var dishes = restaurantService.getDishesForRestaurant(1L);
		assertNotNull(dishes);
		assertTrue(dishes.size() > 0);
	}

	@Test
	void testAddDish() {
		Dish dish = new Dish();
		dish.setName("Test Dish");
		dish.setDescription("Test Description");
		dish.setPrice(9.99);
		dish.setAvailable(true);

		Dish added = restaurantService.addDish(1L, dish);
		assertNotNull(added);
		assertNotNull(added.getId());
		assertEquals("Test Dish", added.getName());
	}
}
