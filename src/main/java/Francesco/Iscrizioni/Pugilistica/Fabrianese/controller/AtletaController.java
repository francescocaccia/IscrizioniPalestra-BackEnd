package Francesco.Iscrizioni.Pugilistica.Fabrianese.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Abbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.VisitaMedica;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.enums.TipoAbbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.payload.AtletaPayload;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.payload.AtletaResponse;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.AbbonamentoRepository;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.VisitaMedicaRepository;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.AbbonamentoService;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.AtletaService;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.VisitaMedicaService;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "https://iscrizioni-palestra-front-end.vercel.app")
@RestController
@RequestMapping("/atleti")
public class AtletaController {

	@Autowired
	private AtletaService atletaService;

	@Autowired
	private AbbonamentoService abbonamentoService;

	@Autowired
	private VisitaMedicaService visitaMedicaService;

	@Autowired
	private AbbonamentoRepository abbonamentoRepository;

	@Autowired
	private VisitaMedicaRepository visitaMedicaRepository;

	@PostMapping
	@Transactional
	public Atleta createAtleta(@RequestBody AtletaPayload dto) {
		Atleta atleta = new Atleta();
		// Popola i campi dell'atleta con i dati dal DTO
		atleta.setNome(dto.getNome());
		atleta.setEmail(dto.getEmail());
		atleta.setDataDiNascita(dto.getDataDiNascita());
		atleta.setTelefono(dto.getTelefono());

		atleta = atletaService.createAtleta(atleta); // crea l'atleta

		Abbonamento abbonamento = new Abbonamento();
		// Popola i campi dell'abbonamento con i dati dal DTO
		abbonamento.setAtleta(atleta);
		abbonamento.setTipo(dto.getTipoAbbonamento());
		abbonamento.setDataInizio(dto.getDataInizioAbbonamento());
		abbonamento.setDataScadenza(dto.getDataScadenzaAbbonamento());
		abbonamentoService.createAbbonamento(abbonamento); // crea l'abbonamento

		VisitaMedica visita = new VisitaMedica();
		// Popola i campi della visita medica con i dati dal DTO
		visita.setAtleta(atleta);
		visita.setDataVisitaMedica(dto.getDataVisitaMedica());
		visita.setDataScadenzaVisitaMedica(dto.getDataScadenzaVisitaMedica());

		// Controlla se i campi per l'elettroencefalogramma sono presenti nel DTO prima
		// di settarli
		if (dto.getDataInizioElettroencefalogramma() != null) {
			visita.setDataInizioElettroencefalogramma(dto.getDataInizioElettroencefalogramma());
		}
		if (dto.getDataFineElettroencefalogramma() != null) {
			visita.setDataFineElettroencefalogramma(dto.getDataFineElettroencefalogramma());
		}

		visitaMedicaService.createVisitaMedica(visita); // crea la visita medica

		return atleta;
	}

	@GetMapping("/search")
	public List<AtletaResponse> searchAtletiByNome(@RequestParam String nome) {
		return atletaService.searchByNome(nome);
	}

	@GetMapping
	public ResponseEntity<List<AtletaResponse>> getAllAtleti() {
		List<AtletaResponse> atleti = atletaService.getAllAtleti();
		return ResponseEntity.ok(atleti);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AtletaResponse> getAtletaById(@PathVariable Long id) {
		Optional<Atleta> optionalAtleta = atletaService.getAtletaById(id);
		if (optionalAtleta.isPresent()) {
			AtletaResponse response = atletaService.convertToAtletaResponse(optionalAtleta.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AtletaResponse> updateAtleta(@PathVariable Long id, @RequestBody AtletaPayload payload) {
		// Log dei dati del payload ricevuto
		System.out.println("Aggiornamento atleta con ID: " + id + ", Payload ricevuto: " + payload);

		Optional<Atleta> optionalAtleta = atletaService.getAtletaById(id);
		if (!optionalAtleta.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Atleta existingAtleta = optionalAtleta.get();

		// Update atleta's details
		existingAtleta.setNome(payload.getNome());
		existingAtleta.setEmail(payload.getEmail());
		existingAtleta.setDataDiNascita(payload.getDataDiNascita());
		existingAtleta.setTelefono(payload.getTelefono());

		Atleta updatedAtleta = atletaService.updateAtleta(existingAtleta);

		// Update abbonamento's details
		Optional<Abbonamento> optionalAbbonamento = abbonamentoRepository.findByAtleta(updatedAtleta);
		if (optionalAbbonamento.isPresent()) {
			Abbonamento existingAbbonamento = optionalAbbonamento.get();
			existingAbbonamento.setTipo(payload.getTipoAbbonamento());
			existingAbbonamento.setDataInizio(payload.getDataInizioAbbonamento());
			existingAbbonamento.setDataScadenza(payload.getDataScadenzaAbbonamento());
			abbonamentoRepository.save(existingAbbonamento);
		}

		// Update visita medica's details
		Optional<VisitaMedica> optionalVisita = visitaMedicaRepository.findByAtleta(updatedAtleta);
		if (optionalVisita.isPresent()) {
			VisitaMedica existingVisita = optionalVisita.get();
			existingVisita.setDataVisitaMedica(payload.getDataVisitaMedica());
			existingVisita.setDataScadenzaVisitaMedica(payload.getDataScadenzaVisitaMedica());

			// Controlla se i campi per l'elettroencefalogramma sono presenti nel DTO prima
			// di aggiornare
			if (payload.getDataInizioElettroencefalogramma() != null) {
				existingVisita.setDataInizioElettroencefalogramma(payload.getDataInizioElettroencefalogramma());
			}
			if (payload.getDataFineElettroencefalogramma() != null) {
				existingVisita.setDataFineElettroencefalogramma(payload.getDataFineElettroencefalogramma());
			}

			visitaMedicaRepository.save(existingVisita);
		}

		AtletaResponse response = atletaService.convertToAtletaResponse(updatedAtleta);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAtleta(@PathVariable Long id) {
		atletaService.deleteAtleta(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/tipoAbbonamento")
	public ResponseEntity<List<String>> getTipiAbbonamento() {
		return ResponseEntity.ok(Arrays.stream(TipoAbbonamento.values()).map(Enum::name).collect(Collectors.toList()));
	}

}
