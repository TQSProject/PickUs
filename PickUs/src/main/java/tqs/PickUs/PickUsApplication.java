package tqs.PickUs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.services.ACPsService;
import tqs.PickUs.services.OrdersService;

@SpringBootApplication
public class PickUsApplication {
	@Autowired
	private ACPsService acpsService;

	@Autowired
	private OrdersService ordersService;

	public static void main(String[] args) {
		SpringApplication.run(PickUsApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadInitialData() {
		System.out.println("Loading initial data...");

		ACP acp1 = new ACP("Worten Lisboa", "Lisboa");
		acpsService.save(acp1);
		ACP acp2 = new ACP("Fnac Aveiro", "Aveiro");
		acpsService.save(acp2);
		ACP acp3 = new ACP("Continente #37", "Aveiro");
		acpsService.save(acp3);

		Order order1 = new Order();
		order1.setStore("eStore");
		order1.setBuyer("Ricardo");
		order1.setProduct("PS4");
		order1.setAcp(acp2);
		ordersService.save(order1);

		Order order2 = new Order();
		order2.setStore("eStore");
		order2.setBuyer("Ricardo");
		order2.setProduct("PS4");
		order2.setAcp(acp2);
		ordersService.save(order2);

		Order order3 = new Order();
		order3.setStore("eStore");
		order3.setBuyer("Daniel");
		order3.setProduct("Toothpaste X12");
		order3.setAcp(acp2);
		ordersService.save(order3);

		Order order4 = new Order();
		order4.setStore("eStore");
		order4.setBuyer("Alexandre");
		order4.setProduct("Blue pen");
		order4.setAcp(acp1);
		ordersService.save(order4);

		Order order5 = new Order();
		order5.setStore("eStore");
		order5.setBuyer("Alexandre");
		order5.setProduct("Iron rod");
		order5.setAcp(acp3);
		ordersService.save(order5);

	}
}
