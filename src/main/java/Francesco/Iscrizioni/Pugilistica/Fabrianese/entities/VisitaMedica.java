package Francesco.Iscrizioni.Pugilistica.Fabrianese.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "visita_medica")
@Data
public class VisitaMedica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "atleta_id", nullable = false)
	private Atleta atleta;

	@Column(nullable = false)
	private LocalDate dataVisitaMedica;

	@Column(nullable = false)
	private LocalDate dataScadenzaVisitaMedica;

	@Column
	private LocalDate dataInizioElettroencefalogramma;

	@Column
	private LocalDate dataFineElettroencefalogramma;

}
