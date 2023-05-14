package pt.ua.tqsproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.data.ACP;
import pt.ua.tqsproject.service.ACPService;

import java.util.List;

@RestController
@RequestMapping("/acps")
public class ACPController {
	private final ACPService acpService;
	
	@Autowired
	public ACPController(ACPService acpService) {
		this.acpService = acpService;
	}
	
	@PostMapping
	public ResponseEntity<ACP> createACP(@RequestBody ACP acp) {
		// Save the ACP using the ACPService
		ACP createdACP = acpService.createACP(acp);
		return ResponseEntity.ok(createdACP);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ACP> getACPById(@PathVariable Long id) {
		// Retrieve the ACP by ID using the ACPService
		ACP acp = acpService.getACPById(id);
		if (acp != null) {
			return ResponseEntity.ok(acp);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<ACP>> getAllACPs() {
		// Retrieve all ACPs using the ACPService
		List<ACP> acps = acpService.getAllACPs();
		return ResponseEntity.ok(acps);
	}
}


