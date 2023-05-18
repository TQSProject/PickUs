package pt.ua.tqsproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqsproject.data.CustomerOrder;
import pt.ua.tqsproject.data.CustomerOrderRepository;
import pt.ua.tqsproject.data.Status;

import java.util.List;
import java.util.Optional;

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
	
	public List<CustomerOrder> getAllOrders() {
		return orderRepository.findAll();
	}
	
	public Optional<CustomerOrder> getOrderById(Long id) {
		return orderRepository.findById(id);
	}
}