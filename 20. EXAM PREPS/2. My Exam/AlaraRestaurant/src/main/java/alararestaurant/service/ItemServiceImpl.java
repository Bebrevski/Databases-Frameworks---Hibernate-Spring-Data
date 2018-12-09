package alararestaurant.service;

import alararestaurant.constants.Constants;
import alararestaurant.domain.dtos.items.ItemsImportJsonDto;
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
            if (!this.validationUtil.isValid(itemsImportJsonDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Category categoryEntity = this.categoryRepository
                    .findByName(itemsImportJsonDto.getCategory())
                    .orElse(null);

            if (categoryEntity == null) {
                categoryEntity = new Category();
                categoryEntity.setName(itemsImportJsonDto.getCategory());
                categoryEntity = this.categoryRepository.saveAndFlush(categoryEntity);
            }

            Item itemEntity = this.itemRepository
                    .findByName(itemsImportJsonDto.getName())
                    .orElse(null);

            if (itemEntity != null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            itemEntity = this.modelMapper.map(itemsImportJsonDto, Item.class);
            itemEntity.setCategory(categoryEntity);

            this.itemRepository.saveAndFlush(itemEntity);

            importResult
                    .append(String.format("Record %s successfully imported."
                            , itemEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
