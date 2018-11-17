package app.repository;

import app.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findAllByNameIn(List<String> ingredientNames);

    Set<Ingredient> findAllByNameStartingWith(String letter);
}
