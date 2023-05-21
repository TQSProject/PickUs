package tqs.PickUs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.ACP;

@Repository
public interface ACPRepository extends JpaRepository<ACP, Long> {
}
