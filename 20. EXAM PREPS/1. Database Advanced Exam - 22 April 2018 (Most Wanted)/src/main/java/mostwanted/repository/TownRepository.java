package mostwanted.repository;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByName(String townName);

    @Query("" +
            "SELECT t " +
            "FROM mostwanted.domain.entities.Town AS t " +
            "JOIN t.racers AS r " +
            "GROUP BY t " +
            "ORDER BY SIZE(t.racers) DESC, t.name ASC")
    List<Town> exportRacingTowns();
}
