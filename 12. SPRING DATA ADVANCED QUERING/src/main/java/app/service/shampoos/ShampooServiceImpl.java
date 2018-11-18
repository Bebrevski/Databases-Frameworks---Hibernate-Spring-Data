package app.service.shampoos;

import app.domain.entities.*;
import app.repository.IngredientRepository;
import app.repository.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> selectShampoosBySize(String inputSize) {

        Size size = null;
        try {
            size = Size.valueOf(inputSize.toUpperCase());
        } catch (IllegalStateException ex) {
            System.out.println("You have entered invalid size");
        }

        return this.shampooRepository.findAllBySize(size).stream()
                .map(getFormat())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> selectShampoosByIngredients(List<String> ingredientNames) {
        Set<Ingredient> ingredients = this.ingredientRepository
                .findAllByNameIn(ingredientNames);

        return this.shampooRepository.findByIngredientsIn(ingredients)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> selectShampoosByGivenSizes(List<String> sizes) {

        List<String> result = new ArrayList<>();

        for (String size : sizes) {
            Size currentSize = null;
            try {
                currentSize = Size.valueOf(size.toUpperCase());
            } catch (IllegalStateException ex) {
                System.out.println("You have entered invalid size");
            }

            result.addAll(this.shampooRepository.findAllBySize(currentSize)
                    .stream()
                    .map(getFormat())
                    .collect(Collectors.toList()));
        }

        return result;
    }

    @Override
    public List<String> getShampoosByLabelOrSize(Long labelId, String inputSize) {
        Size size = Size.valueOf(inputSize.toUpperCase());

        return this.shampooRepository.findAllByLabelIdOrSizeOrderByPriceAsc(labelId, size)
                .stream()
                .map(getFormat())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> selectShampoosByPrice(BigDecimal minPrice) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(minPrice)
                .stream()
                .map(getFormat())
                .collect(Collectors.toList());
    }

    @Override
    public Integer countShampoosByPriceLessThan(BigDecimal inputPrice) {
        return this.shampooRepository.countByPriceLessThan(inputPrice);
    }

    @Override
    public List<String> selectShampoosByIngredientsCount(int count) {
        return this.shampooRepository.findAllByIngredientsCountLessThan(count)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }

    private Function<Shampoo, String> getFormat() {
        return s -> String.format("%s %s %.2flv."
                , s.getBrand()
                , s.getSize().name()
                , s.getPrice());
    }
}
