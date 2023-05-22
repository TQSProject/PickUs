package tqs.PickUs.unit.customerorder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tqs.PickUs.controllers.CustomerOrderController;
import tqs.PickUs.entities.CustomerOrder;
import tqs.PickUs.services.CustomerOrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerOrderControllerTests {
	
	@InjectMocks
	CustomerOrderController customerOrderController;
	
	@Mock
	CustomerOrderService customerOrderService;
	
	@Test
	public void testSaveCustomerOrder() {
		CustomerOrder customerOrder = CustomerOrderService.generateOrder();
		
		when(customerOrderService.saveOrder(any(CustomerOrder.class))).then(returnsFirstArg());
		
		ResponseEntity<CustomerOrder> responseEntity = customerOrderController.createOrder(customerOrder);
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(customerOrder);
	}
	
	@Test
	public void testFindCustomerOrder() {
		CustomerOrder customerOrder = CustomerOrderService.generateOrder();
		Long customerId = customerOrder.getBuyer().getId();
		
		when(customerOrderService.getOrdersByCustomerAndStatus(customerId, customerOrder.getStatus())).thenReturn(List.of(customerOrder));
		
		ResponseEntity<List<CustomerOrder>> responseEntity = customerOrderController.getOrdersByCustomerAndStatus(customerId, customerOrder.getStatus());
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).contains(customerOrder);
	}
}