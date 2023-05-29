package tqs.PickUs.ACPs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.PickUs.services.ACPsService;
import tqs.PickUs.controllers.ACPsRestController;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.ACPStatus;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

/* 
 * WebMvcTest loads a simplified web environment for the tests. Note that the normal
 * auto-discovery of beans (and dependency injection) is limited
 * This strategy deploys the required components to a test-friendly web framework, that can be accessed
 * by injecting a MockMvc reference
 */
@WebMvcTest(ACPsRestController.class)
public class ACPsRestControllerTests {
	@Autowired
	private MockMvc mvc; // entry point to the web framework

	// inject required beans as "mockeable" objects
	// note that @AutoWire would result in NoSuchBeanDefinitionException
	@MockBean
	private ACPsService acpsService;

	@BeforeEach
	public void setup() {
		// Return the argument
		Mockito.when(acpsService.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
	}

	@Test
	public void whenPostAcpThenCreateAcp() throws Exception {
		ACP acp = new ACP("Continente Glicinias", "Aveiro");

		mvc.perform(
				post("/api/v1/acps")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\": \"Continente Glicinias\", \"city\": \"Aveiro\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(acp.getName())))
				.andExpect(jsonPath("$.city", is(acp.getCity())))
				.andExpect(jsonPath("$.status", is("WAITING_ADMIN_APPROVAL")));

		Mockito.verify(acpsService, VerificationModeFactory.times(1)).save(Mockito.any());
		Mockito.verify(acpsService, VerificationModeFactory.times(1)).save(Mockito.any());

	}

	@Test
	public void givenAcpWhenGetAcpByIdThenReturnJson() throws Exception {
		ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
		continenteGlicinias.setId(1);
		Mockito.when(acpsService.getACPById(1)).thenReturn(continenteGlicinias);
		Mockito.when(acpsService.getACPByName(continenteGlicinias.getName())).thenReturn(continenteGlicinias);

		mvc.perform(
				get("/api/v1/acps/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(continenteGlicinias.getId())))
				.andExpect(jsonPath("$.name", is(continenteGlicinias.getName())))
				.andExpect(jsonPath("$.city", is(continenteGlicinias.getCity())));

		Mockito.verify(acpsService, VerificationModeFactory.times(1)).getACPById(Mockito.anyInt());
	}

	@Test
	public void givenAcpWhenGetAcpByNameThenReturnJson() throws Exception {
		ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
		continenteGlicinias.setId(1);
		Mockito.when(acpsService.getACPById(1)).thenReturn(continenteGlicinias);
		Mockito.when(acpsService.getACPByName(continenteGlicinias.getName())).thenReturn(continenteGlicinias);

		mvc.perform(
				get("/api/v1/acps/Continente Glicinias")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(continenteGlicinias.getId())))
				.andExpect(jsonPath("$.name", is(continenteGlicinias.getName())))
				.andExpect(jsonPath("$.city", is(continenteGlicinias.getCity())));

		Mockito.verify(acpsService, VerificationModeFactory.times(1)).getACPByName(Mockito.anyString());
	}

	@Test
	public void givenManyAcpsWhenGetAcpsThenReturnJsonArray() throws Exception {
		ACP continenteGlicinias = new ACP("Continente Glicinias", "Aveiro");
		continenteGlicinias.setId(1);
		ACP fnacAveiro = new ACP("Fnac Aveiro", "Aveiro");
		fnacAveiro.setId(2);
		ACP seasideSetubal = new ACP("Seaside Setubal", "Setubal");
		seasideSetubal.setId(3);
		List<ACP> acps = Arrays.asList(continenteGlicinias, fnacAveiro, seasideSetubal);

		Mockito.when(acpsService.getACPs(Mockito.any())).thenReturn(acps);

		mvc.perform(
				get("/api/v1/acps")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].name", is(continenteGlicinias.getName())))
				.andExpect(jsonPath("$[0].city", is(continenteGlicinias.getCity())))
				.andExpect(jsonPath("$[1].name", is(fnacAveiro.getName())))
				.andExpect(jsonPath("$[1].city", is(fnacAveiro.getCity())))
				.andExpect(jsonPath("$[2].name", is(seasideSetubal.getName())))
				.andExpect(jsonPath("$[2].city", is(seasideSetubal.getCity())));

		Mockito.verify(acpsService, VerificationModeFactory.times(1)).getACPs(Mockito.any());

	}

	@Test
	public void testUpdateAcpStatus() throws Exception {

		ACP acp = new ACP("Continente Glicinias", "Aveiro");
		acp.setId(1);

		Mockito.when(acpsService.getACPById(1)).thenReturn(acp);
		Mockito.when(acpsService.getACPByName(acp.getName())).thenReturn(acp);
		acp.setStatus(ACPStatus.APPROVED);
		Mockito.when(acpsService.updateACP(acp.getId(), ACPStatus.APPROVED)).thenReturn(acp);

		mvc.perform(
				post("/api/v1/acps/Continente Glicinias")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"status\": \"APPROVED\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(acp.getName())))
				.andExpect(jsonPath("$.city", is(acp.getCity())))
				.andExpect(jsonPath("$.status", is("APPROVED")))
				.andReturn();

		Mockito.when(acpsService.getACPById(1)).thenReturn(acp);
		Mockito.when(acpsService.getACPByName(acp.getName())).thenReturn(acp);
		acp.setStatus(ACPStatus.REFUSED);
		Mockito.when(acpsService.updateACP(acp.getId(), ACPStatus.REFUSED)).thenReturn(acp);

		mvc.perform(
				post("/api/v1/acps/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"status\": \"REFUSED\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(acp.getName())))
				.andExpect(jsonPath("$.city", is(acp.getCity())))
				.andExpect(jsonPath("$.status", is("REFUSED")));

		Mockito.verify(acpsService, VerificationModeFactory.times(2)).updateACP(Mockito.anyInt(), Mockito.any());

	}

}