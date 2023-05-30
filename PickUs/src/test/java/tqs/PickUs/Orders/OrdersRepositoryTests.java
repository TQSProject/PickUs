package tqs.PickUs.Orders;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.entities.OrderStatus;
import tqs.PickUs.repositories.OrdersRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrdersRepositoryTests {
    
    @Autowired
    OrdersRepository ordersRepository;
    
    @AfterEach
    @BeforeEach
    public void clearDatabase() {
        ordersRepository.deleteAll();
    }
    
    private Order createOrder1() {
        ACP acp1 = new ACP("Continente", "Aveiro");
        String product1 = "Leite";
        String buyer1 = "Afonso";
    
        return new Order("FNAC", buyer1, product1, acp1);
    }
    
    @Test
    public void testCreateSearchReadDeleteOrder() {
        Order order1 = createOrder1();
        ordersRepository.save(order1);
        
        Iterable<Order> orders = ordersRepository.findAll();
        Assertions.assertThat(orders).isNotEmpty();
        assertThat(orders)
                .extracting(Order::getProduct)
                .anyMatch(product -> product.equals(order1.getProduct()));
        
        assertThat(orders).extracting(Order::getBuyer).containsOnly(order1.getBuyer());
        assertThat(orders).extracting(Order::getAcp).containsOnly(order1.getAcp());
        assertThat(orders).extracting(Order::getStatus).containsOnly(OrderStatus.WAITING_ADMIN_APPROVAL);
        
        Order order2 = ordersRepository.findById(order1.getId());
        assertThat(order2).isEqualTo(order1);
        
        ordersRepository.deleteAll();
        Assertions.assertThat(ordersRepository.findAll()).isEmpty();
    }
}