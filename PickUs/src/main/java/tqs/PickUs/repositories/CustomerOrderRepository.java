package tqs.PickUs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.PickUs.entities.CustomerOrder;
import tqs.PickUs.entities.Status;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findCustomerOrderByBuyerIdAndStatus(Long customerID, Status status);
}
