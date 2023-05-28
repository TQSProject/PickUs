package tqs.PickUs.ACPs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class ContainerAcpTest {

  @Container
  public static MySQLContainer container = new MySQLContainer("mysql:5.7")
      .withUsername("user1")
      .withPassword("user1pw")
      .withDatabaseName("PickUs");

  @Autowired
  private ACPsRepository acpsRepository;

  // requires Spring Boot >= 2.2.6
  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void contextLoads() {
    System.out.println("Context loads!");
  }

  @Test
  void insertThenReadACP() {
    ACP acp = new ACP("Glicinias Continente", "Aveiro");
    acp.setId(1);
    acpsRepository.save(acp);
    ACP retrieved = acpsRepository.findById(1);
    assertEquals(acp, retrieved);
  }
}
