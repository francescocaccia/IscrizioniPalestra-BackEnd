package Francesco.Iscrizioni.Pugilistica.Fabrianese.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {
	Optional<Atleta> findByEmail(String email);

	List<Atleta> findByNomeContainingIgnoreCase(String nome);

}
