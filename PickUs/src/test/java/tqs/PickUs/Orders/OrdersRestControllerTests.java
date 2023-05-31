package tqs.PickUs.Orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.PickUs.controllers.ACPsRestController;
import tqs.PickUs.controllers.OrdersRestController;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.OrderStatus;
import tqs.PickUs.services.ACPsService;
import tqs.PickUs.services.OrdersService;

import java.util.HashMap;
import java.util.List;

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
		Mockito.when(ordersService.createOrders(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
	}
	
	@Test
	public void whenPostOrderThenCreateOrder() throws Exception {
		Order order = createOrder1();
		
		mvc.perform(
				post("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"store\": \"FNAC\"," +
						" \"buyer\": \"Afonso\"," +
						" \"acp\": \"Continente\"," +
						" \"product\": \"Leite\"," +
						" \"status\": \"WAITING_ADMIN_APPROVAL\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(OrderStatus.WAITING_ADMIN_APPROVAL.toString())))
				.andExpect(jsonPath("$.store", is(order.getStore())))
				.andExpect(jsonPath("$.buyer", is(order.getBuyer())))
				.andExpect(jsonPath("$.acp", is(order.getAcp().getName())));
	}
	
	@Test
	public void whenGetOrderByIdThenReturnJson() throws Exception {
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
	
	@Test
	public void whenGetOrdersThenReturnJson() throws Exception {
		Order order = createOrder1();
		order.setId(1);
		HashMap<String, String> params = new HashMap<>();
		params.put("store", null);
		params.put("buyer", null);
		params.put("acp", null);
		params.put("product", null);
		params.put("status", null);
		Mockito.when(ordersService.getOrders(params)).thenReturn(List.of(order));
		
		mvc.perform(
				get("/api/v1/orders")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(order.getId())))
				.andExpect(jsonPath("$[0].buyer", is(order.getBuyer())))
				.andExpect(jsonPath("$[0].store", is(order.getStore())))
				.andExpect(jsonPath("$[0].acp.id", is(order.getAcp().getId())))
				.andExpect(jsonPath("$[0].acp.name", is(order.getAcp().getName())))
				.andExpect(jsonPath("$[0].acp.city", is(order.getAcp().getCity())))
				.andExpect(jsonPath("$[0].acp.status", is(order.getAcp().getStatus().toString())))
				.andExpect(jsonPath("$[0].status", is(order.getStatus().toString())));
		
		Mockito.verify(ordersService, VerificationModeFactory.times(1)).getOrders(Mockito.any());
	}
	
	@Test
	public void whenUpdateOrdersThenReturnJson() throws Exception {
		Order order = createOrder1();
		order.setId(1);
		HashMap<String, String> params = new HashMap<>();
		params.put("store", null);
		params.put("buyer", null);
		params.put("acp", null);
		params.put("product", null);
		params.put("status", null);
		
		Mockito.when(ordersService.getOrders(params)).thenReturn(List.of(order));
		Mockito.when(ordersService.getOrderById(order.getId())).thenReturn(order);
		Mockito.when(ordersService.updateOrder(Mockito.eq(order.getId()), Mockito.any(OrderStatus.class))).then(invocation -> {
			Order order1 = ordersService.getOrderById(invocation.getArgument(0, Integer.class));
			OrderStatus status = invocation.getArgument(1, OrderStatus.class);
			order1.setStatus(status);
			return order1;
		});
		
		
		mvc.perform(
				post("/api/v1/orders/1")
				.content("{\"status\": \"DELIVERING\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(order.getId())))
				.andExpect(jsonPath("$.buyer", is(order.getBuyer())))
				.andExpect(jsonPath("$.store", is(order.getStore())))
				.andExpect(jsonPath("$.acp.id", is(order.getAcp().getId())))
				.andExpect(jsonPath("$.acp.name", is(order.getAcp().getName())))
				.andExpect(jsonPath("$.acp.city", is(order.getAcp().getCity())))
				.andExpect(jsonPath("$.acp.status", is(order.getAcp().getStatus().toString())))
				.andExpect(jsonPath("$.status", is(OrderStatus.DELIVERING.toString())));
		
		Mockito.verify(ordersService, VerificationModeFactory.times(2)).getOrderById(Mockito.anyInt());
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
