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
import pt.ua.tqsproject.controllers.ACPController;
import pt.ua.tqsproject.entities.ACP;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ACPIntegrationTests {
	@Autowired
	ACPController acpController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Test
	public void testCreate() {
		ACP acp = new ACP("aaa", "bbb");
		
		String url = "http://localhost:" + port + "/api/v1/acps";
		ResponseEntity<ACP> entity = restTemplate.postForEntity(url, acp, ACP.class);
		
		ACP acpResult = acpController.saveACP(acp).getBody();
		
		Assertions.assertThat(acp).isEqualTo(acpResult);
	}
}
