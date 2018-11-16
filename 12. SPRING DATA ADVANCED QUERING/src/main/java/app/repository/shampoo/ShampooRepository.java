package app.repository.shampoo;

import app.domain.entities.Label;
import app.domain.entities.Shampoo;
import app.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllByBrand(String brand);

    List<Shampoo> findAllBySize(Size size);

    List<Shampoo> findAllByLabel(Label label);

    List<Shampoo> findAllByLabelAndSize(Label label, Size size);

    List<Shampoo> findAllByLabelOrSizeOrderById(Label label, Size size);

}
