package pt.ua.tqsproject.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ua.tqsproject.controllers.ACPController;
import pt.ua.tqsproject.entities.ACP;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ACPIntegrationTests {
	@Autowired
	ACPController acpController;
	@Test
	public void testCreate() {
		ACP acp = new ACP("aaa", "bbb");
		ACP acpResult = acpController.saveACP(acp).getBody();
		
		Assertions.assertThat(acp).isEqualTo(acpResult);
	}
}
