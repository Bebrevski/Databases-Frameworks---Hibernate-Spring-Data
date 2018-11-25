package app.services.products;

import app.domain.dtos.ProductInRangeDTO;
import app.domain.dtos.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDTO> productsInRange(BigDecimal more, BigDecimal less);
}
