package app.entities.shampoos.concreteShampooProducts;

import app.entities.labels.BasicLabel;
import app.entities.shampoos.BasicShampoo;
import app.enums.Size;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FS")
public class FiftyShades extends BasicShampoo {

    private static final String BRAND = "Fifty Shades";
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final Size SIZE = Size.SMALL;

    public FiftyShades() {
    }

    public FiftyShades(BasicLabel basicLabel) {
        super(BRAND, PRICE, SIZE, basicLabel);
    }
}
