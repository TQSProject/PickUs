package tqs.PickUs.Orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tqs.PickUs.entities.Order;
import tqs.PickUs.repositories.ACPsRepository;
import tqs.PickUs.repositories.OrdersRepository;
import tqs.PickUs.services.ACPsService;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.ACPStatus;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test scenario: verify the logic of the Service, mocking the response of the
 * datasource
 * Results in standard unit test with mocks
 */
@ExtendWith(MockitoExtension.class)
public class OrdersServiceTests {
	@Mock(lenient = true)
	private OrdersRepository ordersRepository;
	
	@InjectMocks
	private ACPsService acpsService;
	
	private Order leite, amendoas;
	private List<Order> orders;
	
	private Order createOrder1() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Leite";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	private Order createOrder2() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Amendoas";
		String buyer1 = "Afonso";
		
		return new Order("FNAC", buyer1, product1, acp1);
	}
	
	@BeforeEach
	public void setup() {
		leite = createOrder1();
		amendoas = createOrder2();
		orders = List.of(leite, amendoas);
		
		updateMockedRepo();
	}
	
	public void updateMockedRepo() {
		Mockito.when(ordersRepository.findAll()).thenReturn(orders);
		
		Mockito.when(ordersRepository.findById(leite.getId())).thenReturn(leite);
		
		Mockito.when(ordersRepository.findById(amendoas.getId())).thenReturn(amendoas);
	}
	
	@Test
	public void testGetACPsWithFilters() {
		// params: name, city, status
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("city", "Aveiro");
		List<ACP> acpsAveiro = acpsService.getACPs(params);
		Assertions.assertEquals(acpsAveiro.size(), 2);
		Assertions.assertTrue(acpsAveiro.containsAll(Arrays.asList(continenteGlicinias, fnacAveiro)));
		
		params = new HashMap<String, String>();
		params.put("name", "Seaside");
		List<ACP> acpsSeaside = acpsService.getACPs(params);
		Assertions.assertEquals(acpsSeaside.size(), 2);
		Assertions.assertTrue(acpsSeaside.containsAll(Arrays.asList(seasideSetubal, seasidePorto)));
		
		fnacAveiro.setStatus(ACPStatus.APPROVED);
		seasideSetubal.setStatus(ACPStatus.APPROVED);
		updateMockedRepo();
		
		params = new HashMap<String, String>();
		params.put("status", "APPROVED");
		List<ACP> acpsApproved = acpsService.getACPs(params);
		Assertions.assertEquals(acpsApproved.size(), 2);
		Assertions.assertTrue(acpsApproved.containsAll(Arrays.asList(fnacAveiro, seasideSetubal)));
		
		params = new HashMap<String, String>();
		params.put("name", "Seaside");
		params.put("city", "Porto");
		List<ACP> acpsSeasidePorto = acpsService.getACPs(params);
		Assertions.assertEquals(acpsSeasidePorto.size(), 1);
		Assertions.assertTrue(acpsSeasidePorto.contains(seasidePorto));
		
		params = new HashMap<String, String>();
		params.put("name", "Seaside");
		params.put("city", "Porto");
		params.put("status", "APPROVED");
		List<ACP> acpsSeasidePortoApproved = acpsService.getACPs(params);
		Assertions.assertEquals(acpsSeasidePortoApproved.size(), 0);
		
	}
	
	@Test
	public void testUpdateAcpStatus() {
		// Return the argument
		Mockito.when(ordersRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
		
		for (ACP acp : acps)
			Assertions.assertEquals(acp.getStatus(), ACPStatus.WAITING_ADMIN_APPROVAL);
		
		ACP savedFnacAveiro = acpsService.updateACP(fnacAveiro.getId(), ACPStatus.APPROVED);
		ACP savedSeasideSetubal = acpsService.updateACP(seasideSetubal.getId(), ACPStatus.REFUSED);
		
		fnacAveiro.setStatus(ACPStatus.APPROVED);
		seasideSetubal.setStatus(ACPStatus.REFUSED);
		updateMockedRepo();
		
		Assertions.assertEquals(acpsService.getACPById(continenteGlicinias.getId()).getStatus(),
				ACPStatus.WAITING_ADMIN_APPROVAL);
		Assertions.assertNotNull(savedSeasideSetubal);
		Assertions.assertNotNull(fnacAveiro);
		Assertions.assertEquals(savedFnacAveiro, fnacAveiro);
		Assertions.assertEquals(savedFnacAveiro.getStatus(), fnacAveiro.getStatus());
		Assertions.assertEquals(savedSeasideSetubal, seasideSetubal);
		Assertions.assertEquals(savedSeasideSetubal.getStatus(), seasideSetubal.getStatus());
		
		Mockito.verify(ordersRepository, VerificationModeFactory.times(2)).save(Mockito.any());
		Mockito.verify(ordersRepository, VerificationModeFactory.times(1)).save(fnacAveiro);
		Mockito.verify(ordersRepository, VerificationModeFactory.times(1)).save(seasideSetubal);
	}
	
}