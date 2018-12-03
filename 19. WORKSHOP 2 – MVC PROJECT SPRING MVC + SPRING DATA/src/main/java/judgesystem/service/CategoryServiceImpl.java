package judgesystem.service;

import com.google.gson.Gson;
import judgesystem.domain.dto.CategoryImportDto;
import judgesystem.domain.entities.Category;
import judgesystem.repository.CategoryRepository;
import judgesystem.util.FileUtil;
import judgesystem.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public String importCategories(String categoriesFilePath) throws IOException {
        String categoriesFileContent = this.fileUtil.readFile(categoriesFilePath);

        CategoryImportDto[] categoryImportDtos = this.gson.fromJson(categoriesFileContent, CategoryImportDto[].class);

        StringBuilder sb = new StringBuilder();

        for (CategoryImportDto categoryImportDto : categoryImportDtos) {

            if (!this.validationUtil.isValid(categoryImportDto)) {
                for (ConstraintViolation<CategoryImportDto> violation : this.validationUtil.violations(categoryImportDto)) {
                    sb
                            .append(violation.getMessage())
                            .append(System.lineSeparator());
                }
                sb.append("Invalid data!").append(System.lineSeparator());
                continue;
            }

            Category parentCategory = this.modelMapper.map(categoryImportDto, Category.class);

            this.setParentCategory(parentCategory.getSubcategories(), parentCategory);

            this.categoryRepository.saveAndFlush(parentCategory);
        }

        return sb.append("Categories imported!").append(System.lineSeparator()).toString();
    }

    private void setParentCategory(Set<Category> subcategories, Category parent) {
        parent.setSubcategories(null);

        this.categoryRepository.saveAndFlush(parent);

        if (subcategories == null) {
            return;
        }

        for (Category subcategory : subcategories) {
            this.setParentCategory(subcategory.getSubcategories(), subcategory);

            subcategory.setParentCategory(parent);
        }
    }
}
