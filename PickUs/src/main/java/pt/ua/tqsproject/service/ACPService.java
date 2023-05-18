package pt.ua.tqsproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqsproject.data.ACP;
import pt.ua.tqsproject.data.ACPRepository;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<ACP> getACPById(Long id) {
		// Implement the logic to retrieve an ACP by ID
		return acpRepository.findById(id);
	}
	
	public List<ACP> getACPByName(String name) {
		// Implement the logic to retrieve an ACP by ID
		return acpRepository.getACPsByNameContains(name);
	}
	
	public List<ACP> getACPsByCity(String city) {
		// Implement the logic to retrieve an ACP by ID
		return acpRepository.getACPSByCityContains(city);
	}
	
	public List<ACP> getAllACPs() {
		// Implement the logic to retrieve all ACPs
		return acpRepository.findAll();
	}
	
}
