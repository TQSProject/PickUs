package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.PickUs.services.OrdersService;


@RestController
@RequestMapping("/api/v1/statistics")
public class StatsRestController {
	
	@Autowired
	OrdersService ordersService;
	
	@GetMapping("/order-delay")
	public ResponseEntity<?> averageOrderDelayInSeconds() {
		return ResponseEntity.ok(ordersService.averageOrderDelayInSeconds());
	}
	
	@GetMapping("/total-orders")
	public ResponseEntity<?> totalOrders() {
		return ResponseEntity.ok(ordersService.totalOrders());
	}
	
	@GetMapping("/total-orders/completed")
	public ResponseEntity<?> totalOrdersCompleted() {
		return ResponseEntity.ok(ordersService.totalOrdersCompleted());
	}
	
	@GetMapping("/total-orders/cancelled")
	public ResponseEntity<?> totalOrdersCancelled() {
		return ResponseEntity.ok(ordersService.totalOrdersCancelled());
	}
	
	@GetMapping("/total-orders/awaiting-approval")
	public ResponseEntity<?> totalOrdersAwaitingApproval() {
		return ResponseEntity.ok(ordersService.totalOrdersWaitingApproval());
	}

}
