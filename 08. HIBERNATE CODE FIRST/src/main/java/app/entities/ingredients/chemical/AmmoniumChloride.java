package app.entities.ingredients.chemical;

import app.entities.ingredients.BasicChemicalIngredient;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "AM")
public class AmmoniumChloride extends BasicChemicalIngredient {
    private static final String NAME = "Ammonium Chloride";
    private static final BigDecimal PRICE = new BigDecimal("0.59");
    private static final String CHEMICAL_FORMULA = "NH4CL";



    public AmmoniumChloride() {
        super(NAME, PRICE, CHEMICAL_FORMULA);
    }
}
