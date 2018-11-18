package app.service.ingredients;

import app.domain.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<String> selectIngredientsByName(String letter);

    List<String> selectIngredientsByNames(List<String> names);
}
