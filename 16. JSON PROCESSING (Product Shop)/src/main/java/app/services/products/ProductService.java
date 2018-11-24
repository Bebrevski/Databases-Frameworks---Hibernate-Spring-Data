package app.services.products;

import app.domain.dtos.ProductSeedDto;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);
}
