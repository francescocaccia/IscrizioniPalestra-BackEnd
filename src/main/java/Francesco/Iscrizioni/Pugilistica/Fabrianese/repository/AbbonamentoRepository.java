package Francesco.Iscrizioni.Pugilistica.Fabrianese.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Abbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Long> {
	Optional<Abbonamento> findByAtleta(Atleta atleta);
}