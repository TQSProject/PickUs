package pt.ua.tqsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqsproject.entities.CustomerOrder;
import pt.ua.tqsproject.entities.Status;
import pt.ua.tqsproject.repositories.CustomerOrderRepository;

@Service
public class CustomerOrderService {
	private final CustomerOrderRepository orderRepository;
	
	@Autowired
	public CustomerOrderService(CustomerOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public CustomerOrder saveOrder(CustomerOrder order) {
		order.setStatus(Status.NOT_ACCEPTED);
		return orderRepository.save(order);
	}
}