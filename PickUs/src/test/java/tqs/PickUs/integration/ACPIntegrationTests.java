package tqs.PickUs.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tqs.PickUs.controllers.ACPController;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ACPIntegrationTests {
	@Autowired
	ACPController acpController;
	
	@Autowired
	ACPRepository acpRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@AfterEach
	public void clearDatabase() {
		acpRepository.deleteAll();
		acpRepository.flush();
	}
	
	@Test
	public void testCreateAndSearch() {
		ACP acp1 = new ACP("aaa", "bbb");
		ACP acp2 = new ACP("ccc", "ddd");
		
		String url = "http://localhost:" + port + "/api/v1/acps";
		ResponseEntity<ACP> entity = restTemplate.postForEntity(url, acp1, ACP.class);
		entity = restTemplate.postForEntity(url, acp2, ACP.class);
		
		ResponseEntity<List<ACP>> responseEntity = acpController.getAllACPs();
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").containsOnly(acp1, acp2);
	}
	
	@Test
	public void testErrorHandlingReturnsBadRequest() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:" + port;
		
		try {
			restTemplate.getForEntity(url + "/wrong", String.class);
		} catch (HttpClientErrorException e) {
			Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}
		
		try {
			restTemplate.getForEntity(url + "/api/v1/acps", String.class);
		} catch (HttpClientErrorException e) {
			Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}
	}
}
