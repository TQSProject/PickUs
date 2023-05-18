package pt.ua.tqsproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.ua.tqsproject.entities.ACP;
import pt.ua.tqsproject.repositories.ACPRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ACPRepositoryTests {
	
	@Autowired
	ACPRepository acpRepository;
	
	@Test
	public void testCreateReadDelete() {
		ACP acp = new ACP("Continente", "Viseu");
		
		acpRepository.save(acp);
		
		Iterable<ACP> acps = acpRepository.findAll();
		Assertions.assertThat(acpRepository.findAll()).isNotEmpty();
		Assertions.assertThat(acps).extracting(ACP::getName).containsOnly("Continente");
		Assertions.assertThat(acps).extracting(ACP::getCity).containsOnly("Viseu");
		
		acpRepository.deleteAll();
		Assertions.assertThat(acpRepository.findAll()).isEmpty();
	}

}
