package Francesco.Iscrizioni.Pugilistica.Fabrianese.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Abbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.VisitaMedica;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.payload.AtletaResponse;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.AbbonamentoRepository;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.AtletaRepository;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.VisitaMedicaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AtletaService {

	@Autowired
	private AtletaRepository atletaRepository;

	@Autowired
	private AbbonamentoRepository abbonamentoRepository;

	@Autowired
	private VisitaMedicaRepository visitaMedicaRepository;

	public List<AtletaResponse> getAllAtleti() {
		List<Atleta> atleti = atletaRepository.findAll();
		return atleti.stream().map(this::convertToAtletaResponse).collect(Collectors.toList());
	}

	public Optional<Atleta> getAtletaById(Long id) {
		return atletaRepository.findById(id);
	}

	public List<AtletaResponse> searchByNome(String nome) {
		List<Atleta> atleti = atletaRepository.findByNomeContainingIgnoreCase(nome);
		return atleti.stream().map(this::convertToAtletaResponse).collect(Collectors.toList());
	}

	public Atleta createAtleta(Atleta atleta) {
		return atletaRepository.save(atleta);
	}

	public Atleta updateAtleta(Atleta atleta) {
		if (atletaRepository.existsById(atleta.getId())) {
			return atletaRepository.save(atleta);
		}
		// Potresti lanciare un'eccezione qui se l'atleta non esiste
		throw new EntityNotFoundException("Atleta non trovato con ID: " + atleta.getId());
	}

	public void deleteAtleta(Long id) {
		atletaRepository.deleteById(id);
	}

	public Optional<Atleta> findByEmail(String email) {
		return atletaRepository.findByEmail(email);
	}

	public AtletaResponse convertToAtletaResponse(Atleta atleta) {
		List<VisitaMedica> visiteMediche = visitaMedicaRepository.findAllByAtleta(atleta);

		AtletaResponse response = new AtletaResponse();
		response.setId(atleta.getId());
		response.setNome(atleta.getNome());
		response.setEmail(atleta.getEmail());
		response.setDataDiNascita(atleta.getDataDiNascita());
		response.setTelefono(atleta.getTelefono());

		Optional<Abbonamento> optionalAbbonamento = abbonamentoRepository.findByAtleta(atleta);
		if (optionalAbbonamento.isPresent()) {
			Abbonamento abbonamento = optionalAbbonamento.get();
			response.setTipoAbbonamento(abbonamento.getTipo());
			response.setDataInizioAbbonamento(abbonamento.getDataInizio());
			response.setDataScadenzaAbbonamento(abbonamento.getDataScadenza());
		}

		// Come esempio, prendiamo l'ultima visita medica se ci sono più visite.
		if (!visiteMediche.isEmpty()) {
			VisitaMedica ultimaVisita = visiteMediche.get(visiteMediche.size() - 1); // Assumendo che l'elenco sia
																						// ordinato per data
			response.setDataVisitaMedica(ultimaVisita.getDataVisitaMedica());
			response.setDataScadenzaVisitaMedica(ultimaVisita.getDataScadenzaVisitaMedica());
			response.setDataInizioElettroencefalogramma(ultimaVisita.getDataInizioElettroencefalogramma());
			response.setDataFineElettroencefalogramma(ultimaVisita.getDataFineElettroencefalogramma());
		}

		return response;
	}

}
