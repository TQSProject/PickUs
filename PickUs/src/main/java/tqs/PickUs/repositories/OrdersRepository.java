package tqs.PickUs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.*;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
	Order findById(int id);

}
