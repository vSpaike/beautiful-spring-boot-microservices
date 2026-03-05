package eventdriven.kafka.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import eventdriven.kafka.orderservice.service.OrderService;
import eventdriven.kafka.orderservice.dto.CreateOrderRequest;
import eventdriven.kafka.orderservice.dto.OrderItemDto;
import eventdriven.kafka.orderservice.model.Order;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateOrder() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setCustomerId("CUST-123");
		request.setRestaurantId(1L);
		request.setItems(Arrays.asList(
				new OrderItemDto(1L, 2),
				new OrderItemDto(2L, 1)));

		Order order = orderService.createOrder(request);
		assertNotNull(order);
		assertNotNull(order.getId());
		assertEquals("CUST-123", order.getCustomerId());
		assertEquals("PENDING", order.getStatus());
	}

	@Test
	void testGetOrderById() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setCustomerId("CUST-456");
		request.setRestaurantId(1L);
		request.setItems(Arrays.asList(new OrderItemDto(1L, 1)));

		Order created = orderService.createOrder(request);
		Order retrieved = orderService.getOrderById(created.getId());

		assertNotNull(retrieved);
		assertEquals(created.getId(), retrieved.getId());
	}

	@Test
	void testGetAllOrders() {
		CreateOrderRequest request1 = new CreateOrderRequest();
		request1.setCustomerId("CUST-789");
		request1.setRestaurantId(1L);
		request1.setItems(Arrays.asList(new OrderItemDto(1L, 1)));

		orderService.createOrder(request1);

		assertTrue(orderService.getAllOrders().size() > 0);
	}

	@Test
	void testUpdateOrderStatus() {
		CreateOrderRequest request = new CreateOrderRequest();
		request.setCustomerId("CUST-001");
		request.setRestaurantId(1L);
		request.setItems(Arrays.asList(new OrderItemDto(1L, 1)));

		Order order = orderService.createOrder(request);
		Order updated = orderService.updateOrderStatus(order.getId(), "CONFIRMED");

		assertNotNull(updated);
		assertEquals("CONFIRMED", updated.getStatus());
	}
}
