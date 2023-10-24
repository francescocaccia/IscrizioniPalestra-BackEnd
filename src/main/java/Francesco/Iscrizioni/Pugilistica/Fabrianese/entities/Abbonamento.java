package Francesco.Iscrizioni.Pugilistica.Fabrianese.entities;

import java.time.LocalDate;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.enums.TipoAbbonamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "abbonamento")
@Data
public class Abbonamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "atleta_id", nullable = false)
	private Atleta atleta;

	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private TipoAbbonamento tipo;

	@Column(nullable = false)
	private LocalDate dataInizio;

	@Column(nullable = false)
	private LocalDate dataScadenza;

}
