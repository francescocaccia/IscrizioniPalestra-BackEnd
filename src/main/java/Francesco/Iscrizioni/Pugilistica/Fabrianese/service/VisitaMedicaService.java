package Francesco.Iscrizioni.Pugilistica.Fabrianese.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.VisitaMedica;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.VisitaMedicaRepository;

@Service
public class VisitaMedicaService {

	@Autowired
	private VisitaMedicaRepository visitaMedicaRepository;

	public VisitaMedica createVisitaMedica(VisitaMedica visitaMedica) {
		return visitaMedicaRepository.save(visitaMedica);
	}

	// Aggiungi altri metodi per operazioni CRUD, se necessario
}