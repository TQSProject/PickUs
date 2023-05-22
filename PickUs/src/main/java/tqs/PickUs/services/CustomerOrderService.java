package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.*;
import tqs.PickUs.repositories.CustomerOrderRepository;

import java.util.List;

@Service
public class CustomerOrderService {
	private final CustomerOrderRepository orderRepository;
	
	public static CustomerOrder generateOrder() {
		Product product = new Product("milk", 1.25);
		Customer customer = new Customer("Mike");
		ACP acp = new ACP("Continente", "Aveiro");
		return new CustomerOrder(List.of(product), customer, acp, Status.NOT_ACCEPTED);
	}
	
	@Autowired
	public CustomerOrderService(CustomerOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public CustomerOrder saveOrder(CustomerOrder order) {
		order.setStatus(Status.NOT_ACCEPTED);
		return orderRepository.save(order);
	}
	
	public List<CustomerOrder> getOrdersByCustomerAndStatus(Long customerID, Status status) {
		return orderRepository.findCustomerOrderByBuyerIdAndStatus(customerID, status);
	}
}