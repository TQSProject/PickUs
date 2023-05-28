package tqs.PickUs.ACPs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import io.restassured.RestAssured;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

@Testcontainers
@SpringBootTest
class ACPsRestControllerIT {

    @Container
    public static MySQLContainer container = new MySQLContainer("mysql:5.7")
            .withUsername("user1")
            .withPassword("user1pw")
            .withDatabaseName("PickUs");

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ACPsRepository acpsRepository;

    @BeforeEach
    public void resetDb() {
        acpsRepository.deleteAll();
    }

    @Test
    void postAcpThenGetAcp() throws Exception {
        ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
        continenteGlicinias.setId(1);

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "acps")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .body("\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"")
                .when().post(endpoint)
                .then().statusCode(200);

        endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "acps", "1")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .get(endpoint)
                .then().statusCode(200)
                .body("$.name", is("Continente Glicinias"))
                .body("$.city", is("Aveiro"));

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
                .body("$.name", is("Continente Glicinias"))
                .body("$.city", is("Aveiro"));
    }

    @Test
    void postACPsThenGetACPs() throws Exception {
        ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
        continenteGlicinias.setId(1);
        ACP seasideSetubal = new ACP("Seaside Setubal", "Setubal");
        seasideSetubal.setId(2);

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "acps")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .body("\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"")
                .when().post(endpoint)
                .then().statusCode(200);

        RestAssured.given().auth().none().contentType("application/json")
                .body("\"name\": \"Seaside Setubal\", \"city\": \"Setubal\"")
                .when().post(endpoint)
                .then().statusCode(200);

        List<ACP> acps = acpsRepository.findAll();
        assertEquals(acps.get(0), continenteGlicinias);
        assertEquals(acps.get(1), seasideSetubal);

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
        ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
        continenteGlicinias.setId(1);

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "acps")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .body("\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"")
                .when().post(endpoint)
                .then().statusCode(200);

        endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "acps", "1")
                .build()
                .toUriString();

        RestAssured.given().auth().none().contentType("application/json")
                .get(endpoint)
                .then().statusCode(200)
                .body("$.name", is("Continente Glicinias"))
                .body("$.city", is("Aveiro"))
                .body("$.status", is("WAITING_ADMIN_APPROVAL"));

        RestAssured.given().auth().none().contentType("application/json")
                .body("\"status\": \"REFUSED\"")
                .when().post(endpoint)
                .then().statusCode(200);

        RestAssured.given().auth().none().contentType("application/json")
                .get(endpoint)
                .then().statusCode(200)
                .body("$.name", is("Continente Glicinias"))
                .body("$.city", is("Aveiro"))
                .body("$.status", is("REFUSED"));
    }

}
