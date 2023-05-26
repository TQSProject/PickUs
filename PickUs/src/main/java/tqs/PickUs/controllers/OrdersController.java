package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.Status;
import tqs.PickUs.services.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customerorders")
public class OrdersController {
	private final OrdersService ordersService;
	
	@Autowired
	public OrdersController(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		// Save the order using the CustomerOrderService
		return ResponseEntity.ok(ordersService.saveOrder(order));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getOrdersByCustomerAndStatus(@RequestParam Long customerID,
	                                                                  @RequestParam Status status) {
		List<Order> customerOrder = ordersService.getOrdersByCustomerAndStatus(customerID, status);
		return ResponseEntity.ok(customerOrder);
	}
}

