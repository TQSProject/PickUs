package tqs.PickUs.unit.customerorder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.PickUs.entities.CustomerOrder;
import tqs.PickUs.entities.Status;
import tqs.PickUs.repositories.CustomerOrderRepository;
import tqs.PickUs.services.CustomerOrderService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderServiceTests {
	@InjectMocks
	CustomerOrderService customerOrderService;
	
	@Mock
	CustomerOrderRepository customerOrderRepository;
	
	@Test
	public void testSaveCustomerOrder() {
		CustomerOrder customerOrder = CustomerOrderService.generateOrder();
		
		customerOrderService.saveOrder(customerOrder);
		
		verify(customerOrderRepository, times(1)).save(customerOrder);
	}
	
	@Test
	public void testSearchCustomerOrder() {
		CustomerOrder customerOrder = CustomerOrderService.generateOrder();
		
		customerOrderService.getOrdersByCustomerAndStatus(customerOrder.getBuyer().getId(), Status.NOT_ACCEPTED);
		
		verify(customerOrderRepository,
				times(1))
				.findCustomerOrderByBuyerIdAndStatus(customerOrder.getBuyer().getId(), Status.NOT_ACCEPTED);
	}
}
