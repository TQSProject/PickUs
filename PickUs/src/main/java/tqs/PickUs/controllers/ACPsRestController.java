package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.services.ACPsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/acps")
public class ACPsRestController {
	@Autowired
	private ACPsService acpsService;
	
	@PostMapping
	public ResponseEntity<?> saveACP(@RequestBody ACP acp) {
		// Save the ACP using the ACPService
		ACP createdACP = acpsService.saveACP(acp);
		if (createdACP != null)
			return ResponseEntity.ok(createdACP);
		else 
			return ResponseEntity.badRequest().body("Invalid ACP");
	}
	
	public ResponseEntity<List<ACP>> getAllACPs() {
		List<ACP> acps = acpsService.getAllACPs();
		return ResponseEntity.ok(acps);
	}

	@GetMapping("{acpId}")
	public ResponseEntity<?> getACPById(@PathVariable int id)
	{
		ACP acp = acpsService.getACPById(id);
		if (acp != null)
			return ResponseEntity.ok(acp);
		else
			return ResponseEntity.badRequest().body("Invalid ACP id");
	}
}


