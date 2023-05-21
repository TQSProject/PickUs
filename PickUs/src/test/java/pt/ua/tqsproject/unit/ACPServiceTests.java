package pt.ua.tqsproject.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.tqsproject.entities.ACP;
import pt.ua.tqsproject.repositories.ACPRepository;
import pt.ua.tqsproject.services.ACPService;

@ExtendWith(MockitoExtension.class)
public class ACPServiceTests {
	@InjectMocks
	ACPService acpService;
	
	@Mock
	ACPRepository acpRepository;
	
	@Test
	public void testSaveACP() {
		ACP acp = new ACP("aaa", "bbb");
		
		acpService.saveACP(acp);
		
		verify(acpRepository, times(1)).save(acp);
	}
}
