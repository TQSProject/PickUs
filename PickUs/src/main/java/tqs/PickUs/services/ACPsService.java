package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.entities.ACPStatus;
import tqs.PickUs.entities.Order;
import tqs.PickUs.repositories.ACPsRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ACPsService {
	@Autowired
	private ACPsRepository acpsRepository;

	@Autowired
	private OrdersService ordersService;

	public List<ACP> getAllACPs() {
		return acpsRepository.findAll();
	}

	public ACP getACPById(int id) {
		return acpsRepository.findById(id);
	}

	public ACP getACPByName(String name) {
		return acpsRepository.findByName(name);
	}

	public List<ACP> getACPs(HashMap<String, String> params) {
		List<ACP> acps = acpsRepository.findAll();

		if (params.containsKey("name") && params.get("name") != null && !params.get("name").isBlank()) {
			acps = acps.stream()
					.filter(acp -> StringUtils.containsIgnoreCase(acp.getName(), params.get("name")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("city") && params.get("city") != null && !params.get("city").isBlank()) {
			acps = acps.stream()
					.filter(acp -> acp.getCity().equalsIgnoreCase(params.get("city")))
					.collect(Collectors.toList());
		}

		if (params.containsKey("status") && params.get("status") != null && !params.get("status").isBlank()) {
			acps = acps.stream()
					.filter(acp -> acp.getStatus().toString().equalsIgnoreCase(params.get("status")))
					.collect(Collectors.toList());
		}

		return acps;
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

	public ACP updateACP(Map<String, Object> json)
	{
		ACP acp = null;

		if (json.containsKey("id"))
		{
			int acpId = (int)json.get("id");
			acp = acpsRepository.findById(acpId);
		}

		if (acp == null && json.containsKey("name"))
			acp = acpsRepository.findByName((String)json.get("name"));

		if (acp == null)
			return null;

		if (json.containsKey("status"))
		{
			ACPStatus newStatus = null;
			try {
				String strNewStatus = (String)json.get("status");
				strNewStatus = strNewStatus.toUpperCase();
				newStatus = ACPStatus.valueOf(strNewStatus);
			}
			catch (IllegalArgumentException e)
			{
				// status field has invalid value
				return null;
			}
			acp.setStatus(newStatus);
			acpsRepository.save(acp);
		}

		return acp;

	}
}
