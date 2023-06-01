package tqs.PickUs.ACPs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;
import tqs.PickUs.repositories.OrdersRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class ACPsRestControllerIT {

	// will need to use the server port for the invocation url
	@LocalServerPort
	int randomServerPort;

	@Autowired
	private ACPsRepository acpsRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	@AfterAll
	@BeforeEach
	public void resetDb() {
		ordersRepository.deleteAll();
		acpsRepository.deleteAll();
	}

	@Test
	void postAcpThenGetAcp() {
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"ContinenteGlicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", "ContinenteGlicinias")
				.build()
				.toUriString();

		ValidatableResponse response = RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("ContinenteGlicinias"))
				.body("city", is("Aveiro"));

		int id = response.extract().path("id");

		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", Integer.toString(id))
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("ContinenteGlicinias"))
				.body("city", is("Aveiro"));
	}

	@Test
	void postACPsThenGetACPs() {
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"ContinenteGlicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"Seaside Setubal\", \"city\": \"Setubal\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		List<ACP> acps = acpsRepository.findAll();
		boolean hasElementWithNameContinenteGlicinias = acps.stream()
				.anyMatch(acp -> acp.getName().equals("ContinenteGlicinias"));
		boolean hasElementWithNameSeasideSetubal = acps.stream()
				.anyMatch(acp -> acp.getName().equals("Seaside Setubal"));

		assertTrue(hasElementWithNameContinenteGlicinias);
		assertTrue(hasElementWithNameSeasideSetubal);

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("size()", is(2))
				.body("[0].name", is("ContinenteGlicinias"))
				.body("[0].city", is("Aveiro"))
				.body("[1].name", is("Seaside Setubal"))
				.body("[1].city", is("Setubal"));

	}

	@Test
	void testUpdateAcpStatus() {
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"ContinenteGlicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", "ContinenteGlicinias")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("ContinenteGlicinias"))
				.body("city", is("Aveiro"))
				.body("status", is("WAITING_ADMIN_APPROVAL"));

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"status\": \"REFUSED\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("ContinenteGlicinias"))
				.body("city", is("Aveiro"))
				.body("status", is("REFUSED"));
	}

	@Test
	public void testAcpOrders() {
		postACPs();

		// Assert 0 orders for certain ACP
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", "ContinenteGlicinias", "orders")
				.build()
				.toUriString();
		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("size()", is(0));

		postOrders("ContinenteGlicinias");
		postOrders("FnacAveiro");

		// Assert orders for certain ACP
		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("size()", is(4))
				.body("acp.name", everyItem(is("ContinenteGlicinias")))
				.body("[2].buyer", is("Ricardo"))
				.body("[2].product", is("Samsung Galaxy S10"))
				.body("[2].count", is(2));

	}

	private void postACPs() {
		// Post ACPs
		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"ContinenteGlicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"FnacAveiro\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);
	}

	private void postOrders(String acp) {

		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "orders")
				.build()
				.toUriString();

		TreeMap<String, Object> data = new TreeMap<String, Object>();
		data.put("store", "eStore");
		data.put("buyer", "Ricardo");
		ArrayList<HashMap<String, Object>> products = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> product1 = new HashMap<String, Object>();
		HashMap<String, Object> product2 = new HashMap<String, Object>();
		HashMap<String, Object> product3 = new HashMap<String, Object>();
		product1.put("name", "Toothpaste Nax Pro");
		product1.put("count", 3);
		product2.put("name", "PS4");
		product3.put("name", "Samsung Galaxy S10");
		product3.put("count", 2);
		products.add(product1);
		products.add(product2);
		products.add(product3);
		data.put("products", products);
		data.put("acp", acp);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(data)
				.when()
				.post(endpoint)
				.then()
				.statusCode(200); // Assuming a successful response has HTTP status code 200

		data = new TreeMap<String, Object>();
		data.put("store", "eStore");
		data.put("buyer", "Daniel");
		data.put("product", "Red apple");
		data.put("acp", acp);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(data)
				.when()
				.post(endpoint)
				.then()
				.statusCode(200); // Assuming a successful response has HTTP status code 200
	}

}
