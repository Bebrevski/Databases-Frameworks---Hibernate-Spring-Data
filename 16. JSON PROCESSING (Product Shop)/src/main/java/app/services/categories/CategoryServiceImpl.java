package app.services.categories;

import app.domain.dtos.CategorySeedDto;
import app.domain.entities.Category;
import app.repositories.CategoryRepository;
import app.utils.validator.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!this.validatorUtil.isValid(categorySeedDto)) {
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Category entity = this.modelMapper.map(categorySeedDto, Category.class);

            this.categoryRepository.saveAndFlush(entity);
        }
    }
}
