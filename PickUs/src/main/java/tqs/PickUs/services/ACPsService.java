package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.Order;
import tqs.PickUs.repositories.ACPsRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class ACPsService {
	@Autowired
	private ACPsRepository acpsRepository;

	@Autowired
	private OrdersService ordersService;

	public List<ACP> getAllACPs() {
		// Implement the logic to retrieve all ACPs
		return acpsRepository.findAll();
	}

	public ACP getACPById(int id) {
		return acpsRepository.findById(id);
	}

	public ACP getACPByName(String name) {
		return acpsRepository.findByName(name);
	}

	public List<ACP> getACPByCity(String city) {
		return acpsRepository.findByCity(city);
	}

	public List<Order> getACPOrders(int acpId) {
		ACP acp = acpsRepository.findById(acpId);
		if (acp == null)
			return null;
		String acpName = acp.getName();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("acp", acpName);
		return ordersService.getOrders(params);
	}

	public ACP save(ACP acp) {
		return acpsRepository.save(acp);
	}
}
