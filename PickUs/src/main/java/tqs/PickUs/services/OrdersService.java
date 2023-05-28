package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.*;
import tqs.PickUs.repositories.ACPsRepository;
import tqs.PickUs.repositories.OrdersRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

	// Each order has 1 product name and count (how many of that product)
	// Returns num order created
	public int createOrders(ObjectNode json) {
		if (json.get("store") == null || json.get("store").asText().isBlank()
				|| json.get("acp") == null
				|| json.get("buyer") == null || json.get("buyer").asText().isBlank())
			return 0;

		String store = json.get("store").asText();
		String buyer = json.get("buyer").asText();
		ACP acp = null;
		if (json.get("acp").isInt()) {
			int acpId = json.get("acp").asInt();
			acp = acpsRepository.findById(acpId);
		} else {
			String acpName = json.get("acp").asText();
			acp = acpsRepository.findByName(acpName);
		}

		if (acp == null)
			return 0;

		// If 1 distinct product

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
				createdOrder.setCount(1);
				ordersRepository.save(createdOrder);
			}

			return 1;
		}

		// If many distinct products
		// "products": [ {name: "Toothpaste X", count: 2}, {name: "Luso Water 1L",count:
		// 1} ]
		// Make 1 order for each distinct product

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

			// Make 1 order for each distinct product
			for (int i = 0; i < numDifferentProducts; i++) {
				JsonNode thisDistinctProduct = json.get("products").get(i);
				String productName = json.get("products").get(i).get("name").asText();
				int count = 1;
				if (thisDistinctProduct.get("count") != null)
					count = thisDistinctProduct.get("count").asInt();
				Order createdOrder = new Order();
				createdOrder.setStore(store);
				createdOrder.setBuyer(buyer);
				createdOrder.setAcp(acp);
				createdOrder.setProduct(productName);
				createdOrder.setCount(count);
				ordersRepository.save(createdOrder);
			}

			return numDifferentProducts;
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
	
	public double averageOrderDelayInSeconds() {
		List<Order> orders = ordersRepository.findAll();
		double sum = 0;
		int amount = 0;
		for(Order order : orders) {
			LocalDateTime createdTime = order.getCreatedDateTime();
			LocalDateTime pickedUpTime = order.getPickedUpDateTime();
			if(pickedUpTime == null || createdTime == null)
				continue;
			long difference = createdTime.until(pickedUpTime, ChronoUnit.SECONDS);
			sum += difference;
			amount++;
			
			System.out.println(createdTime + ";" + pickedUpTime + ";" + difference + ";");
		}
		if(amount == 0)
			return 0;
		return sum/amount;
	}

	public int totalOrders() {
		return ordersRepository.findAll().size();
	}
	
	public Order save(Order order) {
		return ordersRepository.save(order);
	}

}