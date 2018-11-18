package app.service.shampoos;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<String> selectShampoosBySize(String inputSize);

    List<String> selectShampoosByIngredients(List<String> ingredientNames);

    List<String> selectShampoosByGivenSizes(List<String> sizes);

    List<String> getShampoosByLabelOrSize(Long labelId, String size);

    List<String> selectShampoosByPrice(BigDecimal minPrice);

    Integer countShampoosByPriceLessThan(BigDecimal inputPrice);

    List<String> selectShampoosByIngredientsCount(int count);
}
