package app.services.categories;

import app.domain.dtos.CategorySeedDto;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);
}
