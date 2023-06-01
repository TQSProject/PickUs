package tqs.PickUs.Orders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;

public class OrdersUnitTests {
	
	private Order createOrder1() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Leite";
		String buyer1 = "Afonso";
		
		return nullAllDates(new Order("FNAC", buyer1, product1, acp1));
	}
	
	private Order createOrder2() {
		ACP acp1 = new ACP("Continente", "Aveiro");
		String product1 = "Amendoas";
		String buyer1 = "Afonso";
		
		return nullAllDates(new Order("FNAC", buyer1, product1, acp1));
	}
	
	private Order nullAllDates(Order order) {
		order.setApprovedDateTime(null);
		order.setCreatedDateTime(null);
		order.setDeliveredDateTime(null);
		order.setEstimatedDeliveryDateTime(null);
		order.setPickedUpDateTime(null);
		return order;
	}
	
	@Test
	public void testEquals() {
		Order order1 = createOrder1();
		order1.setId(1);
		Order order2 = createOrder1();
		order2.setId(1);
		Order order3 = createOrder2();
		
		Assertions.assertEquals(order1, order2);
		Assertions.assertNotEquals(order1, order3);
	}
	
	@Test
	public void testHashCode()
	{
		Order order1 = createOrder1();
		order1.setId(1);
		Order order2 = createOrder1();
		order2.setId(1);
		Order order3 = createOrder2();

		Assertions.assertEquals(order1.hashCode(), order2.hashCode());
		Assertions.assertNotEquals(order1.hashCode(), order3.hashCode());
	}
}
