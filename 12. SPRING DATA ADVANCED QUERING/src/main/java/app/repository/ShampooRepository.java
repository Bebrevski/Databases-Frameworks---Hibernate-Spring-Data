package app.repository;

import app.domain.entities.Ingredient;
import app.domain.entities.Label;
import app.domain.entities.Shampoo;
import app.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllBySize(Size size);

    @Query("" +
            "select s from app.domain.entities.Shampoo as s " +
            "join s.ingredients as i " +
            "where i in :ingredients")
    List<Shampoo> findByIngredientsIn(@Param(value = "ingredients") Set<Ingredient> ingredients);

    List<Shampoo> findAllByLabelIdOrSizeOrderByPriceAsc(Long labelId, Size size);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
}
