package pt.ua.tqsproject.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACPRepository extends JpaRepository<ACP, Long> {
	// Additional custom query methods can be defined here if needed
}
