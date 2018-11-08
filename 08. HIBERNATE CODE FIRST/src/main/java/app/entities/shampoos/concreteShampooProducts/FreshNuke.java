package app.entities.shampoos.concreteShampooProducts;

import app.entities.labels.BasicLabel;
import app.entities.shampoos.BasicShampoo;
import app.enums.Size;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "FN")
public class FreshNuke extends BasicShampoo {

    private static final String BRAND = "Fresh Nuke";
    private static final BigDecimal PRICE = new BigDecimal("9.33");
    private static final Size SIZE = Size.LARGE;

    public FreshNuke() {
        }

    public FreshNuke(BasicLabel basicLabel) {
        super(BRAND, PRICE, SIZE, basicLabel);
    }
}
