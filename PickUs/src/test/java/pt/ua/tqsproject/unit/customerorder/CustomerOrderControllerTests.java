package pt.ua.tqsproject.unit.customerorder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pt.ua.tqsproject.controllers.CustomerOrderController;
import pt.ua.tqsproject.entities.*;
import pt.ua.tqsproject.services.CustomerOrderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pt.ua.tqsproject.services.CustomerOrderService.generateOrder;


@ExtendWith(MockitoExtension.class)
public class CustomerOrderControllerTests {
	
	@InjectMocks
	CustomerOrderController customerOrderController;
	
	@Mock
	CustomerOrderService customerOrderService;
	
	@Test
	public void testSaveCustomerOrder() {
		CustomerOrder customerOrder = generateOrder();
		
		when(customerOrderService.saveOrder(any(CustomerOrder.class))).then(returnsFirstArg());
		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		ResponseEntity<CustomerOrder> responseEntity = customerOrderController.createOrder(customerOrder);
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(customerOrder);
	}
}