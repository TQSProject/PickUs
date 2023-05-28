package tqs.PickUs.ACPs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tqs.PickUs.repositories.ACPsRepository;
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
public class ACPsServiceTests {
    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock(lenient = true)
    private ACPsRepository acpsRepository;

    @InjectMocks
    private ACPsService acpsService;

    private ACP continenteGlicinias, fnacAveiro, seasideSetubal, seasidePorto;
    private List<ACP> acps;

    @BeforeEach
    public void setup() {
        continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
        continenteGlicinias.setId(1);
        fnacAveiro = new ACP("Fnac Aveiro", "Aveiro");
        fnacAveiro.setId(2);
        seasideSetubal = new ACP("Seaside Setubal", "Setubal");
        seasideSetubal.setId(3);
        seasidePorto = new ACP("Seaside Porto", "Porto");
        seasidePorto.setId(4);

        updateMockedRepo();
    }

    public void updateMockedRepo() {
        acps = Arrays.asList(continenteGlicinias, fnacAveiro, seasideSetubal, seasidePorto);
        Mockito.when(acpsRepository.findAll()).thenReturn(acps);
        
        Mockito.when(acpsRepository.findByCity("Aveiro")).thenReturn(Arrays.asList(continenteGlicinias, fnacAveiro));
        Mockito.when(acpsRepository.findByCity("Porto")).thenReturn(Arrays.asList(seasidePorto));
        Mockito.when(acpsRepository.findByCity("Setubal")).thenReturn(Arrays.asList(seasideSetubal));

        Mockito.when(acpsRepository.findById(continenteGlicinias.getId())).thenReturn(continenteGlicinias);
        Mockito.when(acpsRepository.findByName(continenteGlicinias.getName())).thenReturn(continenteGlicinias);

        Mockito.when(acpsRepository.findById(fnacAveiro.getId())).thenReturn(fnacAveiro);
        Mockito.when(acpsRepository.findByName(fnacAveiro.getName())).thenReturn(fnacAveiro);

        Mockito.when(acpsRepository.findById(seasideSetubal.getId())).thenReturn(seasideSetubal);
        Mockito.when(acpsRepository.findByName(seasideSetubal.getName())).thenReturn(seasideSetubal);

        Mockito.when(acpsRepository.findById(seasidePorto.getId())).thenReturn(seasidePorto);
        Mockito.when(acpsRepository.findByName(seasidePorto.getName())).thenReturn(seasidePorto);
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
        for (ACP acp : acps)
            Assertions.assertEquals(acp.getStatus(), ACPStatus.WAITING_ADMIN_APPROVAL);

        ACP savedFnacAveiro = acpsService.updateACP(fnacAveiro.getId(), ACPStatus.APPROVED);
        ACP savedSeasideSetubal = acpsService.updateACP(seasideSetubal.getId(), ACPStatus.REFUSED);

        fnacAveiro.setStatus(ACPStatus.APPROVED);
        seasideSetubal.setStatus(ACPStatus.REFUSED);
        updateMockedRepo();

        Assertions.assertEquals(acpsService.getACPById(continenteGlicinias.getId()).getStatus(),
                ACPStatus.WAITING_ADMIN_APPROVAL);
        Assertions.assertEquals(savedFnacAveiro, fnacAveiro);
        Assertions.assertEquals(savedFnacAveiro.getStatus(), fnacAveiro.getStatus());
        Assertions.assertEquals(savedSeasideSetubal, seasideSetubal);
        Assertions.assertEquals(savedSeasideSetubal.getStatus(), seasideSetubal.getStatus());

        Mockito.verify(acpsRepository, VerificationModeFactory.times(2)).save(Mockito.any());
        Mockito.verify(acpsRepository, VerificationModeFactory.times(1)).save(fnacAveiro);
        Mockito.verify(acpsRepository, VerificationModeFactory.times(1)).save(seasideSetubal);
    }

}