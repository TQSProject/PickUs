package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.services.ACPService;

@RestController
@RequestMapping("/api/v1/acps")
public class ACPController {
	private final ACPService acpService;
	
	@Autowired
	public ACPController(ACPService acpService) {
		this.acpService = acpService;
	}
	
	@PostMapping
	public ResponseEntity<ACP> saveACP(@RequestBody ACP acp) {
		// Save the ACP using the ACPService
		ACP createdACP = acpService.saveACP(acp);
		return ResponseEntity.ok(createdACP);
	}
}


