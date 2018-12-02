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

        for (CategoryImportDto categoryImportDto : categoryImportDtos) {
            Category parentCategory = this.modelMapper.map(categoryImportDto, Category.class);

            this.setParentCategory(parentCategory.getSubcategories(), parentCategory);

            this.categoryRepository.saveAndFlush(parentCategory);
        }

        return "Categories imported!";
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
