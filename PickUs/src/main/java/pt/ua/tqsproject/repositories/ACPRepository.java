package pt.ua.tqsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqsproject.entities.ACP;

import java.util.List;

@Repository
public interface ACPRepository extends JpaRepository<ACP, Long> {
	//List<ACP> getACPsByName(String name);
	List<ACP> getACPSByCityContains(String city);
	List<ACP> getACPsByNameContains(String name);
}
