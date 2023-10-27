package Francesco.Iscrizioni.Pugilistica.Fabrianese.payload;

import java.time.LocalDate;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.enums.TipoAbbonamento;
import lombok.Data;

@Data
public class AtletaResponse {

	// Campi dell'atleta
	private Long id;
	private String nome;
	private String email;
	private LocalDate dataDiNascita;
	private String telefono;

	// Campi dell'abbonamento
	private TipoAbbonamento tipoAbbonamento;
	private LocalDate dataInizioAbbonamento;
	private LocalDate dataScadenzaAbbonamento;

	// Campi della visita medica
	private LocalDate dataVisitaMedica;
	private LocalDate dataScadenzaVisitaMedica;

	// Campi per l'elettroencefalogramma
	private LocalDate dataInizioElettroencefalogramma;
	private LocalDate dataFineElettroencefalogramma;
}
