package tqs.PickUs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import tqs.PickUs.entities.ACP;
import tqs.PickUs.services.ACPsService;

@SpringBootApplication
public class PickUsApplication {
	@Autowired
	private ACPsService acpsService;

	public static void main(String[] args) {
		SpringApplication.run(PickUsApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("Loading initial data...");
		ACP acp = new ACP("Worten Lisboa", "Lisboa");
		ACP acp2 = new ACP("Fnac Aveiro", "Aveiro");
		acpsService.save(acp);
		acpsService.save(acp2);
	}
}
