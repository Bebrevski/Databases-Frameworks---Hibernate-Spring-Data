package app.repositories;

import app.domain.entities.Product;
import app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenAndBuyerIdOrderByPrice(BigDecimal more, BigDecimal less, User buyer);
}
