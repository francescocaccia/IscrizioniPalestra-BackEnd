package Francesco.Iscrizioni.Pugilistica.Fabrianese.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import Francesco.Iscrizioni.Pugilistica.Fabrianese.payload.AtletaPayload;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.payload.AtletaResponse;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.AbbonamentoService;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.AtletaService;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.VisitaMedicaService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/atleti")
public class AtletaController {

	@Autowired
	private AtletaService atletaService;

	@Autowired
	private AbbonamentoService abbonamentoService;

	@Autowired
	private VisitaMedicaService visitaMedicaService;

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
		visita.setDataVisita(dto.getDataVisita());
		visita.setDataScadenza(dto.getDataScadenzaVisita());
		visitaMedicaService.createVisitaMedica(visita); // crea la visita medica

		return atleta;
	}

	@GetMapping("/search")
	public List<AtletaResponse> searchAtletiByNome(@RequestParam String nome) {
		return atletaService.searchByNome(nome);
	}

	@GetMapping
	public List<Atleta> getAllAtleti() {
		return atletaService.getAllAtleti();
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
	public ResponseEntity<Atleta> updateAtleta(@PathVariable Long id, @RequestBody Atleta atleta) {
		if (!id.equals(atleta.getId())) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(atletaService.updateAtleta(atleta));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAtleta(@PathVariable Long id) {
		atletaService.deleteAtleta(id);
		return ResponseEntity.noContent().build();
	}
}
