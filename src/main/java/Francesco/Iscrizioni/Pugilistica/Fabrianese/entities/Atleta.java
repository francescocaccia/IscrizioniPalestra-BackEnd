package Francesco.Iscrizioni.Pugilistica.Fabrianese.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "atleta")
@Data
public class Atleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column(unique = true) // Assicurarsi che l'email sia univoca
	private String email;

	@Column
	private LocalDate dataDiNascita;

//	@Column
//	private String password; // Ricorda di hashare prima di salvarla!

	@Column
	private String telefono;
}
