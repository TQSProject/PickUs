package pt.ua.tqsproject.unit.customerorder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.tqsproject.entities.CustomerOrder;
import pt.ua.tqsproject.repositories.CustomerOrderRepository;
import pt.ua.tqsproject.services.CustomerOrderService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pt.ua.tqsproject.services.CustomerOrderService.generateOrder;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderServiceTests {
	@InjectMocks
	CustomerOrderService customerOrderService;
	
	@Mock
	CustomerOrderRepository customerOrderRepository;
	
	@Test
	public void testSaveCustomerOrder() {
		CustomerOrder customerOrder = generateOrder();
		
		customerOrderService.saveOrder(customerOrder);
		
		verify(customerOrderRepository, times(1)).save(customerOrder);
	}
}
