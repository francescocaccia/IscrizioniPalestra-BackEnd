package Francesco.Iscrizioni.Pugilistica.Fabrianese.entities;

import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "atleta")
@Data
public class Atleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String nome;

	@NotNull
	@Column(unique = true, nullable = false) // Assicurarsi che l'email sia univoca e non null
	private String email;

	@NotNull
	@Column(nullable = false)
	private LocalDate dataDiNascita;

	@NotNull
	@Column(nullable = false)
	private String telefono;

	// Relazione uno-a-uno con VisitaMedica
	@OneToOne(mappedBy = "atleta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private VisitaMedica visitaMedica;
}
