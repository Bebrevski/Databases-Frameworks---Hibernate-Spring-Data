package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {
    Optional<Racer> findByName(String racerName);
}
