package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        List<Category> categories = this.categoryRepository.exportCategoriesByCountOfItems();
        StringBuilder exportResult = new StringBuilder();

        for (Category category : categories) {
            exportResult
                    .append(String.format("Category: %s", category.getName()))
                    .append(System.lineSeparator());

            for (Item item : category.getItems()) {
                exportResult
                        .append(String.format("---ItemName: %s", item.getName())).append(System.lineSeparator())
                        .append(String.format("---ItemPrice: %.2f", item.getPrice())).append(System.lineSeparator())
                        .append(System.lineSeparator());
            }

            exportResult.append(System.lineSeparator());
        }

        return exportResult.toString();
    }
}
