package tqs.PickUs.Orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.PickUs.controllers.ACPsRestController;
import tqs.PickUs.controllers.OrdersRestController;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.services.ACPsService;
import tqs.PickUs.services.OrdersService;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersRestController.class)
public class OrdersRestControllerTests {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private OrdersService ordersService;
	
	private Order createOrder1() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Leite";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	@BeforeEach
	public void setup() {
		// Return the argument
		Mockito.when(ordersService.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
		Mockito.when(ordersService.createOrders(Mockito.any())).thenReturn(1);
	}
	
	@Test
	public void whenPostOrderThenCreateOrder() throws Exception {
		Order order = createOrder1();
		
		mvc.perform(
				post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"store\": \"FNAC\"," +
						" \"buyer\": \"Afonso\"," +
						" \"acp\": \"Continente Glicinias\"," +
						" \"product\": \"Leite\"," +
						" \"status\": \"WAITING_ADMIN_APPROVAL\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(1)));
		
		Mockito.verify(ordersService, VerificationModeFactory.times(1)).save(Mockito.any());
		Mockito.verify(ordersService, VerificationModeFactory.times(1)).save(Mockito.any());
	}
	
	@Test
	public void givenOrderWhenGetOrderByIdThenReturnJson() throws Exception {
		Order order = createOrder1();
		order.setId(1);
		Mockito.when(ordersService.getOrderById(1)).thenReturn(order);
		
		mvc.perform(
						get("/api/v1/orders/1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(order.getId())))
				.andExpect(jsonPath("$.buyer", is(order.getBuyer())))
				.andExpect(jsonPath("$.store", is(order.getStore())))
				.andExpect(jsonPath("$.acp.id", is(order.getAcp().getId())))
				.andExpect(jsonPath("$.acp.name", is(order.getAcp().getName())))
				.andExpect(jsonPath("$.acp.city", is(order.getAcp().getCity())))
				.andExpect(jsonPath("$.acp.status", is(order.getAcp().getStatus().toString())))
				.andExpect(jsonPath("$.status", is(order.getStatus().toString())));
		
		Mockito.verify(ordersService, VerificationModeFactory.times(1)).getOrderById(Mockito.anyInt());
	}
	
	public static String asJsonString(Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
