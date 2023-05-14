package pt.ua.tqsproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.data.CustomerOrder;
import pt.ua.tqsproject.service.CustomerOrderService;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {
	private final CustomerOrderService orderService;
	
	@Autowired
	public CustomerOrderController(CustomerOrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder order) {
		// Save the order using the OrderService
		CustomerOrder createdOrder = orderService.createOrder(order);
		return ResponseEntity.ok(createdOrder);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
		// Retrieve the order by ID using the OrderService
		Optional<CustomerOrder> order = orderService.getOrderById(id);
		return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}

