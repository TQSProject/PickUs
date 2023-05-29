package tqs.PickUs.ACPs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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

	@BeforeEach
	@AfterAll
	public void resetDb() {
		acpsRepository.deleteAll();
	}

	@Test
	void postAcpThenGetAcp() throws Exception {
		ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");

		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", "Continente Glicinias")
				.build()
				.toUriString();

		ValidatableResponse response = RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("Continente Glicinias"))
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
				.body("name", is("Continente Glicinias"))
				.body("city", is("Aveiro"));
	}

	@Test
	void postACPsThenGetACPs() throws Exception {
		ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
		ACP seasideSetubal = new ACP("Seaside Setubal", "Setubal");

		String endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"name\": \"Seaside Setubal\", \"city\": \"Setubal\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		List<ACP> acps = acpsRepository.findAll();
		boolean hasElementWithNameContinenteGlicinias = acps.stream()
				.anyMatch(acp -> acp.getName().equals("Continente Glicinias"));
		boolean hasElementWithNameSeasideSetubal = acps.stream()
				.anyMatch(acp -> acp.getName().equals("Seaside Setubal"));

		assertTrue(hasElementWithNameContinenteGlicinias);
		assertTrue(hasElementWithNameSeasideSetubal);

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("size()", is(2))
				.body("[0].name", is("Continente Glicinias"))
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
				.body("{\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		endpoint = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("127.0.0.1")
				.port(randomServerPort)
				.pathSegment("api", "v1", "acps", "Continente Glicinias")
				.build()
				.toUriString();

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("Continente Glicinias"))
				.body("city", is("Aveiro"))
				.body("status", is("WAITING_ADMIN_APPROVAL"));

		RestAssured.given().auth().none().contentType("application/json")
				.body("{\"status\": \"REFUSED\"}")
				.when().post(endpoint)
				.then().statusCode(200);

		RestAssured.given().auth().none().contentType("application/json")
				.get(endpoint)
				.then().statusCode(200)
				.body("name", is("Continente Glicinias"))
				.body("city", is("Aveiro"))
				.body("status", is("REFUSED"));
	}

}
