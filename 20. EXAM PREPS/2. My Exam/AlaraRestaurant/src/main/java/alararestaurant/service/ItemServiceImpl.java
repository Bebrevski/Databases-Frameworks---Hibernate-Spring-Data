package alararestaurant.service;

import alararestaurant.constants.Constants;
import alararestaurant.domain.dtos.ItemsImportJsonDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ITEMS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_JSON_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        StringBuilder importResult = new StringBuilder();

        ItemsImportJsonDto[] itemsImportJsonDtos = this.gson.fromJson(items, ItemsImportJsonDto[].class);

        for (ItemsImportJsonDto itemsImportJsonDto : itemsImportJsonDtos) {
            Category category = this.categoryRepository
                    .findByName(itemsImportJsonDto.getCategory())
                    .orElse(null);

            if (category == null) {
                category = new Category();
                category.setName(itemsImportJsonDto.getCategory());
            }

            if (!this.validationUtil.isValid(itemsImportJsonDto) || !this.validationUtil.isValid(category)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Item itemEntity = this.modelMapper.map(itemsImportJsonDto, Item.class);

            if (this.categoryRepository
                    .findByName(category.getName())
                    .orElse(null) == null) {
                category = this.categoryRepository.saveAndFlush(category);
            }

            itemEntity.setCategory(category);

            Item existingItemEntity = this.itemRepository.findByName(itemEntity.getName()).orElse(null);

            if (existingItemEntity == null) {
                itemEntity = this.itemRepository.saveAndFlush(itemEntity);
            } else {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            importResult
                    .append(String.format(Constants.SUCCESSFULLY_IMPORTED__EMPLOYEE_MESSAGE, itemEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
