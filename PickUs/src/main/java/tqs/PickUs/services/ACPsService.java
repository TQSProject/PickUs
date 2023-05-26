package tqs.PickUs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.PickUs.entities.ACP;
import tqs.PickUs.repositories.ACPsRepository;

import java.util.List;

@Service
public class ACPsService {
	private final ACPsRepository acpRepository;
	
	@Autowired
	public ACPsService(ACPsRepository acpRepository) {
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
