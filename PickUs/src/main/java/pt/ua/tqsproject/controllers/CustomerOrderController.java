package pt.ua.tqsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.entities.CustomerOrder;
import pt.ua.tqsproject.services.CustomerOrderService;

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
}

