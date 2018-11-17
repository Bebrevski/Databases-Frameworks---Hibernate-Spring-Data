package app.service.ingredients;

import java.util.List;

public interface IngredientService {

    List<String> selectIngredientsByName(String letter);
}
