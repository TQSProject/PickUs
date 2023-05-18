package pt.ua.tqsproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqsproject.data.ACP;
import pt.ua.tqsproject.service.ACPService;

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
	
	@GetMapping("")
	public ResponseEntity<List<ACP>> getAllACPs() {
		List<ACP> acps;
		acps = acpService.getAllACPs();
		return ResponseEntity.ok(acps);
	}
	
}


