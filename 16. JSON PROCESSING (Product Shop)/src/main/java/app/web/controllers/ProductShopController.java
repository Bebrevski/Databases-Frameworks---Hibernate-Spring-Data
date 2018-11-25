package app.web.controllers;

import app.domain.dtos.CategorySeedDto;
import app.domain.dtos.ProductInRangeDTO;
import app.domain.dtos.ProductSeedDto;
import app.domain.dtos.UserSeedDto;
import app.services.categories.CategoryService;
import app.services.products.ProductService;
import app.services.users.UserService;
import app.utils.file.FileIOUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {

    private static final String USER_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\16. JSON PROCESSING (Product Shop)\\src\\main\\resources\\files\\users.json";
    private static final String CATEGORY_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\16. JSON PROCESSING (Product Shop)\\src\\main\\resources\\files\\categories.json";
    private static final String PRODUCT_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\16. JSON PROCESSING (Product Shop)\\src\\main\\resources\\files\\products.json";
    private static final String PRODUCT_IN_RANGE_OUTPUT_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\16. JSON PROCESSING (Product Shop)\\src\\main\\resources\\output\\products-in-range.json";

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;

    @Autowired
    public ProductShopController(UserService userService, CategoryService categoryService, ProductService productService, FileIOUtil fileIOUtil, Gson gson) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();

        this.productsInRange(); //Writes to file in resource directory
    }

    private void seedProducts() throws IOException {
        String productsFileContent = this.fileIOUtil.readFile(PRODUCT_FILE_PATH);

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(productsFileContent, ProductSeedDto[].class);

        this.productService.seedProducts(productSeedDtos);
    }

    private void seedCategories() throws IOException {
        String categoriesFileContent = this.fileIOUtil.readFile(CATEGORY_FILE_PATH);

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(categoriesFileContent, CategorySeedDto[].class);

        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedUsers() throws IOException {
        String usersFileContent = this.fileIOUtil.readFile(USER_FILE_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(usersFileContent, UserSeedDto[].class);

        this.userService.seedUsers(userSeedDtos);
    }

    private void productsInRange() throws IOException {
        List<ProductInRangeDTO> productInRangeDTOList =
                this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        String productsInRangeJSON = this.gson.toJson(productInRangeDTOList);

        File file = new File(PRODUCT_IN_RANGE_OUTPUT_PATH);

        FileWriter writer = new FileWriter(file);
        writer.write(productsInRangeJSON);

        writer.close();
    }
}
