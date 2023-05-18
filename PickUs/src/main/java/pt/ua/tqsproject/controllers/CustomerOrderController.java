package pt.ua.tqsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.entities.CustomerOrder;
import pt.ua.tqsproject.services.CustomerOrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customerorders")
public class CustomerOrderController {
	private final CustomerOrderService orderService;
	
	@Autowired
	public CustomerOrderController(CustomerOrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder order) {
		// Save the order using the CustomerOrderService
		return ResponseEntity.ok(orderService.saveOrder(order));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
		// Retrieve the order by ID using the CustomerOrderService
		Optional<CustomerOrder> customerOrder = orderService.getOrderById(id);
		return customerOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("")
	public ResponseEntity<List<CustomerOrder>> getAllOrder() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}
}

