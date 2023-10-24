package Francesco.Iscrizioni.Pugilistica.Fabrianese.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Abbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.AbbonamentoRepository;

@Service
public class AbbonamentoService {

	@Autowired
	private AbbonamentoRepository abbonamentoRepository;

	public Abbonamento createAbbonamento(Abbonamento abbonamento) {
		return abbonamentoRepository.save(abbonamento);
	}

	// Aggiungi altri metodi per operazioni CRUD, se necessario
}
