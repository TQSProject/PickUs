package tqs.PickUs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.ACP;

@Repository
public interface ACPsRepository extends JpaRepository<ACP, Integer> {
    public ACP findById(int id);

    public ACP findByName(String name);

    public ACP findByCity(String city);

    public ACP findByNameAndCity(String name, String city);
}
