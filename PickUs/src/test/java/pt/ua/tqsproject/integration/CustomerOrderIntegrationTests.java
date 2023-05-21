package pt.ua.tqsproject.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ua.tqsproject.controllers.CustomerOrderController;
import pt.ua.tqsproject.entities.CustomerOrder;

import static pt.ua.tqsproject.services.CustomerOrderService.generateOrder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerOrderIntegrationTests {
	@Autowired
	CustomerOrderController customerOrderController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Test
	public void testCreate() {
		CustomerOrder customerOrder = generateOrder();
		
		String url = "http://localhost:" + port + "/api/v1/customerorders";
		ResponseEntity<CustomerOrder> entity = restTemplate.postForEntity(url, customerOrder, CustomerOrder.class);
		
		CustomerOrder customerOrderResult = customerOrderController.createOrder(customerOrder).getBody();
		
		Assertions.assertThat(customerOrder).isEqualTo(customerOrderResult);
	}
}
