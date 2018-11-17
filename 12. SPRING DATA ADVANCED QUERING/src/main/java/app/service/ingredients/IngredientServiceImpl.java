package app.service.ingredients;

import app.domain.entities.Ingredient;
import app.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public List<String> selectIngredientsByName(String letter) {
        return this.ingredientRepository.findAllByNameStartingWith(letter)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }
}
