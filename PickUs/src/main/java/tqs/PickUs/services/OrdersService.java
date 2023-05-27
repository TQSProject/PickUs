package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.*;
import tqs.PickUs.repositories.ACPsRepository;
import tqs.PickUs.repositories.OrdersRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private ACPsRepository acpsRepository;

	public List<Order> getAllOrders() {
		return ordersRepository.findAll();

	}

	public Order getOrderById(int id) {
		return ordersRepository.findById(id);
	}

	public List<Order> getOrders(HashMap<String, String> params) {
		List<Order> orders = ordersRepository.findAll();

		if (params.containsKey("store") && params.get("store") != null && !params.get("store").isBlank()) {
			orders = orders.stream()
					.filter(order -> order.getStore().equalsIgnoreCase(params.get("store")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("buyer") && params.get("buyer") != null && !params.get("buyer").isBlank()) {
			orders = orders.stream()
					.filter(order -> order.getBuyer().equalsIgnoreCase(params.get("buyer")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("acp") && params.get("acp") != null && !params.get("acp").isBlank()) {
			orders = orders.stream()
					.filter(order -> order.getAcp().getName().equalsIgnoreCase(params.get("acp")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("product") && params.get("product") != null && !params.get("product").isBlank()) {
			orders = orders.stream()
					.filter(order -> StringUtils.containsIgnoreCase(order.getProduct(), params.get("product")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("status") && params.get("status") != null && !params.get("status").isBlank()) {
			orders = orders.stream()
					.filter(order -> order.getStatus().toString().equalsIgnoreCase(params.get("status")))
					.collect(Collectors.toList());
		}

		return orders;
	}

	// Returns num of orders created (1 order for each product)
	public int createOrders(ObjectNode json) {
		if (json.get("store") == null || json.get("store").asText().isBlank()
				|| json.get("acp") == null || json.get("acp").asText().isBlank()
				|| json.get("buyer") == null || json.get("buyer").asText().isBlank())
			return 0;

		String store = json.get("store").asText();
		String acpName = json.get("acp").asText();
		String buyer = json.get("buyer").asText();
		ACP acp = acpsRepository.findByName(acpName);
		if (acp == null)
			return 0;

		// If only 1 product
		// "product": "Toothpaste X"
		// "count": 2 (optional, default is 1)

		if (json.get("product") != null && !json.get("product").asText().isBlank()) {
			String product = json.get("product").asText();
			int count = 1;
			if (json.get("count") != null)
				count = json.get("count").asInt();

			for (int i = 0; i < count; i++) {
				Order createdOrder = new Order();
				createdOrder.setStore(store);
				createdOrder.setBuyer(buyer);
				createdOrder.setAcp(acp);
				createdOrder.setProduct(product);
				ordersRepository.save(createdOrder);
			}

			return count;
		}

		// If many products
		// "products": [ {name: "Toothpaste X", count: 2}, {name: "Luso Water 1L",
		// count: 1} ]
		// Make 1 order for each product (if count=3, make 3 orders)

		int totalOrders = 0;
		if (json.get("products") != null) {
			int numDifferentProducts = json.get("products").size();

			// Validate "products" field
			for (int i = 0; i < numDifferentProducts; i++) {
				JsonNode thisProduct = json.get("products").get(i);
				if (thisProduct.get("name") == null || thisProduct.get("name").asText().isBlank())
					return 0;
				if (thisProduct.get("count") != null && !thisProduct.get("count").isInt())
					return 0;
			}

			// Make orders
			for (int i = 0; i < numDifferentProducts; i++) {
				JsonNode thisDistinctProduct = json.get("products").get(i);
				String productName = json.get("products").get(i).get("name").asText();
				int count = 1;
				if (thisDistinctProduct.get("count") != null)
					count = thisDistinctProduct.get("count").asInt();
				for (int j = 0; j < count; j++) {
					Order createdOrder = new Order();
					createdOrder.setStore(store);
					createdOrder.setBuyer(buyer);
					createdOrder.setAcp(acp);
					createdOrder.setProduct(productName);
					ordersRepository.save(createdOrder);
				}
				totalOrders += count;
			}

			return totalOrders;
		}

		return 0;
	}

	public Order updateOrder(int orderId, OrderStatus newStatus) {
		Order order = ordersRepository.findById(orderId);
		if (order == null)
			return null;

		order.setStatus(newStatus);
		order = ordersRepository.save(order);
		return order;
		
	}

	public Order save(Order order) {
		return ordersRepository.save(order);
	}

}