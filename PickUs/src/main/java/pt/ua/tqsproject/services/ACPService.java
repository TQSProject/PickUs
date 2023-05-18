package pt.ua.tqsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqsproject.entities.ACP;
import pt.ua.tqsproject.repositories.ACPRepository;

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
}
