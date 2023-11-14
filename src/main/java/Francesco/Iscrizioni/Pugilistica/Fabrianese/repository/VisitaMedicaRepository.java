package Francesco.Iscrizioni.Pugilistica.Fabrianese.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.VisitaMedica;

@Repository
public interface VisitaMedicaRepository extends JpaRepository<VisitaMedica, Long> {
	List<VisitaMedica> findAllByAtleta(Atleta atleta);

	Optional<VisitaMedica> findByAtleta(Atleta atleta);

	List<VisitaMedica> findAllByDataScadenzaVisitaMedicaBefore(LocalDate dataScadenzaVisitaMedica);

	List<VisitaMedica> findAllByDataScadenzaVisitaMedicaBetween(LocalDate dataVisitaMedica,
			LocalDate dataScadenzaVisitaMedica);
}
