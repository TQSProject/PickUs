package tqs.PickUs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.Status;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
	//List<Order> findCustomerOrderByBuyerIdAndStatus(Long customerID, Status status);
}
