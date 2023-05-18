package pt.ua.tqsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.entities.ACP;
import pt.ua.tqsproject.services.ACPService;

import java.util.List;
import java.util.Optional;

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


