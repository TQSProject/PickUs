package pt.ua.tqsproject.unit.customerorder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ua.tqsproject.entities.*;
import pt.ua.tqsproject.repositories.CustomerOrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerOrderRepositoryTests {
	
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	
	@AfterEach
	@BeforeEach
	public void clearDatabase() {
		customerOrderRepository.deleteAll();
	}
	
	@Test
	public void testCreateReadDeleteCustomerOrder() {
		Product product = new Product("milk", 1.25);
		Customer customer = new Customer("Mike");
		ACP acp = new ACP("Continente", "Aveiro");
		CustomerOrder customerOrder = new CustomerOrder(List.of(product), customer, acp, Status.NOT_ACCEPTED);
		
		customerOrderRepository.save(customerOrder);
		
		Iterable<CustomerOrder> customerOrders = customerOrderRepository.findAll();
		assertThat(customerOrderRepository.findAll()).isNotEmpty();
		assertThat(customerOrders)
				.extracting(CustomerOrder::getProducts)
				.anyMatch(products -> products.contains(product));
		
		assertThat(customerOrders).extracting(CustomerOrder::getBuyer).containsOnly(customer);
		assertThat(customerOrders).extracting(CustomerOrder::getDropOffSite).containsOnly(acp);
		assertThat(customerOrders).extracting(CustomerOrder::getStatus).containsOnly(Status.NOT_ACCEPTED);
		
		customerOrderRepository.deleteAll();
		assertThat(customerOrderRepository.findAll()).isEmpty();
	}

}
