package tqs.PickUs.unit.acp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tqs.PickUs.controllers.ACPController;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.services.ACPService;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ACPControllerTests {
	
	@InjectMocks
	ACPController acpController;
	
	@Mock
	ACPService acpService;
	
	@Test
	public void testSaveACP() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(acpService.saveACP(any(ACP.class))).then(returnsFirstArg());
		
		ACP acp = new ACP("abc", "def");
		ResponseEntity<ACP> responseEntity = acpController.saveACP(acp);
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(acp);
	}
}