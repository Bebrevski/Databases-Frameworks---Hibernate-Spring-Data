package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {
    Optional<Racer> findByName(String racerName);

    @Query("" +
            "SELECT r " +
            "FROM mostwanted.domain.entities.Racer AS r " +
            "JOIN r.cars AS c " +
            "GROUP BY r " +
            "ORDER BY SIZE(r.cars) DESC, r.name ASC")
    Set<Racer> exportRacingCars();
}
