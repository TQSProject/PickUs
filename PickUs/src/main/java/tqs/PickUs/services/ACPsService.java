package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;

import java.util.List;

@Service
public class ACPsService {
	@Autowired
	private ACPsRepository acpsRepository;
	
	public List<ACP> getAllACPs() {
		// Implement the logic to retrieve all ACPs
		return acpsRepository.findAll();
	}

	public ACP getACPById(int id)
	{
		return acpsRepository.findById(id);
	}

	public ACP saveACP(ACP acp) {
		// Implement the logic to create and save the ACP
		return acpsRepository.save(acp);
	}
}
