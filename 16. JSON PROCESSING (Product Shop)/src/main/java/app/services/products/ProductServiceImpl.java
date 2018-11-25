package app.services.products;

import app.domain.dtos.ProductInRangeDTO;
import app.domain.dtos.ProductSeedDto;
import app.domain.entities.Category;
import app.domain.entities.Product;
import app.domain.entities.User;
import app.repositories.CategoryRepository;
import app.repositories.ProductRepository;
import app.repositories.UserRepository;
import app.utils.validator.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            if (!this.validatorUtil.isValid(productSeedDto)) {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Product entity = this.modelMapper.map(productSeedDto, Product.class);

            entity.setSellerId(this.getRandomUser());

            Random random = new Random();
            if (random.nextInt() % 13 != 0) {
                entity.setBuyerId(this.getRandomUser());
            }

            entity.setCategories(this.getRandomCategories());

            this.productRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<ProductInRangeDTO> productsInRange(BigDecimal more, BigDecimal less) {
        List<Product> productEntities = this.productRepository.findAllByPriceBetweenAndBuyerIdOrderByPrice(more, less, null);

        List<ProductInRangeDTO> productInRangeDTOS = new ArrayList<>();
        for (Product productEntity : productEntities) {
            ProductInRangeDTO productInRangeDTO = this.modelMapper.map(productEntity, ProductInRangeDTO.class);

            productInRangeDTO.setSeller(String.format("%s %s"
                    , productEntity.getSellerId().getFirstName()
                    , productEntity.getSellerId().getLastName()));

            productInRangeDTOS.add(productInRangeDTO);
        }
        return productInRangeDTOS;
    }

    private User getRandomUser() {
        Random random = new Random();

        return this.userRepository
                .getOne((long) random.nextInt((int) this.userRepository.count() - 1) + 1);
    }

    private List<Category> getRandomCategories() {
        Random random = new Random();

        List<Category> categories = new ArrayList<>();

        int categoriesCount = random.nextInt((int) this.categoryRepository.count() - 1) + 1;

        for (int i = 0; i < categoriesCount; i++) {
            categories.add(this.categoryRepository
                    .getOne((long) (random.nextInt((int) this.categoryRepository.count() - 1) + 1)));
        }

        return categories;
    }
}
