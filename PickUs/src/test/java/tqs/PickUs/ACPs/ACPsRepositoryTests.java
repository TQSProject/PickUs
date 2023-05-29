package tqs.PickUs.ACPs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Assertions;
import tqs.PickUs.repositories.ACPsRepository;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.ACPStatus;
import java.util.List;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

/**
 * DataJpaTest limits the test scope to the data access context (no web
 * environment loaded, for example)
 * tries to autoconfigure the database, if possible (e.g.: in memory db)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ACPsRepositoryTests {
    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ACPsRepository acpsRepository;

    private ACP continenteGlicinias, fnacAveiro, seasideSetubal;

    @BeforeEach
    public void setup() {
        // Clear database
        entityManager.flush();
        entityManager.clear();

        continenteGlicinias = new ACP("Continente Glicias", "Aveiro");
        entityManager.persistAndFlush(continenteGlicinias); // ensure data is persisted at this point

        fnacAveiro = new ACP("Fnac Aveiro", "Aveiro");
        entityManager.persistAndFlush(fnacAveiro); // ensure data is persisted at this point

        seasideSetubal = new ACP("Seaside Setubal", "Setubal");
        entityManager.persistAndFlush(seasideSetubal); // ensure data is persisted at this point
    }

    @Test
    public void testFindByName() {
        ACP found = acpsRepository.findByName(continenteGlicinias.getName());
        Assertions.assertEquals(found, continenteGlicinias);
    }

    @Test
    public void testFindByCity() {
        List<ACP> found = acpsRepository.findByCity("Aveiro");
        Assertions.assertEquals(found.size(), 2);
        Assertions.assertTrue(found.contains(continenteGlicinias));
        Assertions.assertTrue(found.contains(fnacAveiro));
    }

    @Test
    public void testDefaultAcpStatus() {
        List<ACP> acps = acpsRepository.findAll();
        Assertions.assertEquals(acps.size(), 3);
        for (ACP acp : acps)
            Assertions.assertEquals(acp.getStatus(), ACPStatus.WAITING_ADMIN_APPROVAL);
    }

}