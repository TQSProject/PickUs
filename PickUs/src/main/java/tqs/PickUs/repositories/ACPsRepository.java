package tqs.PickUs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.ACP;

@Repository
public interface ACPsRepository extends JpaRepository<ACP, Integer> {
    public ACP findById(int id);

    public ACP findByName(String name);

    public List<ACP> findByCity(String city);

}
