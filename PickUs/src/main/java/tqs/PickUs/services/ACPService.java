package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPRepository;

import java.util.List;

@Service
public class ACPService {
	private final ACPRepository acpRepository;
	
	@Autowired
	public ACPService(ACPRepository acpRepository) {
		this.acpRepository = acpRepository;
	}
	
	public ACP saveACP(ACP acp) {
		// Implement the logic to create and save the ACP
		return acpRepository.save(acp);
	}
	
	public List<ACP> getAllACPs() {
		// Implement the logic to retrieve all ACPs
		return acpRepository.findAll();
	}
}
