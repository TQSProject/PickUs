package tqs.PickUs.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tqs.PickUs.controllers.CustomerOrderController;
import tqs.PickUs.entities.CustomerOrder;
import tqs.PickUs.repositories.CustomerOrderRepository;
import tqs.PickUs.services.CustomerOrderService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerOrderIntegrationTests {
	@Autowired
	CustomerOrderController customerOrderController;
	
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@AfterEach
	public void clearDatabase() {
		customerOrderRepository.deleteAll();
		customerOrderRepository.flush();
	}
	
	
	@Test
	public void testCreate() {
		CustomerOrder customerOrder = CustomerOrderService.generateOrder();
		
		String url = "http://localhost:" + port + "/api/v1/customerorders";
		ResponseEntity<CustomerOrder> entity = restTemplate.postForEntity(url, customerOrder, CustomerOrder.class);
		
		CustomerOrder customerOrderResult = customerOrderController.createOrder(customerOrder).getBody();
		
		Assertions.assertThat(customerOrder).isEqualTo(customerOrderResult);
	}
}
