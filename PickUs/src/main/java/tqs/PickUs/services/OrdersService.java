package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.*;
import tqs.PickUs.repositories.OrdersRepository;

import java.util.List;

@Service
public class OrdersService {
	private final OrdersRepository ordersRepository;
	
	/* 
	public static Order generateOrder() {
		Product product = new Product("milk", 1.25);
		Customer customer = new Customer("Mike");
		ACP acp = new ACP("Continente", "Aveiro");
		return new CustomerOrder(List.of(product), customer, acp, Status.NOT_ACCEPTED);
	}
	*/
	
	@Autowired
	public OrdersService(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}
	
	public Order saveOrder(Order order) {
		//order.setStatus(Status.NOT_STARTED);
		return ordersRepository.save(order);
	}
	
	public List<Order> getOrdersByCustomerAndStatus(Long customerID, Status status) {
		//return ordersRepository.findCustomerOrderByBuyerIdAndStatus(customerID, status);
		return null;
	}
}