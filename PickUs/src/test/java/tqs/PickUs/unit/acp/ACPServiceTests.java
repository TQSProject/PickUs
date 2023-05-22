package tqs.PickUs.unit.acp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPRepository;
import tqs.PickUs.services.ACPService;

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
	
	@Test
	public void testGetAllACPs() {
		acpService.getAllACPs();
		
		verify(acpRepository, times(1)).findAll();
	}
}
