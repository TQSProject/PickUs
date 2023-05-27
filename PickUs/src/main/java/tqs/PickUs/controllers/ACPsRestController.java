package tqs.PickUs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.services.ACPsService;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/acps")
public class ACPsRestController {
	@Autowired
	private ACPsService acpsService;

	public ResponseEntity<List<ACP>> getACPs(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "status", required = false) String status) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("city", city);
		params.put("status", status);

		List<ACP> acps = acpsService.getACPs(params);
		return ResponseEntity.ok(acps);
	}

	@GetMapping("{acpId}")
	public ResponseEntity<?> getACPById(@PathVariable int acpId) {
		ACP acp = acpsService.getACPById(acpId);
		if (acp != null)
			return ResponseEntity.ok(acp);
		else
			return ResponseEntity.badRequest().body("Invalid ACP id");
	}

	@GetMapping("{acpName}")
	public ResponseEntity<?> getACPByName(@PathVariable String acpName) {
		ACP acp = acpsService.getACPByName(acpName);
		if (acp != null)
			return ResponseEntity.ok(acp);
		else
			return ResponseEntity.badRequest().body("Invalid ACP name");
	}

	@GetMapping("{acpId}/orders")
	public ResponseEntity<?> getACPOrders(@PathVariable int acpId) {
		List<Order> orders = acpsService.getACPOrders(acpId);
		if (orders == null)
			return ResponseEntity.badRequest().body("Invalid ACP id");
		return ResponseEntity.ok(orders);

	}

	@PostMapping
	public ResponseEntity<?> saveACP(@RequestBody ACP acp) {
		// Save the ACP using the ACPService
		ACP createdACP = acpsService.save(acp);
		if (createdACP != null)
			return ResponseEntity.ok(createdACP);
		else
			return ResponseEntity.badRequest().body("Invalid ACP");
	}

	@PostMapping
	public ResponseEntity<?> updateACP(@RequestBody Map<String, Object> json) {
		ACP updatedACP = acpsService.updateACP(json);
		if (updatedACP != null)
			return ResponseEntity.ok(updatedACP);
		else
			return ResponseEntity.badRequest().body("Invalid request");
	}
}
