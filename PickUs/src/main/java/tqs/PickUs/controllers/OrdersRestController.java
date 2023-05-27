package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.OrderStatus;
import tqs.PickUs.services.OrdersService;

import java.util.List;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersRestController {
	@Autowired
	private OrdersService ordersService;

	public ResponseEntity<List<Order>> getOrders(
			@RequestParam(name = "store", required = false) String store,
			@RequestParam(name = "buyer", required = false) String buyer,
			@RequestParam(name = "acp", required = false) String acp,
			@RequestParam(name = "product", required = false) String product,
			@RequestParam(name = "status", required = false) String status) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("store", store);
		params.put("buyer", store);
		params.put("acp", acp);
		params.put("product", product);
		params.put("status", status);

		List<Order> orders = ordersService.getOrders(params);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
		Order order = ordersService.getOrderById(orderId);
		if (order != null)
			return ResponseEntity.ok(order);
		else
			return ResponseEntity.badRequest().body("Invalid order id");
	}

	@PostMapping
	public ResponseEntity<?> createOrders(@RequestBody ObjectNode json) {
		int totalOrdersCreated = ordersService.createOrders(json);
		if (totalOrdersCreated == 0)
			return ResponseEntity.badRequest().body("Invalid order(s) creation request");
		return ResponseEntity.ok().body(Integer.valueOf(totalOrdersCreated));
	}

	@PostMapping("{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestBody ObjectNode json) {
		Order order = ordersService.getOrderById(orderId);
		if (order == null)
			return ResponseEntity.badRequest().body("Invalid order id");

		if (json.get("status") == null || json.get("status").asText().isBlank())
			return ResponseEntity.badRequest()
					.body("Invalid request, please include a valid \"status\" field with the new status of the order");

		OrderStatus newStatus = null;
		try {
			String strNewStatus = json.get("status").asText().toUpperCase();
			newStatus = OrderStatus.valueOf(strNewStatus);
		} catch (IllegalArgumentException e) {
			// status field has invalid value
			return ResponseEntity.badRequest()
					.body("Invalid request, please include a valid \"status\" field with the new status of the order");
		}

		Order updatedOrder = ordersService.updateOrder(orderId, newStatus);
		if (updatedOrder != null)
			return ResponseEntity.ok(updatedOrder);
		else
			return ResponseEntity.internalServerError().body("Internal server error");
	}

}
