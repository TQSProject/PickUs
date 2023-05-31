package tqs.PickUs.Orders;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.repositories.OrdersRepository;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrdersRestControllerIT {
	
	// will need to use the server port for the invocation url
	@LocalServerPort
	int randomServerPort;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@BeforeEach
	@AfterAll
	public void resetDb() {
		ordersRepository.deleteAll();
	}
	
	private Order createOrder1() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Leite";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	@Test
	void postOrderThenGetOrder() {
		Order order1 = createOrder1();
		
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();
		
		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"Continente\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);
		
		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "orders")
				.build()
				.toUriString();
		
		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"store\": \"FNAC\"," +
						" \"buyer\": \"Afonso\"," +
						" \"acp\": \"Continente\"," +
						" \"product\": \"Leite\"," +
						" \"status\": \"WAITING_ADMIN_APPROVAL\"}")
				.when().post(endpoint)
				.then().statusCode(200);
	}
}
