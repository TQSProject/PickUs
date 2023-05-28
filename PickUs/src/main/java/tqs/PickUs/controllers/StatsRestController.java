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
		double delay = ordersService.averageOrderDelayInSeconds();
		return ResponseEntity.ok(delay);
	}
	
	@GetMapping("/total-orders")
	public ResponseEntity<?> totalOrders() {
		int numberOfOrders = ordersService.totalOrders();
		return ResponseEntity.ok(numberOfOrders);
	}
	
	

}
