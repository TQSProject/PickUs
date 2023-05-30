package tqs.PickUs.Orders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.OrderStatus;
import tqs.PickUs.repositories.OrdersRepository;
import tqs.PickUs.services.OrdersService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test scenario: verify the logic of the Service, mocking the response of the
 * datasource
 * Results in standard unit test with mocks
 */
@ExtendWith(MockitoExtension.class)
public class OrdersServiceTests {
	@Mock(strictness = Mock.Strictness.LENIENT)
	private OrdersRepository ordersRepository;
	
	@InjectMocks
	private OrdersService ordersService;
	
	private Order leite, amendoas;
	private List<Order> orders;
	
	private Order createOrder1() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Leite";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	private Order createOrder2() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Amendoas";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	@BeforeEach
	public void setup() {
		leite = createOrder1();
		leite.setId(1);
		amendoas = createOrder2();
		leite.setId(2);
		orders = List.of(leite, amendoas);
		
		updateMockedRepo();
	}
	
	public void updateMockedRepo() {
		Mockito.when(ordersRepository.findAll()).thenReturn(orders);
		
		Mockito.when(ordersRepository.findById(leite.getId())).thenReturn(leite);
		Mockito.when(ordersRepository.findById(amendoas.getId())).thenReturn(amendoas);
	}
	
	@Test
	public void testGetOrdersWithFilters() {
		HashMap<String, String> params = new HashMap<>();
		params.put("store", "FNAC");
		List<Order> ordersFNAC = ordersService.getOrders(params);
		Assertions.assertEquals(ordersFNAC.size(), 2);
		Assertions.assertTrue(ordersFNAC.containsAll(Arrays.asList(leite, amendoas)));
		
		params = new HashMap<>();
		params.put("buyer", "Afonso");
		List<Order> ordersAfonso = ordersService.getOrders(params);
		Assertions.assertEquals(ordersAfonso.size(), 2);
		Assertions.assertTrue(ordersAfonso.containsAll(Arrays.asList(leite, amendoas)));
		
		params = new HashMap<>();
		params.put("product", "Amendoas");
		List<Order> ordersAmendoas = ordersService.getOrders(params);
		Assertions.assertEquals(ordersAmendoas.size(), 1);
		Assertions.assertTrue(ordersAmendoas.contains(amendoas));
		
		
	}
	
	@Test
	public void testUpdateOrderStatus() {
		// Return the argument
		Mockito.when(ordersRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
		
		for (Order order : orders)
			Assertions.assertEquals(order.getStatus(), OrderStatus.WAITING_ADMIN_APPROVAL);
		
		Order savedLeite = ordersService.updateOrder(leite.getId(), OrderStatus.DELIVERING);
		Order savedAmendoas = ordersService.updateOrder(amendoas.getId(), OrderStatus.DELIVERING);
		
		leite.setStatus(OrderStatus.DELIVERING);
		amendoas.setStatus(OrderStatus.DELIVERING);
		updateMockedRepo();
		
		Assertions.assertEquals(ordersService.getOrderById(leite.getId()).getStatus(),
				OrderStatus.DELIVERING);
		Assertions.assertNotNull(savedAmendoas);
		Assertions.assertNotNull(savedLeite);
		Assertions.assertEquals(savedLeite, leite);
		Assertions.assertEquals(savedLeite.getStatus(), leite.getStatus());
		Assertions.assertEquals(savedAmendoas, amendoas);
		Assertions.assertEquals(savedAmendoas.getStatus(), amendoas.getStatus());
		
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("store", "FNAC");
		Assertions.assertTrue(ordersService.getOrders(hashMap).containsAll(Arrays.asList(leite, amendoas)));
		Assertions.assertTrue(ordersService.getAllOrders().containsAll(Arrays.asList(leite, amendoas)));
		Assertions.assertEquals(ordersService.save(leite), leite);
		
		Mockito.verify(ordersRepository, VerificationModeFactory.times(3)).save(Mockito.any());
		Mockito.verify(ordersRepository, VerificationModeFactory.times(2)).save(leite);
		Mockito.verify(ordersRepository, VerificationModeFactory.times(1)).save(amendoas);
	}
	
}