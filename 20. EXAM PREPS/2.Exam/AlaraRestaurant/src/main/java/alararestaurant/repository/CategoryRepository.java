package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);

    @Query("" +
            "SELECT c " +
            "FROM alararestaurant.domain.entities.Category AS c " +
            "JOIN c.items AS i " +
            "GROUP BY c.id " +
            "ORDER BY size(i) DESC, SUM(i.price) DESC ")
    List<Category> exportCategoriesByCountOfItems();
}
